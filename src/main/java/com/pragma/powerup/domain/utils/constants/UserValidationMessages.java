package com.pragma.powerup.domain.utils.constants;

public class UserValidationMessages {
    private UserValidationMessages() {}
    public static final String NAME_REQUIRED = "name is mandatory";
    public static final String LAST_NAME_REQUIRED = "last name is mandatory";
    public static final String DNI_REQUIRED = "dni is mandatory";
    public static final String DNI_FORMAT_INVALID = "dni is invalid";
    public static final String DNI_ALREADY_EXISTS = "DNI already exists";
    public static final String PHONE_REQUIRED = "phone is mandatory";
    public static final String PHONE_FORMAT_INVALID = "phone is invalid";
    public static final String DATE_OF_BIRTH_REQUIRED = "date of birth is mandatory";
    public static final String DATE_OF_BIRTH_FORMAT_INVALID = "invalid date format. It should be yyyy-MM-dd";
    public static final String MESSAGE_MINOR_AGE_RESTRICTION= "the user must be of legal age";
    public static final String PHONE_LENGTH_INVALID = "phone number cannot exceed 13 characters";
    public static final String EMAIL_REQUIRED = "email is mandatory";
    public static final String EMAIL_FORMAT_INVALID = "invalid email format";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String PASSWORD_REQUIRED = "password is mandatory";


}
