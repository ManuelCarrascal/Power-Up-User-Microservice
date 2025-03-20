package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserServicePort {
    void saveOwner(UserModel userModel);
    void saveEmployee(UserModel userModel, Long restaurantId);
    Boolean isOwner(Long ownerId);
}
