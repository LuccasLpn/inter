package com.br.inter.infrastructure.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public static String encryptPassword(String password) {
        return password != null ? BCrypt.hashpw(password, BCrypt.gensalt()) : null;
    }
}
