package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.exception.AuthenticationException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.security.jwt.JwtService;
import com.pragma.powerup.infrastructure.util.constants.AuthAdapterConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

public class AuthAdapter implements IAuthPersistencePort {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleEntityMapper roleEntityMapper;

    public AuthAdapter(AuthenticationManager authenticationManager, JwtService jwtService, IUserRepository userRepository, PasswordEncoder passwordEncoder, IRoleEntityMapper roleEntityMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public UserModel authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            throw new AuthenticationException(AuthAdapterConstants.AUTHENTICATION_FAILED_MESSAGE);
        }

        return userRepository.findByEmail(email)
                .map(user -> new UserModel(user.getId(), user.getEmail(), user.getPassword(), roleEntityMapper.toDomain(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException(AuthAdapterConstants.USER_NOT_FOUND_MESSAGE));
    }


    @Override
    public String generateToken(UserModel userModel) {
        return jwtService.generateToken(userModel, Map.of(
                AuthAdapterConstants.ROLE_CLAIM_KEY, userModel.getRole()
        ));
    }


    @Override
    public boolean validateCredentials(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

}