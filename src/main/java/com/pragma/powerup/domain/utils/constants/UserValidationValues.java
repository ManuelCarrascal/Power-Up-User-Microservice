package com.pragma.powerup.domain.utils.constants;

import java.time.format.DateTimeFormatter;

public class UserValidationValues {
    private UserValidationValues() {}

    public static final int PHONE_MAX_LENGTH = 13;
    public static final String PHONE_REGEX ="^\\+?\\d{1,13}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String NUMERIC_REGEX = "^[0-9]+$";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final int MIN_AGE = 18;


}
