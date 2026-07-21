package com.example.Aplicacion;

import com.example.Dominio.Aula;
import com.example.Puertos.IAulaRepository;
import java.util.List;

public class AulaService {
    private final IAulaRepository repository;

    public AulaService(IAulaRepository repository) {
        this.repository = repository;
    }

    public String crearAula(String nombre, String codigo, int capacidad) {
        try {
            Aula aula = new Aula(nombre, codigo, capacidad);
            repository.guardar(aula);
            return "<p style='color: #27ae60; font-weight: bold;'>Aula creada: ID=" + aula.getId() + ", Nombre=" + aula.getNombre() + ", Código=" + aula.getCodigo() + ", Capacidad=" + aula.getCapacidad() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar aula: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarAula(int id, String nombre, String codigo, int capacidad) {
        try {
            Aula aula = repository.obtenerPorId(id);
            if (aula == null) {
                return "<p style='color: #e74c3c;'>Aula con ID " + id + " no encontrada.</p>";
            }
            aula.setNombre(nombre);
            aula.setCodigo(codigo);
            aula.setCapacidad(capacidad);
            repository.actualizar(aula);
            return "<p style='color: #27ae60; font-weight: bold;'>Aula '" + aula.getNombre() + "' actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar aula: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarAula(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Aula con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar aula: " + e.getMessage() + "</p>";
        }
    }

    public Aula obtenerAula(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Aula> listarAulas() throws Exception {
        return repository.listar();
    }
}
