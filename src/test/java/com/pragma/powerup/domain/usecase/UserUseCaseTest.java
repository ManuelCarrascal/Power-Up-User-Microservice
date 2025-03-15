package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ResourceConflictException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IEncryptionPersistencePort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.utils.constants.UserUseCaseConstants;
import com.pragma.powerup.domain.utils.validators.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserUseCaseTest {
    @Test
    void saveOwner_ShouldSaveOwnerSuccessfully_WhenDataIsValid() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("5551234")
                .dateOfBirth(java.time.LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RoleModel ownerRole = new RoleModel();
        ownerRole.setName(UserUseCaseConstants.USER_OWNER);

        when(userPersistencePort.existsByDni("12345678")).thenReturn(false);
        when(userPersistencePort.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(rolePersistencePort.getRoleByName(UserUseCaseConstants.USER_OWNER)).thenReturn(ownerRole);
        when(encryptionPersistencePort.encodedPassword("password123")).thenReturn("encryptedPassword123");

        userUseCase.saveOwner(userModel);

        verify(userValidator, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).existsByDni("12345678");
        verify(userPersistencePort, times(1)).existsByEmail("john.doe@example.com");
        verify(rolePersistencePort, times(1)).getRoleByName(UserUseCaseConstants.USER_OWNER);
        verify(encryptionPersistencePort, times(1)).encodedPassword("password123");
        verify(userPersistencePort, times(1)).saveOwner(userModel);
    }

    @Test
    void saveOwner_ShouldThrowResourceConflictException_WhenDniAlreadyExists() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("5551234")
                .dateOfBirth(java.time.LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .password("password123")
                .build();

        when(userPersistencePort.existsByDni("12345678")).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> userUseCase.saveOwner(userModel));
        verify(userPersistencePort, times(1)).existsByDni("12345678");
        verify(userPersistencePort, never()).saveOwner(any());
    }

    @Test
    void saveOwner_ShouldThrowResourceConflictException_WhenEmailAlreadyExists() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("5551234")
                .dateOfBirth(java.time.LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .password("password123")
                .build();

        when(userPersistencePort.existsByDni("12345678")).thenReturn(false);
        when(userPersistencePort.existsByEmail("john.doe@example.com")).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> userUseCase.saveOwner(userModel));
        verify(userPersistencePort, times(1)).existsByDni("12345678");
        verify(userPersistencePort, times(1)).existsByEmail("john.doe@example.com");
        verify(userPersistencePort, never()).saveOwner(any());
    }

    @Test
    void saveUser_ShouldSaveUserSuccessfully_WhenDataIsValid() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("Alice")
                .lastName("Smith")
                .dni("98765432")
                .phone("5555678")
                .dateOfBirth(java.time.LocalDate.of(1992, 5, 15))
                .email("alice.smith@example.com")
                .password("securePass123")
                .build();

        RoleModel userRole = new RoleModel();
        userRole.setName("USER_ROLE");

        when(userPersistencePort.existsByDni("98765432")).thenReturn(false);
        when(userPersistencePort.existsByEmail("alice.smith@example.com")).thenReturn(false);
        when(rolePersistencePort.getRoleByName("USER_ROLE")).thenReturn(userRole);
        when(encryptionPersistencePort.encodedPassword("securePass123")).thenReturn("encodedSecurePass123");

        userUseCase.saveUser(userModel, "USER_ROLE");

        verify(userValidator, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).existsByDni("98765432");
        verify(userPersistencePort, times(1)).existsByEmail("alice.smith@example.com");
        verify(rolePersistencePort, times(1)).getRoleByName("USER_ROLE");
        verify(encryptionPersistencePort, times(1)).encodedPassword("securePass123");
    }

    @Test
    void saveUser_ShouldThrowResourceConflictException_WhenDniAlreadyExists() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("Alice")
                .lastName("Smith")
                .dni("98765432")
                .phone("5555678")
                .dateOfBirth(java.time.LocalDate.of(1992, 5, 15))
                .email("alice.smith@example.com")
                .password("securePass123")
                .build();

        when(userPersistencePort.existsByDni("98765432")).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> userUseCase.saveUser(userModel, "USER_ROLE"));
        verify(userPersistencePort, times(1)).existsByDni("98765432");
        verify(userPersistencePort, never()).existsByEmail(any());
    }

    @Test
    void saveUser_ShouldThrowResourceConflictException_WhenEmailAlreadyExists() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        IEncryptionPersistencePort encryptionPersistencePort = mock(IEncryptionPersistencePort.class);
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        UserValidator userValidator = mock(UserValidator.class);
        UserUseCase userUseCase = new UserUseCase(userPersistencePort, encryptionPersistencePort, rolePersistencePort, userValidator);

        UserModel userModel = new UserModel.Builder()
                .name("Alice")
                .lastName("Smith")
                .dni("98765432")
                .phone("5555678")
                .dateOfBirth(java.time.LocalDate.of(1992, 5, 15))
                .email("alice.smith@example.com")
                .password("securePass123")
                .build();

        when(userPersistencePort.existsByDni("98765432")).thenReturn(false);
        when(userPersistencePort.existsByEmail("alice.smith@example.com")).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> userUseCase.saveUser(userModel, "USER_ROLE"));
        verify(userPersistencePort, times(1)).existsByDni("98765432");
        verify(userPersistencePort, times(1)).existsByEmail("alice.smith@example.com");
    }
}