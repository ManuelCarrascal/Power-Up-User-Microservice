package com.pragma.powerup.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pragma.powerup.application.constants.UserRequestDtoConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = UserRequestDtoConstants.NAME_REQUIRED_MESSAGE)
    private String name;
    @NotBlank(message = UserRequestDtoConstants.LAST_NAME_REQUIRED_MESSAGE)
    private String lastName;
    @NotBlank(message = UserRequestDtoConstants.DNI_REQUIRED_MESSAGE)
    @Pattern(regexp = UserRequestDtoConstants.DNI_REGEX)
    private String dni;
    @NotBlank(message = UserRequestDtoConstants.PHONE_REQUIRED_MESSAGE)
    @Pattern( regexp = UserRequestDtoConstants.PHONE_REGEX)
    private String phone;
    @NotNull(message = UserRequestDtoConstants.DATE_OF_BIRTH_REQUIRED_MESSAGE)
    @Past(message = UserRequestDtoConstants.DATE_OF_BIRTH_PAST_MESSAGE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UserRequestDtoConstants.DATE_FORMAT)
    private LocalDate dateOfBirth;
    @NotBlank(message = UserRequestDtoConstants.EMAIL_REQUIRED_MESSAGE)
    @Email(message = UserRequestDtoConstants.EMAIL_INVALID_MESSAGE)
    private String email;
    @NotBlank(message = UserRequestDtoConstants.PASSWORD_REQUIRED_MESSAGE)
    private String password;
    @NotNull(message = UserRequestDtoConstants.ROLE_REQUIRED_MESSAGE)
    private Long roleId;
}
