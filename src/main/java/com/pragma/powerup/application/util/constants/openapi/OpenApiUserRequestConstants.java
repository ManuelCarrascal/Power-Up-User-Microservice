package com.pragma.powerup.application.util.constants.openapi;

public class OpenApiUserRequestConstants {

    private OpenApiUserRequestConstants() {
    }
    public static final String USER_REQUEST_DESCRIPTION = "Data required to create a new user.";
    public static final String NAME_DESCRIPTION = "User's first name (required)";
    public static final String LAST_NAME_DESCRIPTION = "User's last name (required)";
    public static final String DNI_DESCRIPTION = "Unique identifier (DNI) for the user, following the specified pattern.";
    public static final String PHONE_DESCRIPTION = "User's phone number, following a valid international format.";
    public static final String DATE_OF_BIRTH_DESCRIPTION = "User's birth date in the format yyyy-MM-dd. Must be a past date.";
    public static final String EMAIL_DESCRIPTION = "A valid email address for the user.";
    public static final String PASSWORD_DESCRIPTION = "Password for the user (required).";
    public static final String ROLE_ID_DESCRIPTION = "ID of the role assigned to the user.";
    public static final String NAME_EXAMPLE = "John";
    public static final String LAST_NAME_EXAMPLE = "Doe";
    public static final String DNI_EXAMPLE = "12345678";
    public static final String PHONE_EXAMPLE = "+123456789012";
    public static final String DATE_OF_BIRTH_EXAMPLE = "1985-05-15";
    public static final String EMAIL_EXAMPLE = "john.doe@example.com";
    public static final String PASSWORD_EXAMPLE = "strongP@ssw0rd";
    public static final String ROLE_ID_EXAMPLE = "1";
}