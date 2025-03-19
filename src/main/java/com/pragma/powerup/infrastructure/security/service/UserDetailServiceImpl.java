package com.pragma.powerup.infrastructure.security.service;

import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        UserEntity userFound = userRepository.findByEmail(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        List<RoleEntity> roles = roleRepository.findRolesByUserId(userFound.getId());
        return new UserDetailImpl(userFound, roles);
    }
}
