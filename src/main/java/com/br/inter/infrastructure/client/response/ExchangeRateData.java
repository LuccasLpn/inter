package com.br.inter.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateData {
    @JsonProperty("cotacaoCompra")
    private BigDecimal cotacaoCompra;
    @JsonProperty("cotacaoVenda")
    private BigDecimal cotacaoVenda;
    @JsonProperty("dataHoraCotacao")
    private String dataHoraCotacao;
}
