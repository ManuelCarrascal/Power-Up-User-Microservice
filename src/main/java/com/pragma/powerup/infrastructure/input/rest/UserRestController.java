package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.ClientRequestDto;
import com.pragma.powerup.application.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.util.constants.openapi.ResponseCodes;
import com.pragma.powerup.infrastructure.util.constants.UserRestControllerConstants;
import com.pragma.powerup.infrastructure.util.constants.openapi.OpenApiUserRestControllerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = OpenApiUserRestControllerConstants.TAG, description = OpenApiUserRestControllerConstants.TAG_DESCRIPTION)
public class UserRestController {

    private final IUserHandler userHandler;
    @PostMapping("/owner")
    @PreAuthorize(UserRestControllerConstants.PREAUTHORIZE_ROLE_ADMIN)
    @Operation(
            summary = OpenApiUserRestControllerConstants.CREATE_OWNER_OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.CREATE_OWNER_OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.CREATED, description = OpenApiUserRestControllerConstants.RESPONSE_OWNER_CREATED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST)
    @ApiResponse(responseCode = ResponseCodes.CONFLICT, description = OpenApiUserRestControllerConstants.RESPONSE_CONFLICT)
    public ResponseEntity<Void> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveOwner(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @Operation(
            summary = OpenApiUserRestControllerConstants.CREATE_EMPLOYEE_OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.CREATE_EMPLOYEE_OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.CREATED, description = OpenApiUserRestControllerConstants.RESPONSE_EMPLOYEE_CREATED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST)
    @ApiResponse(responseCode = ResponseCodes.NOT_FOUND, description = OpenApiUserRestControllerConstants.RESPONSE_RESTAURANT_NOT_FOUND)
    public ResponseEntity<Void> createEmployee(
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto,
            @RequestParam Long restaurantId) {
        userHandler.saveEmployee(employeeRequestDto, restaurantId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/client")
    @Operation(
            summary = OpenApiUserRestControllerConstants.CREATE_CLIENT_OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.CREATE_CLIENT_OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.CREATED, description = OpenApiUserRestControllerConstants.RESPONSE_CLIENT_CREATED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST)
    @ApiResponse(responseCode = ResponseCodes.CONFLICT, description = OpenApiUserRestControllerConstants.RESPONSE_CONFLICT)
    public ResponseEntity<Void> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        userHandler.saveClient(clientRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/isOwner")
    @Operation(
            summary = OpenApiUserRestControllerConstants.VERIFY_OWNER_OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.VERIFY_OWNER_OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.OK, description = OpenApiUserRestControllerConstants.RESPONSE_OWNER_VERIFIED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST)
    public ResponseEntity<Boolean> findOwnerById(@RequestParam Long ownerId) {
        return ResponseEntity.ok(userHandler.isOwner(ownerId));
    }

    @GetMapping("/phone")
    @Operation(
            summary = OpenApiUserRestControllerConstants.GET_PHONE_OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.GET_PHONE_OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.OK, description = OpenApiUserRestControllerConstants.RESPONSE_PHONE_RETRIEVED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.NOT_FOUND, description = OpenApiUserRestControllerConstants.RESPONSE_USER_NOT_FOUND)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST)
    public ResponseEntity<String> getPhoneNumberById(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(userHandler.getPhoneNumberById(userId));
    }
}