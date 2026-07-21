package com.example.Adaptadores.Salida;

import com.example.Dominio.Grupo;
import com.example.Puertos.IGrupoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresGrupoRepository implements IGrupoRepository {

    @Override
    public void guardar(Grupo grupo) throws Exception {
        String sql = "INSERT INTO grupos (codigo, materia_id) VALUES (?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, grupo.getCodigo());
            ps.setInt(2, grupo.getMateriaId());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    grupo.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar grupo: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Grupo grupo) throws Exception {
        String sql = "UPDATE grupos SET codigo = ?, materia_id = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grupo.getCodigo());
            ps.setInt(2, grupo.getMateriaId());
            ps.setInt(3, grupo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar grupo: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM grupos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar grupo: " + e.getMessage(), e);
        }
    }

    @Override
    public Grupo obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, codigo, materia_id FROM grupos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Grupo(rs.getInt("id"), rs.getString("codigo"), rs.getInt("materia_id"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener grupo: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Grupo> listar() throws Exception {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, materia_id FROM grupos;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("id"), rs.getString("codigo"), rs.getInt("materia_id")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar grupos: " + e.getMessage(), e);
        }
        return lista;
    }
}
