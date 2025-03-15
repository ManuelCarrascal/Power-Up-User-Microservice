package com.pragma.powerup.infrastructure.out.jpa.adapter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PasswordEncoderAdapterTest {

    @ParameterizedTest
    @CsvSource({
            "test45478, encodedPassword",
            "'', encodedEmptyPassword",
            "null, encodedNullPassword"
    })
    void testEncodedPassword_ShouldReturnEncodedPassword(String rawPassword, String expectedEncodedPassword) {
        PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
        when(mockPasswordEncoder.encode(rawPassword)).thenReturn(expectedEncodedPassword);

        PasswordEncoderAdapter passwordEncoderAdapter = new PasswordEncoderAdapter(mockPasswordEncoder);

        String result = passwordEncoderAdapter.encodedPassword(rawPassword);

        assertEquals(expectedEncodedPassword, result);
        Mockito.verify(mockPasswordEncoder, Mockito.times(1)).encode(rawPassword);
    }
}