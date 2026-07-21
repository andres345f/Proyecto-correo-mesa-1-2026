package com.example.Aplicacion;

import com.example.Dominio.Cuota;
import com.example.Puertos.ICuotaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CuotaService {
    private final ICuotaRepository repository;

    public CuotaService(ICuotaRepository repository) {
        this.repository = repository;
    }

    public String crearCuota(int matriculaPeriodoId, String descripcion, BigDecimal monto, LocalDate fechaVencimiento) {
        try {
            Cuota cuota = new Cuota(0, matriculaPeriodoId, descripcion, monto, fechaVencimiento, "pendiente");
            repository.guardar(cuota);
            return "<p style='color: #27ae60; font-weight: bold;'>Cuota creada: ID=" + cuota.getId() + ", Descripción=" + cuota.getDescripcion() + ", Monto=" + cuota.getMonto() + ", Vencimiento=" + cuota.getFechaVencimiento() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar cuota: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarCuota(int id, int matriculaPeriodoId, String descripcion, BigDecimal monto,
                                 LocalDate fechaVencimiento, String estado) {
        try {
            Cuota cuota = repository.obtenerPorId(id);
            if (cuota == null) {
                return "<p style='color: #e74c3c;'>Cuota con ID " + id + " no encontrada.</p>";
            }
            cuota.setMatriculaPeriodoId(matriculaPeriodoId);
            cuota.setDescripcion(descripcion);
            cuota.setMonto(monto);
            cuota.setFechaVencimiento(fechaVencimiento);
            cuota.setEstado(estado);
            repository.actualizar(cuota);
            return "<p style='color: #27ae60; font-weight: bold;'>Cuota actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar cuota: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarCuota(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Cuota con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar cuota: " + e.getMessage() + "</p>";
        }
    }

    public Cuota obtenerCuota(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Cuota> listarCuotas() throws Exception {
        return repository.listar();
    }
}
