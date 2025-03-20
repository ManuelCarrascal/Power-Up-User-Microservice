package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.exception.CustomException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.util.constants.UserJpaAdapterConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void saveOwner(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userRepository.save(userEntity);
    }

    @Override
    public void saveEmployee(UserModel userModel, Long restaurantId) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        userModel.setId(savedUserEntity.getId());
    }

    @Override
    public boolean existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserModel findUser(Long ownerId) {
        return userRepository.findById(ownerId)
                .map(userEntityMapper::toModel)
                .orElse(null);
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::getId)
                .orElseThrow(() -> new CustomException(UserJpaAdapterConstants.USER_NOT_FOUND_BY_EMAIL + email));
    }
}