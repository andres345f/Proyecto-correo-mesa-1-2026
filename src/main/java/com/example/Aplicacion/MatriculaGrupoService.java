package com.example.Aplicacion;

import com.example.Dominio.MatriculaGrupo;
import com.example.Puertos.IMatriculaGrupoRepository;
import java.math.BigDecimal;
import java.util.List;

public class MatriculaGrupoService {
    private final IMatriculaGrupoRepository repository;

    public MatriculaGrupoService(IMatriculaGrupoRepository repository) {
        this.repository = repository;
    }

    public String inscribirAlumno(int matriculaPeriodoId, int grupoPeriodoId) {
        try {
            MatriculaGrupo mg = new MatriculaGrupo(0, matriculaPeriodoId, grupoPeriodoId, null, "inscrito");
            repository.guardar(mg);
            return "<p style='color: #27ae60; font-weight: bold;'>Alumno inscrito: ID=" + mg.getId() + ", MatPeriodoID=" + mg.getMatriculaPeriodoId() + ", GrupoPeriodoID=" + mg.getGrupoPeriodoId() + ", Estado=" + mg.getEstado() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al inscribir alumno: " + e.getMessage() + "</p>";
        }
    }

    public String calificarAlumno(int id, BigDecimal notaFinal) {
        try {
            MatriculaGrupo mg = repository.obtenerPorId(id);
            if (mg == null) {
                return "<p style='color: #e74c3c;'>Inscripción con ID " + id + " no encontrada.</p>";
            }
            mg.setNotaFinal(notaFinal);
            if (notaFinal != null && notaFinal.compareTo(new BigDecimal("10")) >= 0) {
                mg.setEstado("aprobado");
            } else if (notaFinal != null) {
                mg.setEstado("reprobado");
            }
            repository.actualizar(mg);
            return "<p style='color: #27ae60; font-weight: bold;'>Nota registrada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al calificar: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarInscripcion(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Inscripción con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar inscripción: " + e.getMessage() + "</p>";
        }
    }

    public MatriculaGrupo obtenerMatriculaGrupo(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<MatriculaGrupo> listarMatriculasGrupo() throws Exception {
        return repository.listar();
    }
}
