package com.example.lab6.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nombre;
    @Column
    private String correo;
    @Column
    private String psw;
    @Column
    private Integer rol_id;
    @Column
    private Integer enable;

}
