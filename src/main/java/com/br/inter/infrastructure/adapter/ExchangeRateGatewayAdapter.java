package com.br.inter.infrastructure.adapter;

import com.br.inter.domain.transaction.gateway.ExchangeRateGateway;
import com.br.inter.domain.transaction.model.ExchangeRate;
import com.br.inter.infrastructure.cache.RedisCache;
import com.br.inter.infrastructure.client.ExchangeRateClient;
import com.br.inter.infrastructure.client.response.ExchangeRateResponse;
import com.br.inter.infrastructure.exceptions.ExchangeRateUnavailableException;
import com.br.inter.infrastructure.repository.ExchangeRateRepository;
import com.br.inter.infrastructure.repository.entity.ExchangeRateEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExchangeRateGatewayAdapter implements ExchangeRateGateway {

    private final RedisCache redisCache;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateClient exchangeRateClient;

    @Override
    @CircuitBreaker(name = "exchangeRateGatewayAdapter", fallbackMethod = "fallbackExchangeRate")
    public ExchangeRate fetchLatestExchangeRate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        if (isWeekend(date)) {
            ExchangeRateEntity cachedRate = redisCache.getExchangeRateFromCache();
            if (cachedRate != null) {
                return new ExchangeRate(cachedRate.getId(), cachedRate.getUuid(), cachedRate.getRate(), cachedRate.getCreatedAt());
            }
        }
        Optional<ExchangeRateEntity> latestExchangeRate = exchangeRateRepository.findLatestExchangeRate();
        if (latestExchangeRate.isPresent()) {
            ExchangeRateEntity exchangeRate = latestExchangeRate.get();
            redisCache.saveExchangeRate(exchangeRate);
            return new ExchangeRate(exchangeRate.getId(), exchangeRate.getUuid(), exchangeRate.getRate(), exchangeRate.getCreatedAt());
        }
        ExchangeRateEntity newExchangeRate = fetchFromApi(date, formatter);
        redisCache.saveExchangeRate(newExchangeRate);
        ExchangeRateEntity exchangeRateEntity = exchangeRateRepository.save(newExchangeRate);
        return new ExchangeRate(exchangeRateEntity.getId(), exchangeRateEntity.getUuid(), exchangeRateEntity.getRate(), exchangeRateEntity.getCreatedAt());
    }

    private ExchangeRateEntity fetchFromApi(LocalDate date, DateTimeFormatter formatter) {
        ExchangeRateResponse response = exchangeRateClient.getExchangeRate(date.format(formatter), 1, "json");
        if (response == null || response.getExchangeRates().isEmpty()) {
            date = getLastValidBusinessDay(date);
            response = exchangeRateClient.getExchangeRate(date.format(formatter), 1, "json");
        }
        if (response == null || response.getExchangeRates().isEmpty()) {
            throw new IllegalStateException("Exchange rate not available");
        }
        BigDecimal exchangeRate = response.getExchangeRates().get(0).getCotacaoCompra();
        ExchangeRateEntity newExchangeRate = new ExchangeRateEntity();
        newExchangeRate.setRate(exchangeRate);
        newExchangeRate.setCreatedAt(date.atStartOfDay());
        return newExchangeRate;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private LocalDate getLastValidBusinessDay(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.minusDays(1);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.minusDays(2);
        }
        return date;
    }
    public ExchangeRate fallbackExchangeRate(Throwable throwable) {
        throw new ExchangeRateUnavailableException("Service is currently unavailable. Please try again later.");
    }

}
