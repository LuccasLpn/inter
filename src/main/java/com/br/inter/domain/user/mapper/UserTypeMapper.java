package com.br.inter.domain.user.mapper;

import com.br.inter.infrastructure.repository.enums.UserType;

public class UserTypeMapper {

    public static UserType determineUserType(String document) {
        return (document != null && document.length() == 11) ? UserType.CPF : UserType.CNPJ;
    }
}
