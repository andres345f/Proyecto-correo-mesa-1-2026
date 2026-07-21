package com.example.Aplicacion;

import com.example.Dominio.Entrega;
import com.example.Puertos.IEntregaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EntregaService {
    private final IEntregaRepository repository;

    public EntregaService(IEntregaRepository repository) {
        this.repository = repository;
    }

    public String crearEntrega(int tareaId, int usuarioId, String rutaArchivo) {
        try {
            Entrega entrega = new Entrega(0, tareaId, usuarioId, rutaArchivo, LocalDateTime.now(), null, null);
            repository.guardar(entrega);
            return "<p style='color: #27ae60; font-weight: bold;'>Entrega registrada: ID=" + entrega.getId() + ", TareaID=" + entrega.getTareaId() + ", UsuarioID=" + entrega.getUsuarioId() + ", Archivo=" + entrega.getRutaArchivo() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al registrar entrega: " + e.getMessage() + "</p>";
        }
    }

    public String calificarEntrega(int id, BigDecimal nota, String retroalimentacion) {
        try {
            Entrega entrega = repository.obtenerPorId(id);
            if (entrega == null) {
                return "<p style='color: #e74c3c;'>Entrega con ID " + id + " no encontrada.</p>";
            }
            entrega.setNota(nota);
            entrega.setRetroalimentacion(retroalimentacion);
            repository.actualizar(entrega);
            return "<p style='color: #27ae60; font-weight: bold;'>Entrega calificada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al calificar entrega: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarEntrega(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Entrega con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar entrega: " + e.getMessage() + "</p>";
        }
    }

    public Entrega obtenerEntrega(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Entrega> listarEntregas() throws Exception {
        return repository.listar();
    }
}
