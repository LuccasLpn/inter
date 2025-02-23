package com.br.inter.infrastructure.cache;

import com.br.inter.infrastructure.repository.entity.ExchangeRateEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_KEY = "latest_exchange_rate";
    private static final long EXPIRATION_TIME = 3; // Cache válido por 3 dias

    public void saveExchangeRate(ExchangeRateEntity exchangeRateEntity) {
        redisTemplate.opsForValue().set(CACHE_KEY, exchangeRateEntity, Duration.ofDays(EXPIRATION_TIME));
    }

    public ExchangeRateEntity getExchangeRateFromCache() {
        Object cachedEntity = redisTemplate.opsForValue().get(CACHE_KEY);
        if (cachedEntity instanceof LinkedHashMap<?,?>) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return null;
        }
        return (cachedEntity instanceof ExchangeRateEntity) ? (ExchangeRateEntity) cachedEntity : null;
    }


    public void clearCache() {
        redisTemplate.delete(CACHE_KEY);
    }
}

