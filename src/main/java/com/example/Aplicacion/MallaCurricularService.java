package com.example.Aplicacion;

import com.example.Dominio.MallaCurricular;
import com.example.Puertos.IMallaCurricularRepository;
import java.util.List;

public class MallaCurricularService {
    private final IMallaCurricularRepository repository;

    public MallaCurricularService(IMallaCurricularRepository repository) {
        this.repository = repository;
    }

    public String crearMalla(int ofertaAcademicaId, int materiaId, int semestreOrden) {
        try {
            MallaCurricular malla = new MallaCurricular(0, ofertaAcademicaId, materiaId, semestreOrden);
            repository.guardar(malla);
            return "<p style='color: #27ae60; font-weight: bold;'>Malla curricular creada: ID=" + malla.getId() + ", Oferta ID=" + malla.getOfertaAcademicaId() + ", Materia ID=" + malla.getMateriaId() + ", Semestre=" + malla.getSemestreOrden() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar malla curricular: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarMalla(int id, int ofertaAcademicaId, int materiaId, int semestreOrden) {
        try {
            MallaCurricular malla = repository.obtenerPorId(id);
            if (malla == null) {
                return "<p style='color: #e74c3c;'>Malla curricular con ID " + id + " no encontrada.</p>";
            }
            malla.setOfertaAcademicaId(ofertaAcademicaId);
            malla.setMateriaId(materiaId);
            malla.setSemestreOrden(semestreOrden);
            repository.actualizar(malla);
            return "<p style='color: #27ae60; font-weight: bold;'>Malla curricular actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar malla curricular: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarMalla(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Malla curricular con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar malla curricular: " + e.getMessage() + "</p>";
        }
    }

    public MallaCurricular obtenerMalla(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<MallaCurricular> listarMallas() throws Exception {
        return repository.listar();
    }
}
