package com.pragma.powerup.domain.utils.validators;

import com.pragma.powerup.domain.exception.InvalidDataException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.utils.constants.UserValidationValues;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.pragma.powerup.domain.utils.constants.UserValidationMessages.*;
import static com.pragma.powerup.domain.utils.constants.UserValidationValues.*;

public class UserValidator {

    public void validate(UserModel userModel) {
        validName(userModel.getName());
        validLastName(userModel.getLastName());
        validDni(userModel.getDni());
        validPhone(userModel.getPhone());
        validEmail(userModel.getEmail());
        validPassword(userModel.getPassword());
        validAge(userModel.getDateOfBirth());
    }

    private static void validName(String name) {
        if(name == null || name.isEmpty()) {
            throw new InvalidDataException(NAME_REQUIRED);
        }
    }

    private static void validLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()) {
            throw new InvalidDataException(LAST_NAME_REQUIRED);
        }
    }

    private static void validDni(String dni) {
        if(dni == null || dni.isEmpty()) {
            throw new InvalidDataException(DNI_REQUIRED);
        }
        if(!dni.matches(UserValidationValues.NUMERIC_REGEX)){
            throw new InvalidDataException(DNI_FORMAT_INVALID);
        }
    }

    private static void validPhone(String phone){
        if(phone == null || phone.isEmpty()) {
            throw new InvalidDataException(PHONE_REQUIRED);
        }
        if(!phone.matches(PHONE_REGEX)){
            throw new InvalidDataException(PHONE_FORMAT_INVALID);
        }
        if(phone.length() > UserValidationValues.PHONE_MAX_LENGTH){
            throw new InvalidDataException(PHONE_LENGTH_INVALID);
        }
    }

    private static void validEmail(String email){
        if(email == null || email.isEmpty()) {
            throw new InvalidDataException(EMAIL_REQUIRED);
        }
        if(!email.matches(UserValidationValues.EMAIL_REGEX)){
            throw new InvalidDataException(EMAIL_FORMAT_INVALID);
        }
    }

    private static void validPassword(String password){
        if(password == null || password.isEmpty()) {
            throw new InvalidDataException(PASSWORD_REQUIRED);
        }
    }

    private static void validAge (LocalDate dateOfBirth){
        if(dateOfBirth == null){
                throw new InvalidDataException(DATE_OF_BIRTH_REQUIRED);
        }

        try{
            DATE_FORMATTER.format(dateOfBirth);
        } catch (DateTimeParseException e) {
            throw new InvalidDataException(DATE_OF_BIRTH_FORMAT_INVALID);
        }

        if(dateOfBirth.plusYears(MIN_AGE).isAfter(LocalDate.now())){
            throw new InvalidDataException(MESSAGE_MINOR_AGE_RESTRICTION);
        }
    }

}
