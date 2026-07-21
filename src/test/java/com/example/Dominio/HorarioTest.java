package com.example.Dominio;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {

    @Test
    void crearHorarioValido() {
        Horario h = new Horario(1, 1, "LUNES",
            LocalTime.of(8, 0), LocalTime.of(10, 0), 1);
        assertEquals("LUNES", h.getDia());
        assertEquals(LocalTime.of(8, 0), h.getHoraInicio());
    }

    @Test
    void horaFinDebeSerPosteriorAInicio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Horario(1, 1, "LUNES",
                LocalTime.of(10, 0), LocalTime.of(8, 0), 1));
        assertTrue(e.getMessage().contains("posterior"));
    }

    @Test
    void diaNoPuedeSerVacio() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Horario(1, 1, "",
                LocalTime.of(8, 0), LocalTime.of(10, 0), 1));
        assertTrue(e.getMessage().contains("obligatorio"));
    }
}
