package com.br.inter.domain.user.mapper;

import com.br.inter.domain.user.model.User;
import com.br.inter.infrastructure.repository.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setType(UserTypeMapper.determineUserType(user.getDocument()));
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setDocument(user.getDocument());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getName(),
                entity.getEmail(),
                entity.getDocument()
        );
    }
}
