package com.example.Dominio;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanPagoTest {

    @Test
    void crearPlanPagoValido() {
        PlanPago plan = new PlanPago(1, 1, "Plan Estandar", "MENSUAL",
            new BigDecimal("500"), new BigDecimal("300"), 6);
        assertEquals("Plan Estandar", plan.getNombre());
        assertEquals(6, plan.getCantidadCuotas());
    }

    @Test
    void cantidadCuotasDebeSerPositiva() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new PlanPago(1, 1, "Plan", "MENSUAL",
                BigDecimal.ZERO, BigDecimal.ZERO, 0));
        assertTrue(e.getMessage().contains("mayor a cero"));
    }

    @Test
    void montoMatriculaNoPuedeSerNegativo() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new PlanPago(1, 1, "Plan", "MENSUAL",
                new BigDecimal("-1"), BigDecimal.ZERO, 1));
        assertTrue(e.getMessage().contains("negativo"));
    }
}
