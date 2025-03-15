package com.pragma.powerup.application.constants;

public class UserRequestDtoConstants {

    private UserRequestDtoConstants() {
    }

    public static final String NAME_REQUIRED_MESSAGE = "Name is mandatory";
    public static final String LAST_NAME_REQUIRED_MESSAGE = "Last Name is mandatory";
    public static final String DNI_REQUIRED_MESSAGE = "DNI is mandatory";
    public static final String PHONE_REQUIRED_MESSAGE = "Phone is mandatory";
    public static final String DATE_OF_BIRTH_REQUIRED_MESSAGE = "Date of Birth is mandatory";
    public static final String DATE_OF_BIRTH_PAST_MESSAGE = "Date of Birth cannot be in the future";
    public static final String EMAIL_REQUIRED_MESSAGE = "Email is mandatory";
    public static final String EMAIL_INVALID_MESSAGE = "Email invalid format";
    public static final String PASSWORD_REQUIRED_MESSAGE = "Password is mandatory";
    public static final String PHONE_REGEX = "^\\+?\\d{1,13}$";
    public static final String DNI_REGEX = "\\d+";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ROLE_REQUIRED_MESSAGE = "Role ID is mandatory";


}

