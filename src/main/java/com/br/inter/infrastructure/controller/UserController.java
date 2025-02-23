package com.br.inter.infrastructure.controller;


import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.domain.user.model.User;
import com.br.inter.domain.user.usercase.CreateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUser createUser;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request, UriComponentsBuilder uriBuilder) {
        User user = createUser.execute(request);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
