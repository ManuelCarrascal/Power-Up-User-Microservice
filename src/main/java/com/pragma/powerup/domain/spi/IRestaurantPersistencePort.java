package com.pragma.powerup.domain.spi;

public interface IRestaurantPersistencePort {
    boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId);
    void createEmployee(Long employeeId, Long restaurantId);
}
