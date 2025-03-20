package com.pragma.powerup.infrastructure.feign;

import com.pragma.powerup.infrastructure.util.constants.IRestaurantFeignClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = IRestaurantFeignClientConstants.CLIENT_NAME,
        url = IRestaurantFeignClientConstants.BASE_URL,
        configuration = FeignConfig.class
)
public interface IRestaurantFeignClient {

    @PostMapping(IRestaurantFeignClientConstants.CREATE_EMPLOYEE_PATH)
    void createEmployee(@RequestParam Long userId, @RequestParam Long restaurantId);
}