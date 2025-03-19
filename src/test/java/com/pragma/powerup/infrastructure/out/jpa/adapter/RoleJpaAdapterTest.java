package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class RoleJpaAdapterTest {

    @Test
    void getRoleByName_shouldReturnRoleModel_whenRoleExists() {
        String roleName = "OWNER";
        RoleEntity mockRoleEntity = new RoleEntity(1L, roleName, null);
        RoleModel expectedRoleModel = new RoleModel(1L, roleName);

        IRoleRepository roleRepository = mock(IRoleRepository.class);
        IRoleEntityMapper roleEntityMapper = mock(IRoleEntityMapper.class);

        when(roleRepository.findByName(roleName)).thenReturn(mockRoleEntity);
        when(roleEntityMapper.toDomain(mockRoleEntity)).thenReturn(expectedRoleModel);

        RoleJpaAdapter roleJpaAdapter = new RoleJpaAdapter(roleRepository, roleEntityMapper);

        RoleModel result = roleJpaAdapter.getRoleByName(roleName);

        assertEquals(expectedRoleModel.getId(), result.getId());
        assertEquals(expectedRoleModel.getName(), result.getName());

        verify(roleRepository, times(1)).findByName(roleName);
        verify(roleEntityMapper, times(1)).toDomain(mockRoleEntity);
    }

    @Test
    void getRoleByName_shouldReturnNull_whenRoleDoesNotExist() {
        String roleName = "UNKNOWN";

        IRoleRepository roleRepository = mock(IRoleRepository.class);
        IRoleEntityMapper roleEntityMapper = mock(IRoleEntityMapper.class);

        when(roleRepository.findByName(roleName)).thenReturn(null);
        when(roleEntityMapper.toDomain(null)).thenReturn(null);

        RoleJpaAdapter roleJpaAdapter = new RoleJpaAdapter(roleRepository, roleEntityMapper);

        RoleModel result = roleJpaAdapter.getRoleByName(roleName);

        assertNull(result);

        verify(roleRepository, times(1)).findByName(roleName);
        verify(roleEntityMapper, times(1)).toDomain(null);
    }
}