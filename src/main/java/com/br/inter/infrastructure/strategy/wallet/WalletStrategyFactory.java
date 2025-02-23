package com.br.inter.infrastructure.strategy.wallet;

import java.util.HashMap;
import java.util.Map;

public class WalletStrategyFactory {

    private static final Map<String, WalletStrategy> strategies = new HashMap<>();

    static {
        strategies.put("BRL", new BRLWalletStrategy());
        strategies.put("USD", new USDWalletStrategy());
    }
    public static WalletStrategy getStrategy(String currency) {
        return strategies.getOrDefault(currency, new BRLWalletStrategy());
    }
}
