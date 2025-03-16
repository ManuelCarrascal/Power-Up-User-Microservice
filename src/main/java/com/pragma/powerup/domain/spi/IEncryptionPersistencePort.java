package com.pragma.powerup.domain.spi;


public interface IEncryptionPersistencePort {
    String encodedPassword(String password);
}
