package com.br.inter.infrastructure.controller;

import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.WalletAddBalanceResponse;
import com.br.inter.domain.user.model.User;
import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.infrastructure.repository.enums.TransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class TransactionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String fromWalletUuid;
    private String toWalletUuid;

    @BeforeEach
    void setup() {
        UUID fromUserId = createUser("Alice", "alice@email.com", "48188562823");
        UUID toUserId = createUser("Bob", "bob@email.com", "48188562822");
        fromWalletUuid = getWallet(fromUserId, "BRL");
        toWalletUuid = getWallet(toUserId, "USD");
        addBalance(fromUserId, new BigDecimal("200.00"));
    }

    @Test
    public void shouldTransferBalanceSuccessfully() {
        RemittanceRequest remittanceRequest = new RemittanceRequest();
        remittanceRequest.setFromWalletUuid(fromWalletUuid);
        remittanceRequest.setToWalletUuid(toWalletUuid);
        remittanceRequest.setAmount(new BigDecimal("50.00"));
        remittanceRequest.setTransactionType(TransactionType.TRANSFER);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RemittanceRequest> entity = new HttpEntity<>(remittanceRequest, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                "/transactions/remittance",
                HttpMethod.POST,
                entity,
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    private UUID createUser(String name, String email, String document) {
        CreateUserRequest request = new CreateUserRequest(name, email, document, "123456789");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<User> response = restTemplate.exchange("/users", HttpMethod.POST, entity, User.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        return response.getBody().getUuid();
    }

    private String getWallet(UUID userId, String currency) {
        ResponseEntity<List<Wallet>> response = restTemplate.exchange(
                "/wallet/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Wallet>>() {}
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet wallet = Objects.requireNonNull(response.getBody())
                .stream()
                .filter(w -> w.getBalanceType().name().equalsIgnoreCase(currency))
                .findFirst()
                .orElse(null);
        return wallet != null ? wallet.getUuid().toString() : null;
    }



    private void addBalance(UUID userId, BigDecimal amount) {
        CreateBalanceRequest request = new CreateBalanceRequest(amount, "BRL");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBalanceRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<WalletAddBalanceResponse> response = restTemplate.exchange(
                "/wallet/" + userId + "/balance",
                HttpMethod.POST,
                entity,
                WalletAddBalanceResponse.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}