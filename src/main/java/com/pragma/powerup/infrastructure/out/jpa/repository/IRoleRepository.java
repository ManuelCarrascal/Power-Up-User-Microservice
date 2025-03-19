package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);

    @Query("SELECT r FROM RoleEntity r JOIN r.users u WHERE u.id = :userId")
    List<RoleEntity> findRolesByUserId(@Param("userId") Long userId);
}
