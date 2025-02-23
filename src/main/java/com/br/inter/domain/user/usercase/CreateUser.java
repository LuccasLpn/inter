package com.br.inter.domain.user.usercase;

import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.domain.user.model.User;

public interface CreateUser {
    User execute(CreateUserRequest userRequest);
}
