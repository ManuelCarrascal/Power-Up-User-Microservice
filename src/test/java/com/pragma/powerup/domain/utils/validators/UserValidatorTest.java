package com.pragma.powerup.domain.utils.validators;

import com.pragma.powerup.domain.exception.InvalidDataException;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

 class UserValidatorTest {

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        UserModel userModel = new UserModel.Builder()
                .name(null)
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password("password")
                .build();

        UserValidator validator = new UserValidator();

        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
        Assertions.assertEquals("name is mandatory", exception.getMessage());
    }

     @Test
     void shouldThrowExceptionWhenNameIsEmpty() {
         UserValidator validator = new UserValidator();

         UserModel userModel = new UserModel.Builder()
                 .name("")
                 .build();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("name is mandatory", exception.getMessage());
     }

     @Test
    void shouldThrowExceptionWhenLastNameIsNull() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName(null)
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password("password")
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("last name is mandatory", exception.getMessage());
    }

     @Test
     void shouldThrowExceptionWhenLastNameIsEmpty() {
         UserModel userModel = new UserModel.Builder()
                 .name("John")
                 .lastName("").build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("last name is mandatory", exception.getMessage());
     }

     @Test
    void shouldThrowExceptionWhenDniIsInvalid() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("abc123")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password("password")
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("dni is invalid", exception.getMessage());
    }

     @Test
     void shouldThrowExceptionWhenDniIsNull() {
         UserModel userModel = new UserModel.Builder()
                 .name("John")
                 .lastName("Doe")
                 .dni(null)
                 .phone("1234567890")
                 .dateOfBirth(LocalDate.of(2000, 1, 1))
                 .email("test@example.com")
                 .password("password")
                 .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("dni is mandatory", exception.getMessage());
     }

     @Test
    void shouldThrowExceptionWhenPhoneIsTooLong() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("123456789012345")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password("password")
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("phone is invalid", exception.getMessage());
    }

     @Test
     void shouldThrowExceptionWhenPhoneIsNull() {
         UserModel userModel = new UserModel.Builder()
                 .name("John")
                 .lastName("Doe")
                 .dni("12345678")
                 .phone(null)
                 .dateOfBirth(LocalDate.of(2000, 1, 1))
                 .email("test@example.com")
                 .password("password")
                 .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("phone is mandatory", exception.getMessage());
     }

     @Test
     void shouldThrowExceptionWhenPhoneFormatIsInvalid() {
         UserModel userModel = new UserModel.Builder()
                 .name("John")
                 .lastName("Doe")
                 .dni("12345678")
                 .phone("phone#123").build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("phone is invalid", exception.getMessage());
     }

     @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("invalid-email")
                .password("password")
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("invalid email format", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email(null)
                .password("password")
                .build();

        UserValidator validator = new UserValidator();

        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
        Assertions.assertEquals("email is mandatory", exception.getMessage());
    }

     @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password(null)
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("password is mandatory", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        UserValidator validator = new UserValidator();

        UserModel userModel = new UserModel.Builder()
            .name("John")
            .lastName("Doe")
            .dni("12345678")
            .phone("1234567890")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .email("test@example.com")
            .password("")
            .build();

        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class,
            () -> validator.validate(userModel)
        );
        Assertions.assertEquals("password is mandatory", exception.getMessage());
    }

     @Test
     void shouldThrowExceptionWhenDateOfBirthIsInvalid() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(null)
                .email("test@example.com")
                .password("password")
                .build();

         UserValidator validator = new UserValidator();

         InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> validator.validate(userModel));
         Assertions.assertEquals("date of birth is mandatory", exception.getMessage());
    }


    @Test
    void shouldValidateSuccessfullyWhenDataIsValid() {
        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@example.com")
                .password("password")
                .build();

        UserValidator validator = new UserValidator();

        Assertions.assertDoesNotThrow(() -> validator.validate(userModel));
    }
}