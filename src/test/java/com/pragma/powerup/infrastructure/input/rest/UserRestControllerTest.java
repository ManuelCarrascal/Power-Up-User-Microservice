package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.domain.exception.ResourceNotFoundException;
import com.pragma.powerup.infrastructure.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        mockMvc.perform(post("/api/v1/user/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated());

        verify(userHandler).saveOwner(any(UserRequestDto.class));
    }

    @Test
    void createOwner_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();

        mockMvc.perform(post("/api/v1/user/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userHandler);
    }

    @Test
    void findOwnerById_ValidRequest_ShouldReturnOk() throws Exception {
        Long ownerId = 1L;
        when(userHandler.isOwner(ownerId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/isOwner")
                        .param("ownerId", ownerId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userHandler, times(1)).isOwner(ownerId);
    }

    @Test
    void findOwnerById_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/isOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userHandler);
    }

    @Test
    void findOwnerById_NonExistingOwner_ShouldReturnNotFound() throws Exception {
        Long nonExistingOwnerId = 999L;
        when(userHandler.isOwner(nonExistingOwnerId)).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/isOwner")
                        .param("ownerId", nonExistingOwnerId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userHandler, times(1)).isOwner(nonExistingOwnerId);
    }
}
