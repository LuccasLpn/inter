package com.br.inter.infrastructure.controller;

import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class UserControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest(
                "Luccas Pereira Nunes",
                "luccas.lpn@outlook.com",
                "48188562822",
                "123456"
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<User> response = restTemplate.exchange("/users", HttpMethod.POST, entity, User.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getId());
    }

}