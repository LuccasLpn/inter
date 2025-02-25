package com.br.inter.infrastructure.controller;

import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.application.response.WalletAddBalanceResponse;
import com.br.inter.domain.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class WalletControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private UUID userId;

    @BeforeEach
    void setup() {
        CreateUserRequest request = new CreateUserRequest(
                "Luccas Pereira Nunes",
                "luccas.lpn@outlook.com",
                "48188562822",
                "123456"
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserRequest> userEntity = new HttpEntity<>(request, headers);
        ResponseEntity<User> userResponse = restTemplate.exchange("/users", HttpMethod.POST, userEntity, User.class);
        Assertions.assertEquals(HttpStatus.CREATED, userResponse.getStatusCode());
        Assertions.assertNotNull(userResponse.getBody());
        Assertions.assertNotNull(userResponse.getBody().getId());
        userId = userResponse.getBody().getUuid();
    }

    @Test
    public void shouldAddBalanceSuccessfully() {
        CreateBalanceRequest balanceRequest = new CreateBalanceRequest(new BigDecimal("100.00"), "USD");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBalanceRequest> balanceEntity = new HttpEntity<>(balanceRequest, headers);
        ResponseEntity<WalletAddBalanceResponse> response = restTemplate.exchange(
                "/wallet/" + userId + "/balance",
                HttpMethod.POST,
                balanceEntity,
                WalletAddBalanceResponse.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getBalance());
        Assertions.assertEquals(response.getBody().getBalance(), new BigDecimal("100.00"));
    }

}