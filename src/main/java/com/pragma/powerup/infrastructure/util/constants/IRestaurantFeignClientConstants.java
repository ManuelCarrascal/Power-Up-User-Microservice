package com.pragma.powerup.infrastructure.util.constants;

public class IRestaurantFeignClientConstants {

    private IRestaurantFeignClientConstants() {
    }

    public static final String CLIENT_NAME = "power-up-plaza-microservice";
    public static final String BASE_URL = "http://localhost:8082/api/v1";

    public static final String CREATE_EMPLOYEE_PATH = "/restaurant/create-employee";
}