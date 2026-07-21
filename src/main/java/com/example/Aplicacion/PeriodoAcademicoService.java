package com.example.Aplicacion;

import com.example.Dominio.PeriodoAcademico;
import com.example.Puertos.IPeriodoAcademicoRepository;
import java.time.LocalDate;
import java.util.List;

public class PeriodoAcademicoService {
    private final IPeriodoAcademicoRepository repository;

    public PeriodoAcademicoService(IPeriodoAcademicoRepository repository) {
        this.repository = repository;
    }

    public String crearPeriodo(int ofertaAcademicaId, String nombre, String tipo,
                              LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            PeriodoAcademico p = new PeriodoAcademico();
            p.setOfertaAcademicaId(ofertaAcademicaId);
            p.setNombre(nombre);
            p.setTipo(tipo);
            p.setFechaInicio(fechaInicio);
            p.setFechaFin(fechaFin);
            p.setEstado("inscripcion");
            repository.guardar(p);
            return "<p style='color: #27ae60; font-weight: bold;'>Periodo creado: ID=" + p.getId() + ", Oferta ID=" + p.getOfertaAcademicaId() + ", Nombre=" + p.getNombre() + ", Tipo=" + p.getTipo() + ", Inicio=" + p.getFechaInicio() + ", Fin=" + p.getFechaFin() + ", Estado=" + p.getEstado() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar periodo: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarPeriodo(int id, int ofertaAcademicaId, String nombre, String tipo,
                                   LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        try {
            PeriodoAcademico p = repository.obtenerPorId(id);
            if (p == null) {
                return "<p style='color: #e74c3c;'>Periodo con ID " + id + " no encontrado.</p>";
            }
            p.setOfertaAcademicaId(ofertaAcademicaId);
            p.setNombre(nombre);
            p.setTipo(tipo);
            p.setFechaInicio(fechaInicio);
            p.setFechaFin(fechaFin);
            p.setEstado(estado);
            repository.actualizar(p);
            return "<p style='color: #27ae60; font-weight: bold;'>Periodo '" + p.getNombre() + "' actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar periodo: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarPeriodo(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Periodo con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar periodo: " + e.getMessage() + "</p>";
        }
    }

    public PeriodoAcademico obtenerPeriodo(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<PeriodoAcademico> listarPeriodos() throws Exception {
        return repository.listar();
    }
}
