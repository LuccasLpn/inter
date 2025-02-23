package com.br.inter.domain.user.gateway;

import com.br.inter.domain.user.model.User;

public interface CreateUserGateway {
    User save(User user);
}
