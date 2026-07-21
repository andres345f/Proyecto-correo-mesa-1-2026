package com.example.Adaptadores.Salida;

import com.example.Dominio.Cuota;
import com.example.Puertos.ICuotaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresCuotaRepository implements ICuotaRepository {

    @Override
    public void guardar(Cuota cuota) throws Exception {
        String sql = "INSERT INTO cuotas (matricula_periodo_id, descripcion, monto, fecha_vencimiento, estado) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cuota.getMatriculaPeriodoId());
            ps.setString(2, cuota.getDescripcion());
            ps.setBigDecimal(3, cuota.getMonto());
            ps.setDate(4, Date.valueOf(cuota.getFechaVencimiento()));
            ps.setString(5, cuota.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cuota.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar cuota: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Cuota cuota) throws Exception {
        String sql = "UPDATE cuotas SET matricula_periodo_id = ?, descripcion = ?, monto = ?, fecha_vencimiento = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cuota.getMatriculaPeriodoId());
            ps.setString(2, cuota.getDescripcion());
            ps.setBigDecimal(3, cuota.getMonto());
            ps.setDate(4, Date.valueOf(cuota.getFechaVencimiento()));
            ps.setString(5, cuota.getEstado());
            ps.setInt(6, cuota.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar cuota: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM cuotas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar cuota: " + e.getMessage(), e);
        }
    }

    @Override
    public Cuota obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, matricula_periodo_id, descripcion, monto, fecha_vencimiento, estado FROM cuotas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cuota(rs.getInt("id"), rs.getInt("matricula_periodo_id"), rs.getString("descripcion"),
                            rs.getBigDecimal("monto"), rs.getDate("fecha_vencimiento").toLocalDate(), rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener cuota: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cuota> listar() throws Exception {
        List<Cuota> lista = new ArrayList<>();
        String sql = "SELECT id, matricula_periodo_id, descripcion, monto, fecha_vencimiento, estado FROM cuotas;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cuota(rs.getInt("id"), rs.getInt("matricula_periodo_id"), rs.getString("descripcion"),
                        rs.getBigDecimal("monto"), rs.getDate("fecha_vencimiento").toLocalDate(), rs.getString("estado")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar cuotas: " + e.getMessage(), e);
        }
        return lista;
    }
}
