package com.pragma.powerup.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pragma.powerup.application.util.constants.UserRequestDtoConstants;
import com.pragma.powerup.application.util.constants.openapi.OpenApiUserRequestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = OpenApiUserRequestConstants.USER_REQUEST_DESCRIPTION)
public class UserRequestDto {
    @NotBlank(message = UserRequestDtoConstants.NAME_REQUIRED_MESSAGE)
    @Schema(
            description = OpenApiUserRequestConstants.NAME_DESCRIPTION,
            example = OpenApiUserRequestConstants.NAME_EXAMPLE
    )
    private String name;

    @NotBlank(message = UserRequestDtoConstants.LAST_NAME_REQUIRED_MESSAGE)
    @Schema(
            description = OpenApiUserRequestConstants.LAST_NAME_DESCRIPTION,
            example = OpenApiUserRequestConstants.LAST_NAME_EXAMPLE
    )
    private String lastName;

    @NotBlank(message = UserRequestDtoConstants.DNI_REQUIRED_MESSAGE)
    @Pattern(regexp = UserRequestDtoConstants.DNI_REGEX)
    @Schema(
            description = OpenApiUserRequestConstants.DNI_DESCRIPTION,
            example = OpenApiUserRequestConstants.DNI_EXAMPLE
    )
    private String dni;

    @NotBlank(message = UserRequestDtoConstants.PHONE_REQUIRED_MESSAGE)
    @Pattern(regexp = UserRequestDtoConstants.PHONE_REGEX)
    @Schema(
            description = OpenApiUserRequestConstants.PHONE_DESCRIPTION,
            example = OpenApiUserRequestConstants.PHONE_EXAMPLE
    )
    private String phone;

    @NotNull(message = UserRequestDtoConstants.DATE_OF_BIRTH_REQUIRED_MESSAGE)
    @Past(message = UserRequestDtoConstants.DATE_OF_BIRTH_PAST_MESSAGE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UserRequestDtoConstants.DATE_FORMAT)
    @Schema(
            description = OpenApiUserRequestConstants.DATE_OF_BIRTH_DESCRIPTION,
            example = OpenApiUserRequestConstants.DATE_OF_BIRTH_EXAMPLE
    )
    private LocalDate dateOfBirth;

    @NotBlank(message = UserRequestDtoConstants.EMAIL_REQUIRED_MESSAGE)
    @Email(message = UserRequestDtoConstants.EMAIL_INVALID_MESSAGE)
    @Schema(
            description = OpenApiUserRequestConstants.EMAIL_DESCRIPTION,
            example = OpenApiUserRequestConstants.EMAIL_EXAMPLE
    )
    private String email;

    @NotBlank(message = UserRequestDtoConstants.PASSWORD_REQUIRED_MESSAGE)
    @Schema(
            description = OpenApiUserRequestConstants.PASSWORD_DESCRIPTION,
            example = OpenApiUserRequestConstants.PASSWORD_EXAMPLE
    )
    private String password;

    @Schema(
            description = OpenApiUserRequestConstants.ROLE_ID_DESCRIPTION,
            example = OpenApiUserRequestConstants.ROLE_ID_EXAMPLE
    )
    private Long roleId;
}