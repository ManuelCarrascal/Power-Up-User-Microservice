package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private IUserHandler userHandler;

    @BeforeEach
    void setUp() {
        userHandler = Mockito.mock(IUserHandler.class);
        UserRestController userRestController = new UserRestController(userHandler);

        mockMvc = MockMvcBuilders.standaloneSetup(userRestController, new ControllerAdvisor()).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createOwner_ValidRequest_ShouldReturnCreated() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userRequestDto.setDni("12345678");
        userRequestDto.setEmail("jane.doe@example.org");
        userRequestDto.setLastName("Doe");
        userRequestDto.setName("Jane");
        userRequestDto.setPassword("securePassword");
        userRequestDto.setPhone("1234567890");
        userRequestDto.setRoleId(1L);

        doNothing().when(userHandler).saveOwner(any(UserRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated());

        verify(userHandler, times(1)).saveOwner(any(UserRequestDto.class));
    }

    @Test
    void createOwner_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userHandler);
    }
}
