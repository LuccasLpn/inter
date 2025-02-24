package com.br.inter.domain.user.fixture;

import com.br.inter.domain.user.model.User;

public class UserFixture {

    public static User createMockUser() {
        return User.createNewUser("Fulano de Tal", "fulano@example.com", "senha123", "12345678901");
    }
}
