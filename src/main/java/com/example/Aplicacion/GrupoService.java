package com.example.Aplicacion;

import com.example.Dominio.Grupo;
import com.example.Puertos.IGrupoRepository;
import java.util.List;

public class GrupoService {
    private final IGrupoRepository repository;

    public GrupoService(IGrupoRepository repository) {
        this.repository = repository;
    }

    public String crearGrupo(String codigo, int materiaId) {
        try {
            Grupo grupo = new Grupo(codigo, materiaId);
            repository.guardar(grupo);
            return "<p style='color: #27ae60; font-weight: bold;'>Grupo creado: ID=" + grupo.getId() + ", Código=" + grupo.getCodigo() + ", Materia ID=" + grupo.getMateriaId() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar grupo: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarGrupo(int id, String codigo, int materiaId) {
        try {
            Grupo grupo = repository.obtenerPorId(id);
            if (grupo == null) {
                return "<p style='color: #e74c3c;'>Grupo con ID " + id + " no encontrado.</p>";
            }
            grupo.setCodigo(codigo);
            grupo.setMateriaId(materiaId);
            repository.actualizar(grupo);
            return "<p style='color: #27ae60; font-weight: bold;'>Grupo '" + grupo.getCodigo() + "' actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar grupo: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarGrupo(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Grupo con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar grupo: " + e.getMessage() + "</p>";
        }
    }

    public Grupo obtenerGrupo(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Grupo> listarGrupos() throws Exception {
        return repository.listar();
    }
}
