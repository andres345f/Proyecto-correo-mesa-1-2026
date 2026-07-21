package com.example.Aplicacion;

import com.example.Dominio.PlanPago;
import com.example.Puertos.IPlanPagoRepository;
import java.math.BigDecimal;
import java.util.List;

public class PlanPagoService {
    private final IPlanPagoRepository repository;

    public PlanPagoService(IPlanPagoRepository repository) {
        this.repository = repository;
    }

    public String crearPlanPago(int ofertaAcademicaId, String nombre, String tipo,
                               BigDecimal montoMatricula, BigDecimal montoCuota, int cantidadCuotas) {
        try {
            PlanPago plan = new PlanPago(0, ofertaAcademicaId, nombre, tipo, montoMatricula, montoCuota, cantidadCuotas);
            repository.guardar(plan);
            return "<p style='color: #27ae60; font-weight: bold;'>Plan de pago creado: ID=" + plan.getId() + ", Nombre=" + plan.getNombre() + ", Tipo=" + plan.getTipo() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar plan de pago: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarPlanPago(int id, int ofertaAcademicaId, String nombre, String tipo,
                                    BigDecimal montoMatricula, BigDecimal montoCuota, int cantidadCuotas) {
        try {
            PlanPago plan = repository.obtenerPorId(id);
            if (plan == null) {
                return "<p style='color: #e74c3c;'>Plan de pago con ID " + id + " no encontrado.</p>";
            }
            plan.setOfertaAcademicaId(ofertaAcademicaId);
            plan.setNombre(nombre);
            plan.setTipo(tipo);
            plan.setMontoMatricula(montoMatricula);
            plan.setMontoCuota(montoCuota);
            plan.setCantidadCuotas(cantidadCuotas);
            repository.actualizar(plan);
            return "<p style='color: #27ae60; font-weight: bold;'>Plan de pago actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar plan de pago: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarPlanPago(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Plan de pago con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar plan de pago: " + e.getMessage() + "</p>";
        }
    }

    public PlanPago obtenerPlanPago(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<PlanPago> listarPlanesPago() throws Exception {
        return repository.listar();
    }
}
