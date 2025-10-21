package com.example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = "$2a$10$hAaFma0fp34pzWqujOf3/uD/VxWKTyDDaeOa5nhvy/cPh3AETh8hK";
        String passwordIngresado = "hola"; // la contraseña que escribes en el login

        boolean matches = encoder.matches(passwordIngresado, hash);
        System.out.println("Coincide contraseña? " + matches);
    }
}