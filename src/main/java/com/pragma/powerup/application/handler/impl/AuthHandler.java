package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        return authServicePort.login(authRequestDto);
    }


}
