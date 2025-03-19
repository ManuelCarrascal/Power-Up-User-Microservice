package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.application.util.constants.AuthRequestDtoConstants;
import com.pragma.powerup.application.util.constants.openapi.OpenApiAuthRequestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = OpenApiAuthRequestConstants.AUTH_REQUEST_DTO_DESCRIPTION)
public class AuthRequestDto {

    @NotBlank(message = AuthRequestDtoConstants.EMAIL_VALIDATION_MESSAGE)
    @Schema(description = OpenApiAuthRequestConstants.EMAIL_DESCRIPTION,
            example = OpenApiAuthRequestConstants.EMAIL_EXAMPLE,
            required = true)
    private String email;

    @NotBlank(message = AuthRequestDtoConstants.PASSWORD_VALIDATION_MESSAGE)
    @Schema(description = OpenApiAuthRequestConstants.PASSWORD_DESCRIPTION,
            example = OpenApiAuthRequestConstants.PASSWORD_EXAMPLE,
            required = true)
    private String password;

    public AuthRequestDto(String email, String password) {
    }
}