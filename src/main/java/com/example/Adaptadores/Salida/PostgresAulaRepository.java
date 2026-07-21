package com.example.Adaptadores.Salida;

import com.example.Dominio.Aula;
import com.example.Puertos.IAulaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresAulaRepository implements IAulaRepository {
    @Override
    public void guardar(Aula aula) throws Exception {
        String sql = "INSERT INTO aulas (nombre, codigo, capacidad) VALUES (?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aula.getNombre());
            ps.setString(2, aula.getCodigo());
            ps.setInt(3, aula.getCapacidad());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aula.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar aula: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Aula aula) throws Exception {
        String sql = "UPDATE aulas SET nombre = ?, codigo = ?, capacidad = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aula.getNombre());
            ps.setString(2, aula.getCodigo());
            ps.setInt(3, aula.getCapacidad());
            ps.setInt(4, aula.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar aula: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM aulas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar aula: " + e.getMessage(), e);
        }
    }

    @Override
    public Aula obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, nombre, codigo, capacidad FROM aulas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Aula(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo"), rs.getInt("capacidad"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener aula: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Aula> listar() throws Exception {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, codigo, capacidad FROM aulas;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Aula(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo"), rs.getInt("capacidad")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar aulas: " + e.getMessage(), e);
        }
        return lista;
    }
}
