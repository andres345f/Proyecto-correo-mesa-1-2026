package com.example.Dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void crearUsuarioValido() {
        Usuario u = new Usuario(1, "Juan Perez", "juan@test.com", "pass123");
        assertEquals("Juan Perez", u.getName());
        assertEquals("juan@test.com", u.getEmail());
        assertTrue(u.isActivo());
    }

    @Test
    void emailInvalido() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Usuario(1, "Juan", "no-email", "pass123"));
        assertTrue(e.getMessage().contains("Email"));
    }

    @Test
    void passwordMuyCorta() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Usuario(1, "Juan", "juan@test.com", "12345"));
        assertTrue(e.getMessage().contains("6 caracteres"));
    }
}
