package com.example.Dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GrupoPeriodoTest {

    @Test
    void crearGrupoPeriodoValido() {
        GrupoPeriodo gp = new GrupoPeriodo(1, 1, 1, 2, 35);
        assertEquals(1, gp.getGrupoId());
        assertEquals(1, gp.getPeriodoAcademicoId());
        assertEquals(Integer.valueOf(2), gp.getDocenteId());
        assertEquals(35, gp.getCupoMaximo());
    }

    @Test
    void cupoMaximoDebeSerPositivo() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new GrupoPeriodo(1, 1, 0, null, 0));
        assertTrue(e.getMessage().contains("mayor a cero"));
    }

    @Test
    void docentePuedeSerNull() {
        GrupoPeriodo gp = new GrupoPeriodo(1, 1, 1, null, 30);
        assertNull(gp.getDocenteId());
    }
}
