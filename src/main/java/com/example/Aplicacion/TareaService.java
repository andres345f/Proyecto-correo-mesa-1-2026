package com.example.Aplicacion;

import com.example.Dominio.Tarea;
import com.example.Puertos.ITareaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TareaService {
    private final ITareaRepository repository;

    public TareaService(ITareaRepository repository) {
        this.repository = repository;
    }

    public String crearTarea(int grupoPeriodoId, String titulo, String descripcion,
                            LocalDateTime fechaVencimiento, BigDecimal puntajeMaximo) {
        try {
            Tarea tarea = new Tarea(0, grupoPeriodoId, titulo, descripcion, fechaVencimiento, puntajeMaximo);
            repository.guardar(tarea);
            return "<p style='color: #27ae60; font-weight: bold;'>Tarea creada: ID=" + tarea.getId() + ", Título=" + tarea.getTitulo() + ", GrupoPeriodoID=" + tarea.getGrupoPeriodoId() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar tarea: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarTarea(int id, int grupoPeriodoId, String titulo, String descripcion,
                                 LocalDateTime fechaVencimiento, BigDecimal puntajeMaximo) {
        try {
            Tarea tarea = repository.obtenerPorId(id);
            if (tarea == null) {
                return "<p style='color: #e74c3c;'>Tarea con ID " + id + " no encontrada.</p>";
            }
            tarea.setGrupoPeriodoId(grupoPeriodoId);
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descripcion);
            tarea.setFechaVencimiento(fechaVencimiento);
            tarea.setPuntajeMaximo(puntajeMaximo);
            repository.actualizar(tarea);
            return "<p style='color: #27ae60; font-weight: bold;'>Tarea actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar tarea: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarTarea(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Tarea con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar tarea: " + e.getMessage() + "</p>";
        }
    }

    public Tarea obtenerTarea(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Tarea> listarTareas() throws Exception {
        return repository.listar();
    }
}
