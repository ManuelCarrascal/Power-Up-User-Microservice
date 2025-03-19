package com.pragma.powerup.infrastructure.util.constants;

public class JwtAuthenticationFilterConstants {
    public static final String SPLITERSTRING = " ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static final String ERROR_TOKEN_EMPTY = "Token is empty";
    public static final String ERROR_TOKEN_EXPIRED = "Token is expired";
    public static final String ERROR_TOKEN_UNSUPPORTED = "Token is unsupported";
    public static final String ERROR_TOKEN_MALFORMED = "Token is malformed";
    public static final String ERROR_USER_NOT_FOUND = "User not found";

    public static final int TOKEN_INDEX_PARTS_MIN = 2;
    public static final int TOKEN_INDEX_EXTRACTED = 1;





    private JwtAuthenticationFilterConstants() {
    }
}
