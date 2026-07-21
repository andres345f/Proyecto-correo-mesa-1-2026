package com.example.Aplicacion;

import com.example.Dominio.OfertaAcademica;
import com.example.Puertos.IOfertaAcademicaRepository;
import java.util.List;

public class OfertaAcademicaService {
    private final IOfertaAcademicaRepository repository;

    public OfertaAcademicaService(IOfertaAcademicaRepository repository) {
        this.repository = repository;
    }

    public String crearOferta(String nombre, String codigo, String descripcion) {
        try {
            OfertaAcademica oferta = new OfertaAcademica(0, nombre, codigo, descripcion);
            oferta.setNombre(nombre);
            oferta.setCodigo(codigo);
            repository.guardar(oferta);
            return "<p style='color: #27ae60; font-weight: bold;'>Oferta Académica creada: ID=" + oferta.getId() + ", Nombre=" + oferta.getNombre() + ", Código=" + oferta.getCodigo() + ", Descripción=" + oferta.getDescripcion() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar oferta académica: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarOferta(int id, String nombre, String codigo, String descripcion) {
        try {
            OfertaAcademica oferta = repository.obtenerPorId(id);
            if (oferta == null) {
                return "<p style='color: #e74c3c;'>Oferta Académica con ID " + id + " no encontrada.</p>";
            }
            oferta.setNombre(nombre);
            oferta.setCodigo(codigo);
            oferta.setDescripcion(descripcion);
            repository.actualizar(oferta);
            return "<p style='color: #27ae60; font-weight: bold;'>Oferta Académica '" + oferta.getNombre() + "' actualizada exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar oferta académica: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarOferta(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Oferta Académica con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar oferta académica: " + e.getMessage() + "</p>";
        }
    }

    public OfertaAcademica obtenerOferta(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<OfertaAcademica> listarOfertas() throws Exception {
        return repository.listar();
    }
}
