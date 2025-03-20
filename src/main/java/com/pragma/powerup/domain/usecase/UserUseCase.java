package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.ResourceConflictException;
import com.pragma.powerup.domain.exception.ResourceNotFoundException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IEncryptionPersistencePort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.utils.constants.UserUseCaseConstants;
import com.pragma.powerup.domain.utils.validators.UserValidator;

import static com.pragma.powerup.domain.utils.constants.UserValidationMessages.DNI_ALREADY_EXISTS;
import static com.pragma.powerup.domain.utils.constants.UserValidationMessages.EMAIL_ALREADY_EXISTS;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IEncryptionPersistencePort encryptionPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final UserValidator userValidator;

    public UserUseCase(IUserPersistencePort userPersistencePort, IEncryptionPersistencePort encryptionServicePort, IRolePersistencePort rolePersistencePort, UserValidator userValidator) {
        this.userPersistencePort = userPersistencePort;
        this.encryptionPersistencePort = encryptionServicePort;
        this.rolePersistencePort = rolePersistencePort;
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
        saveUser(userModel, UserUseCaseConstants.USER_EMPLOYEE, false);
        userPersistencePort.saveEmployee(userModel, restaurantId);
    }

    @Override
    public Boolean isOwner(Long ownerId) {
        UserModel user = userPersistencePort.findUser(ownerId);
        if (user == null) {
            throw new ResourceNotFoundException(UserUseCaseConstants.USER_NOT_FOUND_MESSAGE);
        }
        return user.getRole().getName().equals(UserUseCaseConstants.USER_OWNER);
    }
}