package com.br.inter.infrastructure.job;


import com.br.inter.infrastructure.repository.TransactionLimitRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TransactionLimitResetScheduler {

    private final TransactionLimitRepository transactionLimitRepository;

    public TransactionLimitResetScheduler(TransactionLimitRepository transactionLimitRepository) {
        this.transactionLimitRepository = transactionLimitRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDailyLimits() {
        transactionLimitRepository.resetDailyLimits();
    }
}
