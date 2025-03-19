package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.util.constants.openapi.ResponseCodes;
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

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = OpenApiUserRestControllerConstants.OPERATION_SUMMARY,
            description = OpenApiUserRestControllerConstants.OPERATION_DESCRIPTION
    )
    @ApiResponse(responseCode = ResponseCodes.CREATED, description = OpenApiUserRestControllerConstants.RESPONSE_CREATED_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.BAD_REQUEST, description = OpenApiUserRestControllerConstants.RESPONSE_BAD_REQUEST_DESCRIPTION)
    @ApiResponse(responseCode = ResponseCodes.CONFLICT, description = OpenApiUserRestControllerConstants.RESPONSE_CONFLICT_DESCRIPTION)
    public ResponseEntity<Void> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveOwner(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/isOwner")
    public ResponseEntity<Boolean> findOwnerById(@RequestParam Long ownerId) {
        return ResponseEntity.ok(userHandler.isOwner(ownerId));
    }
}