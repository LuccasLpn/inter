package com.br.inter.domain.transaction.gateway;

import com.br.inter.domain.transaction.model.ExchangeRate;

public interface ExchangeRateGateway {

    ExchangeRate fetchLatestExchangeRate();
}
