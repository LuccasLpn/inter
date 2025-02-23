package com.br.inter.infrastructure.job;

import com.br.inter.infrastructure.cache.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheCleanupScheduler {

    private final RedisCache redisCache;

    @Scheduled(cron = "0 0 0 * * MON")
    public void clearExchangeRateCache() {
        redisCache.clearCache();
    }
}
