package com.example.Aplicacion;

import com.example.Dominio.Usuario;
import com.example.Puertos.IUsuarioRepository;
import java.util.List;

public class UsuarioService {
    private final IUsuarioRepository repository;

    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public String crearUsuario(String name, String email, String password) {
        try {
            Usuario usuario = new Usuario(0, name, email, password);
            repository.guardar(usuario);
            return "<p style='color: #27ae60; font-weight: bold;'>Usuario creado: ID=" + usuario.getId() + ", Nombre=" + usuario.getName() + ", Email=" + usuario.getEmail() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar usuario: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarUsuario(int id, String name, String email, String password) {
        try {
            Usuario usuario = repository.obtenerPorId(id);
            if (usuario == null) {
                return "<p style='color: #e74c3c;'>Usuario con ID " + id + " no encontrado.</p>";
            }
            usuario.setName(name);
            usuario.setEmail(email);
            usuario.setPassword(password);
            repository.actualizar(usuario);
            return "<p style='color: #27ae60; font-weight: bold;'>Usuario '" + usuario.getName() + "' actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar usuario: " + e.getMessage() + "</p>";
        }
    }

    public String crearEstudiante(String name, String email, String password, String codigoEstudiante) {
        try {
            Usuario usuario = new Usuario(0, name, email, password);
            usuario.setEstudiante(true);
            usuario.setCodigoEstudiante(codigoEstudiante);
            repository.guardar(usuario);
            return "<p style='color: #27ae60; font-weight: bold;'>Estudiante creado: ID=" + usuario.getId() + ", Nombre=" + usuario.getName() + ", Email=" + usuario.getEmail() + ", Código=" + usuario.getCodigoEstudiante() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar estudiante: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarUsuario(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Usuario con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar usuario: " + e.getMessage() + "</p>";
        }
    }

    public Usuario obtenerUsuario(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Usuario> listarUsuarios() throws Exception {
        return repository.listar();
    }
}
