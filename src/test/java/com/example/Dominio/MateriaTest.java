package com.example.Dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MateriaTest {

    @Test
    void crearMateriaValida() {
        Materia m = new Materia("INF101", "Programación I");
        assertEquals("INF101", m.getCodigo());
        assertEquals("Programación I", m.getNombre());
    }

    @Test
    void codigoNoPuedeSerVacio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Materia("", "Programación"));
        assertTrue(e.getMessage().contains("obligatorio"));
    }

    @Test
    void nombreNoPuedeSerVacio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Materia("INF101", ""));
        assertTrue(e.getMessage().contains("obligatorio"));
    }
}
