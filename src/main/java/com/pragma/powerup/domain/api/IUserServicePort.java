package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserServicePort {
    void saveOwner(UserModel userModel);
    void saveEmployee(UserModel userModel, Long restaurantId);
    void saveClient(UserModel userModel);
    Boolean isOwner(Long ownerId);
    String getPhoneNumberById(Long userId);
}
