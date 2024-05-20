package com.webtech.onlineshop.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "UserAccount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Email is required and can not be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required and can not be empty")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Firstname is required and can not be empty")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Lastname is required and can not be empty")
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String role;

}
