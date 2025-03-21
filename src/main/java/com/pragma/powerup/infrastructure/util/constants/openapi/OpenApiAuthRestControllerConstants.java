package com.pragma.powerup.infrastructure.util.constants.openapi;

public class OpenApiAuthRestControllerConstants {

    private OpenApiAuthRestControllerConstants() {}

    public static final String OPERATION_SUMMARY = "Authenticate user and return JWT token";
    public static final String OPERATION_DESCRIPTION = "This endpoint receives login credentials and returns a JWT token if authentication is successful.";

    public static final String GET_USER_OPERATION_SUMMARY = "Get authenticated user details";
    public static final String GET_USER_OPERATION_DESCRIPTION = "Returns the details of the currently authenticated user";

    public static final String RESPONSE_200_DESCRIPTION = "Authentication successful, JWT token returned";
    public static final String RESPONSE_400_DESCRIPTION = "Invalid request parameters";
    public static final String RESPONSE_401_DESCRIPTION = "Authentication failed";
    public static final String RESPONSE_USER_RETRIEVED = "User details retrieved successfully";

    public static final String APPLICATION_JSON = "application/json";
}