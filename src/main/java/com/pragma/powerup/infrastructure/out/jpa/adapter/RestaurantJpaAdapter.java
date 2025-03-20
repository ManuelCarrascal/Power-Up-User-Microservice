package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.feign.IRestaurantFeignClient;

public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantFeignClient  restaurantFeignClient;

    public RestaurantJpaAdapter(IRestaurantFeignClient restaurantFeignClient) {
        this.restaurantFeignClient = restaurantFeignClient;
    }

    @Override
    public boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId) {
        return restaurantFeignClient.isOwnerOfRestaurant(ownerId, restaurantId);
    }

    @Override
    public void createEmployee(Long employeeId, Long restaurantId) {
        restaurantFeignClient.createEmployee(restaurantId, employeeId);
    }

}
