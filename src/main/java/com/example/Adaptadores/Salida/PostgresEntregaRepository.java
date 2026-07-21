package com.example.Adaptadores.Salida;

import com.example.Dominio.Entrega;
import com.example.Puertos.IEntregaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresEntregaRepository implements IEntregaRepository {

    @Override
    public void guardar(Entrega entrega) throws Exception {
        String sql = "INSERT INTO entregas (tarea_id, usuario_id, ruta_archivo, fecha_entrega) VALUES (?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entrega.getTareaId());
            ps.setInt(2, entrega.getUsuarioId());
            ps.setString(3, entrega.getRutaArchivo());
            ps.setTimestamp(4, Timestamp.valueOf(entrega.getFechaEntrega()));
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entrega.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar entrega: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Entrega entrega) throws Exception {
        String sql = "UPDATE entregas SET nota = ?, retroalimentacion = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (entrega.getNota() != null) {
                ps.setBigDecimal(1, entrega.getNota());
            } else {
                ps.setNull(1, Types.NUMERIC);
            }
            ps.setString(2, entrega.getRetroalimentacion());
            ps.setInt(3, entrega.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar entrega: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM entregas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar entrega: " + e.getMessage(), e);
        }
    }

    @Override
    public Entrega obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, tarea_id, usuario_id, ruta_archivo, fecha_entrega, nota, retroalimentacion FROM entregas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Entrega(rs.getInt("id"), rs.getInt("tarea_id"), rs.getInt("usuario_id"),
                            rs.getString("ruta_archivo"), rs.getTimestamp("fecha_entrega").toLocalDateTime(),
                            rs.getBigDecimal("nota"), rs.getString("retroalimentacion"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener entrega: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Entrega> listar() throws Exception {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT id, tarea_id, usuario_id, ruta_archivo, fecha_entrega, nota, retroalimentacion FROM entregas;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Entrega(rs.getInt("id"), rs.getInt("tarea_id"), rs.getInt("usuario_id"),
                        rs.getString("ruta_archivo"), rs.getTimestamp("fecha_entrega").toLocalDateTime(),
                        rs.getBigDecimal("nota"), rs.getString("retroalimentacion")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar entregas: " + e.getMessage(), e);
        }
        return lista;
    }
}
