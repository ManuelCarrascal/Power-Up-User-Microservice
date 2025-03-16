package com.pragma.powerup.infrastructure.util.constants.openapi;

public class OpenApiUserRestControllerConstants {

    private OpenApiUserRestControllerConstants() {}

    public static final String TAG = "User API";
    public static final String TAG_DESCRIPTION = "Endpoints to manage users.";
    public static final String OPERATION_SUMMARY = "Create a new user";
    public static final String OPERATION_DESCRIPTION = "Endpoint to create a new user, providing the required information.";
    public static final String RESPONSE_CREATED_DESCRIPTION = "User successfully created.";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION = "Bad request. One or more fields are invalid.";
    public static final String RESPONSE_CONFLICT_DESCRIPTION = "Conflict: DNI or Email already exists";

}
