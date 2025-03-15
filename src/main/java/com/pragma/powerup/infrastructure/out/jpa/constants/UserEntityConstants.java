package com.pragma.powerup.infrastructure.out.jpa.constants;

public class UserEntityConstants {

    private UserEntityConstants() {
    }
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME = "name";
    public static final int NAME_MAX_LENGTH = 50;
    public static final String COLUMN_LASTNAME = "lastname";
    public static final int LASTNAME_MAX_LENGTH = 50;
    public static final String COLUMN_DNI = "dni";
    public static final int DNI_MAX_LENGTH = 10;
    public static final String COLUMN_PHONE = "phone";
    public static final int PHONE_MAX_LENGTH = 13;
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    public static final String COLUMN_EMAIL = "email";
    public static final int EMAIL_MAX_LENGTH = 254;
    public static final String COLUMN_PASSWORD = "password";


}
