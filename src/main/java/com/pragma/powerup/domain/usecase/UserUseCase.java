package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.ResourceConflictException;
import com.pragma.powerup.domain.exception.ResourceNotFoundException;
import com.pragma.powerup.domain.exception.UnauthorizedActionException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.utils.constants.UserUseCaseConstants;
import com.pragma.powerup.domain.utils.validators.UserValidator;

import static com.pragma.powerup.domain.utils.constants.UserValidationMessages.DNI_ALREADY_EXISTS;
import static com.pragma.powerup.domain.utils.constants.UserValidationMessages.EMAIL_ALREADY_EXISTS;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IEncryptionPersistencePort encryptionPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IAuthPersistencePort authPersistencePort;
    private final UserValidator userValidator;

    public UserUseCase(IUserPersistencePort userPersistencePort, IEncryptionPersistencePort encryptionServicePort, IRolePersistencePort rolePersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IAuthPersistencePort authPersistencePort, UserValidator userValidator) {
        this.userPersistencePort = userPersistencePort;
        this.encryptionPersistencePort = encryptionServicePort;
        this.rolePersistencePort = rolePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authPersistencePort = authPersistencePort;
        this.userValidator = userValidator;
    }

    void saveUser(UserModel userModel, String roleName, boolean validateDateOfBirth) {
        userValidator.validate(userModel, validateDateOfBirth);
        if (userPersistencePort.existsByDni(userModel.getDni())) {
            throw new ResourceConflictException(DNI_ALREADY_EXISTS);
        }
        if (userPersistencePort.existsByEmail(userModel.getEmail())) {
            throw new ResourceConflictException(EMAIL_ALREADY_EXISTS);
        }
        RoleModel role = rolePersistencePort.getRoleByName(roleName);
        userModel.setRole(role);
        String encodedPassword = encryptionPersistencePort.encodedPassword(userModel.getPassword());
        userModel.setPassword(encodedPassword);
    }

    public void saveOwner(UserModel userModel) {
        saveUser(userModel, UserUseCaseConstants.USER_OWNER, true);
        userPersistencePort.saveOwner(userModel);
    }

    @Override
    public void saveEmployee(UserModel userModel, Long restaurantId) {
        Long ownerId=authPersistencePort.getAuthenticatedUserId();
        boolean isOwnerOfRestaurant = restaurantPersistencePort.isOwnerOfRestaurant(ownerId, restaurantId);
        if (!isOwnerOfRestaurant) {
            throw new UnauthorizedActionException(UserUseCaseConstants.UNAUTHORIZED_ACTION_MESSAGE);
        }
        saveUser(userModel, UserUseCaseConstants.USER_EMPLOYEE, false);
        userPersistencePort.saveEmployee(userModel, restaurantId);
        restaurantPersistencePort.createEmployee(userModel.getId(), restaurantId);
    }

    @Override
    public void saveClient(UserModel userModel) {
        saveUser(userModel, UserUseCaseConstants.USER_CLIENT, false);
        userPersistencePort.saveClient(userModel);
    }

    @Override
    public Boolean isOwner(Long ownerId) {
        UserModel user = userPersistencePort.findUser(ownerId);
        if (user == null) {
            throw new ResourceNotFoundException(UserUseCaseConstants.USER_NOT_FOUND_MESSAGE);
        }
        return user.getRole().getName().equals(UserUseCaseConstants.USER_OWNER);
    }

    @Override
    public String getPhoneNumberById(Long userId) {
        UserModel user = userPersistencePort.findUser(userId);
        if (user == null) {
            throw new ResourceNotFoundException(UserUseCaseConstants.USER_NOT_FOUND_MESSAGE);
        }
        return user.getPhone();
    }
}