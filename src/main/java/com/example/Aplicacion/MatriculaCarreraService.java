package com.example.Aplicacion;

import com.example.Dominio.MatriculaCarrera;
import com.example.Puertos.IMatriculaCarreraRepository;
import java.time.LocalDateTime;
import java.util.List;

public class MatriculaCarreraService {
    private final IMatriculaCarreraRepository repository;

    public MatriculaCarreraService(IMatriculaCarreraRepository repository) {
        this.repository = repository;
    }

    public String crearMatriculaCarrera(int usuarioId, int ofertaAcademicaId) {
        try {
            MatriculaCarrera mc = new MatriculaCarrera(0, usuarioId, ofertaAcademicaId, LocalDateTime.now(), "activo");
            repository.guardar(mc);
            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de carrera creada: ID=" + mc.getId() + ", UsuarioID=" + mc.getUsuarioId() + ", OfertaID=" + mc.getOfertaAcademicaId() + ", Estado=" + mc.getEstado() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar matrícula de carrera: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarMatriculaCarrera(int id, int usuarioId, int ofertaAcademicaId, String estado) {
        try {
            MatriculaCarrera mc = repository.obtenerPorId(id);
            if (mc == null) {
                return "<p style='color: #e74c3c;'>Matrícula de carrera con ID " + id + " no encontrada.</p>";
            }
            mc.setUsuarioId(usuarioId);
            mc.setOfertaAcademicaId(ofertaAcademicaId);
            mc.setEstado(estado);
            repository.actualizar(mc);
            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de carrera actualizada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar matrícula de carrera: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarMatriculaCarrera(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de carrera con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar matrícula de carrera: " + e.getMessage() + "</p>";
        }
    }

    public MatriculaCarrera obtenerMatriculaCarrera(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<MatriculaCarrera> listarMatriculasCarrera() throws Exception {
        return repository.listar();
    }
}
