package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ResourceConflictException;
import com.pragma.powerup.domain.exception.ResourceNotFoundException;
import com.pragma.powerup.domain.exception.UnauthorizedActionException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.utils.constants.UserUseCaseConstants;
import com.pragma.powerup.domain.utils.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IEncryptionPersistencePort encryptionPersistencePort;
    @Mock
    private IRolePersistencePort rolePersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IAuthPersistencePort authPersistencePort;
    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;
    private RoleModel ownerRole;
    private RoleModel employeeRole;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setEmail("test@example.com");
        userModel.setDni("12345678");
        userModel.setPassword("password123");

        ownerRole = new RoleModel(1L, UserUseCaseConstants.USER_OWNER);
        employeeRole = new RoleModel(2L, UserUseCaseConstants.USER_EMPLOYEE);
    }

    @Test
    void saveUser_ShouldSaveSuccessfully() {
        when(userPersistencePort.existsByDni(anyString())).thenReturn(false);
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(anyString())).thenReturn(ownerRole);
        when(encryptionPersistencePort.encodedPassword(anyString())).thenReturn("encodedPassword");

        userUseCase.saveUser(userModel, UserUseCaseConstants.USER_OWNER, true);

        verify(userValidator).validate(userModel, true);
        verify(encryptionPersistencePort).encodedPassword("password123");
        assertEquals("encodedPassword", userModel.getPassword());
        assertEquals(ownerRole, userModel.getRole());
    }

    @Test
    void saveUser_ShouldThrowException_WhenEmailExists() {
        when(userPersistencePort.existsByDni(anyString())).thenReturn(false);
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ResourceConflictException.class,
                () -> userUseCase.saveUser(userModel, UserUseCaseConstants.USER_OWNER, true));
    }

    @Test
    void saveOwner_ShouldSaveSuccessfully() {
        when(userPersistencePort.existsByDni(anyString())).thenReturn(false);
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(UserUseCaseConstants.USER_OWNER)).thenReturn(ownerRole);
        when(encryptionPersistencePort.encodedPassword(anyString())).thenReturn("encodedPassword");

        userUseCase.saveOwner(userModel);

        verify(userPersistencePort).saveOwner(userModel);
        assertEquals("encodedPassword", userModel.getPassword());
    }

    @Test
    void saveEmployee_ShouldSaveSuccessfully() {
        Long restaurantId = 1L;
        Long ownerId = 2L;

        when(authPersistencePort.getAuthenticatedUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.isOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(true);
        when(userPersistencePort.existsByDni(anyString())).thenReturn(false);
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(UserUseCaseConstants.USER_EMPLOYEE)).thenReturn(employeeRole);
        when(encryptionPersistencePort.encodedPassword(anyString())).thenReturn("encodedPassword");

        userUseCase.saveEmployee(userModel, restaurantId);

        verify(userPersistencePort).saveEmployee(userModel, restaurantId);
        verify(restaurantPersistencePort).createEmployee(userModel.getId(), restaurantId);
    }

    @Test
    void isOwner_ShouldReturnTrue() {
        Long userId = 1L;
        userModel.setRole(ownerRole);

        when(userPersistencePort.findUser(userId)).thenReturn(userModel);

        assertTrue(userUseCase.isOwner(userId));
    }

    @Test
    void isOwner_ShouldReturnFalse() {
        Long userId = 1L;
        userModel.setRole(employeeRole);

        when(userPersistencePort.findUser(userId)).thenReturn(userModel);

        assertFalse(userUseCase.isOwner(userId));
    }

    @Test
    void isOwner_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        when(userPersistencePort.findUser(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userUseCase.isOwner(userId));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenUnauthorized() {
        Long restaurantId = 1L;
        Long ownerId = 2L;

        when(authPersistencePort.getAuthenticatedUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.isOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(false);

        assertThrows(UnauthorizedActionException.class,
                () -> userUseCase.saveEmployee(userModel, restaurantId));

        verifyNoInteractions(userPersistencePort);
        verifyNoInteractions(encryptionPersistencePort);
        verifyNoInteractions(rolePersistencePort);
    }

    @Test
    void saveUser_ShouldThrowException_WhenDniExists() {
        when(userPersistencePort.existsByDni(anyString())).thenReturn(true);

        assertThrows(ResourceConflictException.class,
                () -> userUseCase.saveUser(userModel, UserUseCaseConstants.USER_OWNER, true));

        verifyNoInteractions(encryptionPersistencePort);
        verifyNoInteractions(rolePersistencePort);
    }
}