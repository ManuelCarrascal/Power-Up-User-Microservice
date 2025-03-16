package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserJpaAdapterTest {

    @Test
    void testSaveOwner_WhenMapperAndRepositoryCalledCorrectly() {
        IUserRepository userRepository = Mockito.mock(IUserRepository.class);
        IUserEntityMapper userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);

        UserModel userModel = new UserModel.Builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .dni("12345678")
                .phone("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("john.doe@example.com")
                .password("securepassword")
                .build();

        UserEntity userEntity = new UserEntity();
        Mockito.when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);

        userJpaAdapter.saveOwner(userModel);

        Mockito.verify(userEntityMapper).toEntity(userModel);
        Mockito.verify(userRepository).save(userEntity);
    }

    @Test
    void testExistsByEmail_WhenEmailExists_ReturnsTrue() {
        IUserRepository userRepository = Mockito.mock(IUserRepository.class);
        IUserEntityMapper userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);

        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userJpaAdapter.existsByEmail(email);

        assertTrue(result);
        Mockito.verify(userRepository).existsByEmail(email);
    }

    @Test
    void testExistsByEmail_WhenEmailDoesNotExist_ReturnsFalse() {
        IUserRepository userRepository = Mockito.mock(IUserRepository.class);
        IUserEntityMapper userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);

        String email = "nonexistent@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = userJpaAdapter.existsByEmail(email);

        assertFalse(result);
        Mockito.verify(userRepository).existsByEmail(email);
    }

    @Test
    void testExistsByDni_WhenDniExists_ReturnsTrue() {
        IUserRepository userRepository = Mockito.mock(IUserRepository.class);
        IUserEntityMapper userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);

        String dni = "123456789";
        Mockito.when(userRepository.existsByDni(dni)).thenReturn(true);

        boolean result = userJpaAdapter.existsByDni(dni);

        assertTrue(result);
    }

    @Test
    void testExistsByDni_WhenDniDoesNotExist_ReturnsFalse() {
        IUserRepository userRepository = Mockito.mock(IUserRepository.class);
        IUserEntityMapper userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);

        String dni = "987654321";
        Mockito.when(userRepository.existsByDni(dni)).thenReturn(false);

        boolean result = userJpaAdapter.existsByDni(dni);

        assertFalse(result);
    }
}