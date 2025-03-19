package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

public interface IAuthPersistencePort {
    UserModel authenticate(String email, String password);
    String generateToken(UserModel userModel);
    boolean validateCredentials(String email, String password);
}
