package com.br.inter.infrastructure.repository.entity;

import com.br.inter.domain.transaction.model.ExchangeRate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exchange_rates")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    @JsonProperty("rate")
    private BigDecimal rate;

    @Column(nullable = false)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();


    public ExchangeRateEntity(ExchangeRate rate) {
        this.id = rate.getId();
        this.rate = rate.getRate();
        this.createdAt = rate.getCreatedAt();
        this.uuid = rate.getUuid();

    }
}
