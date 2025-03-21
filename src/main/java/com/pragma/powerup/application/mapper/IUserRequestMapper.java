package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.ClientRequestDto;
import com.pragma.powerup.application.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    @Mapping(target = "id", ignore = true)
    UserModel toUser (UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    UserModel toEmployee (EmployeeRequestDto employeeRequestDto);

    @Mapping(target = "id", ignore = true)
    UserModel toClient (ClientRequestDto clientRequestDto);
}
