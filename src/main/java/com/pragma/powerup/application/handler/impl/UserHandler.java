package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.ClientRequestDto;
import com.pragma.powerup.application.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    public void saveOwner(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveOwner(userModel);
    }

    @Override
    public void saveEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId) {
        UserModel userModel = userRequestMapper.toEmployee(employeeRequestDto);
        userServicePort.saveEmployee(userModel, restaurantId);
    }

    @Override
    public void saveClient(ClientRequestDto clientRequestDto) {
        UserModel userModel =userRequestMapper.toClient(clientRequestDto);
        userServicePort.saveClient(userModel);
    }

    @Override
    public Boolean isOwner(Long ownerId) {
        return userServicePort.isOwner(ownerId);
    }

    @Override
    public String getPhoneNumberById(Long userId) {
        return userServicePort.getPhoneNumberById(userId);
    }

}
