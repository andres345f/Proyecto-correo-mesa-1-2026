package com.example.Aplicacion;

import com.example.Dominio.GrupoPeriodo;
import com.example.Puertos.IGrupoPeriodoRepository;
import java.util.List;

public class GrupoPeriodoService {
    private final IGrupoPeriodoRepository repository;

    public GrupoPeriodoService(IGrupoPeriodoRepository repository) {
        this.repository = repository;
    }

    public String crearGrupoPeriodo(int grupoId, int periodoAcademicoId, Integer docenteId, int cupoMaximo) {
        try {
            GrupoPeriodo gp = new GrupoPeriodo(0, grupoId, periodoAcademicoId, docenteId, cupoMaximo);
            repository.guardar(gp);
            return "<p style='color: #27ae60; font-weight: bold;'>Asignación grupo-periodo creada: ID=" + gp.getId() + ", Grupo ID=" + gp.getGrupoId() + ", Periodo ID=" + gp.getPeriodoAcademicoId() + ", Docente ID=" + gp.getDocenteId() + ", Cupo Máximo=" + gp.getCupoMaximo() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar grupo-periodo: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarGrupoPeriodo(int id, int grupoId, int periodoAcademicoId, Integer docenteId, int cupoMaximo) {
        try {
            GrupoPeriodo gp = repository.obtenerPorId(id);
            if (gp == null) {
                return "<p style='color: #e74c3c;'>Grupo-Periodo con ID " + id + " no encontrado.</p>";
            }
            gp.setGrupoId(grupoId);
            gp.setPeriodoAcademicoId(periodoAcademicoId);
            gp.setDocenteId(docenteId);
            gp.setCupoMaximo(cupoMaximo);
            repository.actualizar(gp);
            return "<p style='color: #27ae60; font-weight: bold;'>Asignación grupo-periodo actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar grupo-periodo: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarGrupoPeriodo(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Asignación grupo-periodo con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar grupo-periodo: " + e.getMessage() + "</p>";
        }
    }

    public GrupoPeriodo obtenerGrupoPeriodo(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<GrupoPeriodo> listarGruposPeriodo() throws Exception {
        return repository.listar();
    }
}
