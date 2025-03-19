package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.domain.api.IAuthServicePort;
import com.pragma.powerup.domain.exception.AuthenticationException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPersistencePort;
import com.pragma.powerup.domain.utils.constants.AuthUseCaseConstants;

public class AuthUseCase implements IAuthServicePort {

    private final IAuthPersistencePort authPersistencePort;

    public AuthUseCase(IAuthPersistencePort authPersistencePort) {
        this.authPersistencePort = authPersistencePort;
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto){
        if (!authPersistencePort.validateCredentials(authRequestDto.getEmail(), authRequestDto.getPassword())) {
            throw new AuthenticationException(AuthUseCaseConstants.INVALID_CREDENTIALS_MESSAGE);
        }
        UserModel userModel = authPersistencePort.authenticate(authRequestDto.getEmail(), authRequestDto.getPassword());
        String token = authPersistencePort.generateToken(userModel);

        return new AuthResponseDto(token);
    }

}
