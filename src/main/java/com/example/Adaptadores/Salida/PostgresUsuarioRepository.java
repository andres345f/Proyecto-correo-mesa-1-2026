package com.example.Adaptadores.Salida;

import com.example.Dominio.Usuario;
import com.example.Puertos.IUsuarioRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresUsuarioRepository implements IUsuarioRepository {

    @Override
    public void guardar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO users (name, email, password, is_activo) VALUES (?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setBoolean(4, usuario.isActivo());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void guardarEstudiante(Usuario usuario) throws Exception {
        String sql = "INSERT INTO users (name, email, password, is_estudiante, codigo_estudiante, is_activo) VALUES (?, ?, ?, true, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getCodigoEstudiante());
            ps.setBoolean(5, usuario.isActivo());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar estudiante: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws Exception {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, is_propietario = ?, is_director = ?, " +
                     "is_secretaria = ?, is_profesor = ?, is_estudiante = ?, codigo_estudiante = ?, is_activo = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setBoolean(4, usuario.isPropietario());
            ps.setBoolean(5, usuario.isDirector());
            ps.setBoolean(6, usuario.isSecretaria());
            ps.setBoolean(7, usuario.isProfesor());
            ps.setBoolean(8, usuario.isEstudiante());
            ps.setString(9, usuario.getCodigoEstudiante());
            ps.setBoolean(10, usuario.isActivo());
            ps.setInt(11, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, name, email, password, is_propietario, is_director, is_secretaria, is_profesor, " +
                     "is_estudiante, codigo_estudiante, is_activo FROM users WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                    u.setPropietario(rs.getBoolean("is_propietario"));
                    u.setDirector(rs.getBoolean("is_director"));
                    u.setSecretaria(rs.getBoolean("is_secretaria"));
                    u.setProfesor(rs.getBoolean("is_profesor"));
                    u.setEstudiante(rs.getBoolean("is_estudiante"));
                    u.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                    u.setActivo(rs.getBoolean("is_activo"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener usuario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Usuario> listar() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, name, email, password, is_propietario, is_director, is_secretaria, is_profesor, " +
                     "is_estudiante, codigo_estudiante, is_activo FROM users;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                u.setPropietario(rs.getBoolean("is_propietario"));
                u.setDirector(rs.getBoolean("is_director"));
                u.setSecretaria(rs.getBoolean("is_secretaria"));
                u.setProfesor(rs.getBoolean("is_profesor"));
                u.setEstudiante(rs.getBoolean("is_estudiante"));
                u.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                u.setActivo(rs.getBoolean("is_activo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }
}
