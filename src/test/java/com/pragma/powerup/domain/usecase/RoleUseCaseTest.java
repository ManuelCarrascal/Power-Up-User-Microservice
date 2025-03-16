package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RoleUseCaseTest {


    @Test
    void getRoleByName_WhenRoleExists_ShouldReturnRole() {
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        RoleUseCase roleUseCase = new RoleUseCase(rolePersistencePort);
        RoleModel expectedRole = new RoleModel(1L, "owner");
        when(rolePersistencePort.getRoleByName("owner")).thenReturn(expectedRole);

        RoleModel actualRole = roleUseCase.getRoleByName("owner");

        assertEquals(expectedRole, actualRole);
        verify(rolePersistencePort, times(1)).getRoleByName("owner");
    }

    @Test
    void getRoleByName_WhenRoleDoesNotExist_ShouldReturnNull() {
        IRolePersistencePort rolePersistencePort = mock(IRolePersistencePort.class);
        RoleUseCase roleUseCase = new RoleUseCase(rolePersistencePort);
        when(rolePersistencePort.getRoleByName(anyString())).thenReturn(null);

        RoleModel actualRole = roleUseCase.getRoleByName("UNKNOWN_ROLE");

        assertNull(actualRole);
        verify(rolePersistencePort, times(1)).getRoleByName("UNKNOWN_ROLE");
    }
}