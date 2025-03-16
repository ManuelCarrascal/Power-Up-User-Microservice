package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IRoleServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IEncryptionPersistencePort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.RoleUseCase;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.domain.utils.validators.UserValidator;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public IRolePersistencePort rolePersistencePort(){
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort(),encryptionPersistencePort(),rolePersistencePort(),userValidator());
    }

    @Bean
    public IEncryptionPersistencePort encryptionPersistencePort(){
        return  new PasswordEncoderAdapter(passwordEncoder);
    }


    @Bean
    public IRoleServicePort roleServicePort(){
        return new RoleUseCase(rolePersistencePort());
    }



}