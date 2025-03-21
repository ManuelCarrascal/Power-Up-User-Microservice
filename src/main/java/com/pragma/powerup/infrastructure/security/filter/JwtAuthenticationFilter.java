package com.pragma.powerup.infrastructure.security.filter;

import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.security.jwt.JwtService;
import com.pragma.powerup.infrastructure.security.service.UserDetailImpl;
import com.pragma.powerup.infrastructure.util.constants.JwtAuthenticationFilterConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(JwtAuthenticationFilterConstants.HEADER_STRING);
        if(authorizationHeader == null || !authorizationHeader.startsWith(JwtAuthenticationFilterConstants.AUTH_HEADER_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorizationHeader.contains(JwtAuthenticationFilterConstants.SPLITERSTRING) ||
                authorizationHeader.split(JwtAuthenticationFilterConstants.SPLITERSTRING).length < JwtAuthenticationFilterConstants.TOKEN_INDEX_PARTS_MIN) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, JwtAuthenticationFilterConstants.ERROR_TOKEN_EMPTY);
            return;
        }

        String jwt = authorizationHeader.split(JwtAuthenticationFilterConstants.SPLITERSTRING)[JwtAuthenticationFilterConstants.TOKEN_INDEX_EXTRACTED];

        try{
            String username = jwtService.extractUsername(jwt);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userRepository.findByEmail(username)
                        .map(user -> new UserDetailImpl(user, roleRepository.findRolesByUserId(user.getId())))
                        .orElseThrow(() -> new UsernameNotFoundException(JwtAuthenticationFilterConstants.ERROR_USER_NOT_FOUND));

                if (jwtService.isTokenValid(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        }
        catch (ExpiredJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, JwtAuthenticationFilterConstants.ERROR_TOKEN_EXPIRED);
            return;
        }
        catch (UnsupportedJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, JwtAuthenticationFilterConstants.ERROR_TOKEN_UNSUPPORTED);
            return;
        }
        catch (MalformedJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, JwtAuthenticationFilterConstants.ERROR_TOKEN_MALFORMED);
            return;
        }
        filterChain.doFilter(request, response);

    }
}
