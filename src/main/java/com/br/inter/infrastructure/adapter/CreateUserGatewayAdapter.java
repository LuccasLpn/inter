package com.br.inter.infrastructure.adapter;

import com.br.inter.domain.user.gateway.CreateUserGateway;
import com.br.inter.domain.user.model.User;
import com.br.inter.infrastructure.factory.UserFactory;
import com.br.inter.infrastructure.repository.UserRepository;
import com.br.inter.infrastructure.repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserGatewayAdapter implements CreateUserGateway {

    private final UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public User save(User user) {
        UserEntity userEntity = UserFactory.createUserEntity(user);
        UserEntity userSaved = userRepository.save(userEntity);
        user.setId(userSaved.getId());
        return user;
    }

}
