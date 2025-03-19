package com.pragma.powerup.domain.api;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;

public interface IAuthServicePort {
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
