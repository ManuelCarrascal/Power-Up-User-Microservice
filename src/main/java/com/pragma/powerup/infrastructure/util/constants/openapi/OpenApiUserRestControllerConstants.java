package com.pragma.powerup.infrastructure.util.constants.openapi;

public class OpenApiUserRestControllerConstants {

    private OpenApiUserRestControllerConstants() {}

    public static final String TAG = "User API";
    public static final String TAG_DESCRIPTION = "Endpoints to manage users.";

    public static final String CREATE_OWNER_OPERATION_SUMMARY = "Create a new Owner";
    public static final String CREATE_OWNER_OPERATION_DESCRIPTION = "Endpoint to create an owner, providing the required information.";
    public static final String RESPONSE_OWNER_CREATED_DESCRIPTION = "Owner successfully created.";
    public static final String RESPONSE_BAD_REQUEST = "Bad request. One or more fields are invalid.";
    public static final String RESPONSE_CONFLICT = "Conflict: DNI or Email already exists.";

    public static final String CREATE_EMPLOYEE_OPERATION_SUMMARY = "Create a new Employee";
    public static final String CREATE_EMPLOYEE_OPERATION_DESCRIPTION = "Endpoint to create a new employee and assign them to a specific restaurant.";
    public static final String RESPONSE_EMPLOYEE_CREATED_DESCRIPTION = "Employee successfully created.";
    public static final String RESPONSE_RESTAURANT_NOT_FOUND = "The specified restaurant was not found.";

    public static final String VERIFY_OWNER_OPERATION_SUMMARY = "Verify if a user is an Owner";
    public static final String VERIFY_OWNER_OPERATION_DESCRIPTION = "Endpoint to check if the user with the specified ID has the Owner role.";
    public static final String RESPONSE_OWNER_VERIFIED_DESCRIPTION = "Successful verification. Returns true if the user is an Owner, false otherwise.";

    public static final String CREATE_CLIENT_OPERATION_SUMMARY = "Create a new client";
    public static final String CREATE_CLIENT_OPERATION_DESCRIPTION = "Creates a new client user in the system";
    public static final String RESPONSE_CLIENT_CREATED_DESCRIPTION = "Client created successfully";
}