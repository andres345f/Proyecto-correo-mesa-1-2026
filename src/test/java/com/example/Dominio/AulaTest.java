package com.example.Dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AulaTest {

    @Test
    void crearAulaValida() {
        Aula aula = new Aula("Aula 101", "A101", 30);
        assertEquals("Aula 101", aula.getNombre());
        assertEquals("A101", aula.getCodigo());
        assertEquals(30, aula.getCapacidad());
    }

    @Test
    void nombreNoPuedeSerVacio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Aula("", "A101", 30));
        assertTrue(e.getMessage().contains("obligatorio"));
    }

    @Test
    void codigoNoPuedeSerVacio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Aula("Aula", "", 30));
        assertTrue(e.getMessage().contains("obligatorio"));
    }

    @Test
    void capacidadDebeSerPositiva() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Aula("Aula", "A101", 0));
        assertTrue(e.getMessage().contains("mayor a cero"));
    }
}
