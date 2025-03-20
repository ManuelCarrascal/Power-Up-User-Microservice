package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pragma.powerup.infrastructure.util.constants.UserEntityConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = UserEntityConstants.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = UserEntityConstants.COLUMN_NAME,
            nullable = false,
            length = UserEntityConstants.NAME_MAX_LENGTH
    )
    private String name;
    @Column(
            name = UserEntityConstants.COLUMN_LASTNAME,
            nullable = false, length = UserEntityConstants.LASTNAME_MAX_LENGTH
    )
    private String lastName;
    @Column(
            name = UserEntityConstants.COLUMN_DNI,
            nullable = false,
            length = UserEntityConstants.DNI_MAX_LENGTH,
            unique = true
    )
    private String dni;
    @Column(
            name = UserEntityConstants.COLUMN_PHONE,
            nullable = false,
            length = UserEntityConstants.PHONE_MAX_LENGTH
    )
    private String phone;
    @Column(
            name = UserEntityConstants.COLUMN_DATE_OF_BIRTH
    )
    private LocalDate dateOfBirth;
    @Column(
            name = UserEntityConstants.COLUMN_EMAIL,
            nullable = false,
            length = UserEntityConstants.EMAIL_MAX_LENGTH,
            unique = true
    )
    private String email;
    @Column(
            name = UserEntityConstants.COLUMN_PASSWORD,
            nullable = false
    )
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private RoleEntity role;
}
