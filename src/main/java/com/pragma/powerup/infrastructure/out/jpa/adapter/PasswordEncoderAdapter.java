package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.spi.IEncryptionPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class PasswordEncoderAdapter implements IEncryptionPersistencePort {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
