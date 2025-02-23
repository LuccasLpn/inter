package com.br.inter.infrastructure.client;

import com.br.inter.infrastructure.client.response.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "exchangeRateClient",
        url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata"
)
public interface ExchangeRateClient {

    @GetMapping("/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='{dataCotacao}'&$top={top}&$format={format}")
    ExchangeRateResponse getExchangeRate(@RequestParam("dataCotacao") String dataCotacao,
                                         @RequestParam("top") int top,
                                         @RequestParam("format") String format);
}
