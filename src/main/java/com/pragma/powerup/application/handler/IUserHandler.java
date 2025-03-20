package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveOwner(UserRequestDto userRequestDto);
    void saveEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId);
    Boolean isOwner(Long ownerId);

}
