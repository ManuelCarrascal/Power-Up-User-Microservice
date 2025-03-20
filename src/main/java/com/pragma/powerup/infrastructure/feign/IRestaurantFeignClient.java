package com.pragma.powerup.infrastructure.feign;

import com.pragma.powerup.infrastructure.util.constants.IRestaurantFeignClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = IRestaurantFeignClientConstants.CLIENT_NAME,
        url = IRestaurantFeignClientConstants.BASE_URL,
        configuration = FeignConfig.class
)
public interface IRestaurantFeignClient {

    @PostMapping(IRestaurantFeignClientConstants.CREATE_EMPLOYEE_PATH)
    void createEmployee( @RequestParam(IRestaurantFeignClientConstants.RESTAURANT_ID_PARAM) Long restaurantId,@RequestParam(IRestaurantFeignClientConstants.USER_ID_PARAM) Long userId);

    @GetMapping(IRestaurantFeignClientConstants.IS_OWNER_OF_RESTAURANT_PATH)
    boolean isOwnerOfRestaurant(@RequestParam(IRestaurantFeignClientConstants.OWNER_ID_PARAM) Long ownerId, @RequestParam(IRestaurantFeignClientConstants.RESTAURANT_ID_PARAM) Long restaurantId);
}