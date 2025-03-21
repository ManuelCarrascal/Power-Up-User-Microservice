package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.application.util.constants.openapi.ResponseCodes;
import com.pragma.powerup.infrastructure.security.service.UserDetailImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pragma.powerup.infrastructure.util.constants.openapi.OpenApiAuthRestControllerConstants.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final IAuthHandler authHandler;

    @PostMapping("/login")
    @Operation(
            summary = OPERATION_SUMMARY,
            description = OPERATION_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = ResponseCodes.OK,
                    description = RESPONSE_200_DESCRIPTION,
                    content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = AuthResponseDto.class))),
            @ApiResponse(
                    responseCode = ResponseCodes.BAD_REQUEST,
                    description = RESPONSE_400_DESCRIPTION,
                    content = @Content(mediaType = APPLICATION_JSON)),
            @ApiResponse(
                    responseCode = ResponseCodes.UNAUTHORIZED,
                    description = RESPONSE_401_DESCRIPTION,
                    content = @Content(mediaType = APPLICATION_JSON))
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto response = authHandler.login(authRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/authenticated-user")
    @Operation(
            summary = GET_USER_OPERATION_SUMMARY,
            description = GET_USER_OPERATION_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = ResponseCodes.OK,
                    description = RESPONSE_USER_RETRIEVED,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = UserDetailImpl.class)
                    )
            ),
            @ApiResponse(
                    responseCode = ResponseCodes.UNAUTHORIZED,
                    description = RESPONSE_401_DESCRIPTION,
                    content = @Content(mediaType = APPLICATION_JSON)
            )
    })
    public UserDetailImpl getAuthenticatedUser() {
        return (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}