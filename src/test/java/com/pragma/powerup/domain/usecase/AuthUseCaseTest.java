package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.domain.exception.AuthenticationException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPersistencePort;
import com.pragma.powerup.domain.utils.constants.AuthUseCaseConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IAuthPersistencePort authPersistencePort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private AuthRequestDto authRequestDto;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        authRequestDto = new AuthRequestDto("test@example.com", "password123");
        RoleModel roleModel = new RoleModel(1L, "ADMIN");
        userModel = new UserModel(1L, "test@example.com", "password123", roleModel);
    }

    @Test
    void login_ShouldReturnAuthResponseDto_WhenCredentialsAreValid() {
        when(authPersistencePort.validateCredentials(authRequestDto.getEmail(), authRequestDto.getPassword())).thenReturn(true);
        when(authPersistencePort.authenticate(authRequestDto.getEmail(), authRequestDto.getPassword())).thenReturn(userModel);
        when(authPersistencePort.generateToken(userModel)).thenReturn("validToken123");

        AuthResponseDto response = authUseCase.login(authRequestDto);

        assertNotNull(response);
        assertEquals("validToken123", response.getToken());
        verify(authPersistencePort, times(1)).validateCredentials(authRequestDto.getEmail(), authRequestDto.getPassword());
        verify(authPersistencePort, times(1)).authenticate(authRequestDto.getEmail(), authRequestDto.getPassword());
        verify(authPersistencePort, times(1)).generateToken(userModel);
    }

    @Test
    void login_ShouldThrowAuthenticationException_WhenCredentialsAreInvalid() {
        when(authPersistencePort.validateCredentials(authRequestDto.getEmail(), authRequestDto.getPassword())).thenReturn(false);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> authUseCase.login(authRequestDto));
        assertEquals(AuthUseCaseConstants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());

        verify(authPersistencePort, times(1)).validateCredentials(authRequestDto.getEmail(), authRequestDto.getPassword());

    }


    @Test
    void login_NullCredentials_ThrowsException() {
        IAuthPersistencePort mockAuthPersistencePort = mock(IAuthPersistencePort.class);


        when(mockAuthPersistencePort.validateCredentials(null, null)).thenReturn(false);

        AuthUseCase localAuthUseCase = new AuthUseCase(mockAuthPersistencePort);

        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> localAuthUseCase.login(authRequestDto)
        );

        assertEquals(AuthUseCaseConstants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(mockAuthPersistencePort, times(1)).validateCredentials(null, null);
        verify(authPersistencePort, never()).authenticate(anyString(), anyString());
        verify(authPersistencePort, never()).generateToken(any());
    }
}