package com.pragma.powerup.infrastructure.util.constants;

public class JwtAuthenticationFilterConstants {
    public static final Long EXPIRATION_TIME = 30*60*1000L;
    public static final String BEARER = "Bearer";
    public static final String SPLITERSTRING = " ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ID = "Id";
    public static final String ROLE = "Role";
    public static final String PERMIT_ALL = "PermitAll()";
    public static final String HAS_ROLE_ADMIN = "hasRole('admin')";

    private JwtAuthenticationFilterConstants() {
    }
}
