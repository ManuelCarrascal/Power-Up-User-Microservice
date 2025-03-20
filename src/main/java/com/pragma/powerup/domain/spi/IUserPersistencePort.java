package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserPersistencePort {

    void saveOwner(UserModel userModel);

    void saveEmployee(UserModel userModel, Long restaurantId);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);

    UserModel findUser(Long ownerId);

    Long findUserIdByEmail(String email);
}
