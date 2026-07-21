package com.example.Aplicacion;

import com.example.Dominio.Materia;
import com.example.Puertos.IMateriaRepository;
import java.util.List;

public class MateriaService {
    private final IMateriaRepository repository;

    public MateriaService(IMateriaRepository repository) {
        this.repository = repository;
    }

    public String crearMateria(String codigo, String nombre, String descripcion) {
        try {
            Materia materia = new Materia(0, codigo, nombre, descripcion);
            materia.setCodigo(codigo);
            materia.setNombre(nombre);
            repository.guardar(materia);
            return "<p style='color: #27ae60; font-weight: bold;'>Materia creada: ID=" + materia.getId() + ", Código=" + materia.getCodigo() + ", Nombre=" + materia.getNombre() + ", Descripción=" + materia.getDescripcion() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar materia: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarMateria(int id, String codigo, String nombre, String descripcion) {
        try {
            Materia materia = repository.obtenerPorId(id);
            if (materia == null) {
                return "<p style='color: #e74c3c;'>Materia con ID " + id + " no encontrada.</p>";
            }
            materia.setCodigo(codigo);
            materia.setNombre(nombre);
            materia.setDescripcion(descripcion);
            repository.actualizar(materia);
            return "<p style='color: #27ae60; font-weight: bold;'>Materia '" + materia.getNombre() + "' actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar materia: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarMateria(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Materia con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar materia: " + e.getMessage() + "</p>";
        }
    }

    public Materia obtenerMateria(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Materia> listarMaterias() throws Exception {
        return repository.listar();
    }
}
