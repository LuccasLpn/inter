package com.br.inter.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CreateUserRequest {
    @NotNull(message = "name is not null")
    private String name;
    @NotNull(message = "email is not null")
    private String email;
    @NotNull(message = "document is not null")
    private String document;
    private String password;
}
