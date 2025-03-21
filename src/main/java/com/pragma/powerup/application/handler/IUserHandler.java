package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.ClientRequestDto;
import com.pragma.powerup.application.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveOwner(UserRequestDto userRequestDto);
    void saveEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId);
    void saveClient(ClientRequestDto clientRequestDto);
    Boolean isOwner(Long ownerId);

}
