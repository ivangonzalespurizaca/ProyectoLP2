package com.example.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Usuario")
    private Long id;
    
    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Contrasenia", nullable = false)
    private String contrasenia; 

    @Column(name = "Nombres", nullable = false)
    private String nombres;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "DNI", nullable = false, unique = true)
    private String dni;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Img_Perfil")
    private String imgPerfil;

    @Column(name = "Correo")
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Rol", nullable = false)
    private Roles rol;
}