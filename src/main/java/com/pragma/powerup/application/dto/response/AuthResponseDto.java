package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.pragma.powerup.application.util.constants.openapi.OpenApiAuthResponseConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = AUTH_RESPONSE_DTO_DESCRIPTION)
public class AuthResponseDto {

    @Schema(description = TOKEN_DESCRIPTION,
            example = TOKEN_EXAMPLE)
    private String token;
}