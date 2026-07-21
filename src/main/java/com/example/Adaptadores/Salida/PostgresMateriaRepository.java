package com.example.Adaptadores.Salida;

import com.example.Dominio.Materia;
import com.example.Puertos.IMateriaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresMateriaRepository implements IMateriaRepository {

    @Override
    public void guardar(Materia materia) throws Exception {
        String sql = "INSERT INTO materias (codigo, nombre, descripcion) VALUES (?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, materia.getCodigo());
            ps.setString(2, materia.getNombre());
            ps.setString(3, materia.getDescripcion());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    materia.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar materia: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Materia materia) throws Exception {
        String sql = "UPDATE materias SET codigo = ?, nombre = ?, descripcion = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, materia.getCodigo());
            ps.setString(2, materia.getNombre());
            ps.setString(3, materia.getDescripcion());
            ps.setInt(4, materia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar materia: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM materias WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar materia: " + e.getMessage(), e);
        }
    }

    @Override
    public Materia obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, codigo, nombre, descripcion FROM materias WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Materia(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre"), rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener materia: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Materia> listar() throws Exception {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, descripcion FROM materias;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Materia(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar materias: " + e.getMessage(), e);
        }
        return lista;
    }
}
