package com.example.Adaptadores.Salida;

import com.example.Dominio.Pago;
import com.example.Puertos.IPagoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresPagoRepository implements IPagoRepository {

    @Override
    public void guardar(Pago pago) throws Exception {
        String sql = "INSERT INTO pagos (cuota_id, monto_pagado, metodo_pago, transaccion_id, fecha_pago, estado) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pago.getCuotaId());
            ps.setBigDecimal(2, pago.getMontoPagado());
            ps.setString(3, pago.getMetodoPago());
            ps.setString(4, pago.getTransaccionId());
            ps.setTimestamp(5, Timestamp.valueOf(pago.getFechaPago()));
            ps.setString(6, pago.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar pago: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Pago pago) throws Exception {
        String sql = "UPDATE pagos SET cuota_id = ?, monto_pagado = ?, metodo_pago = ?, transaccion_id = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pago.getCuotaId());
            ps.setBigDecimal(2, pago.getMontoPagado());
            ps.setString(3, pago.getMetodoPago());
            ps.setString(4, pago.getTransaccionId());
            ps.setString(5, pago.getEstado());
            ps.setInt(6, pago.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar pago: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM pagos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar pago: " + e.getMessage(), e);
        }
    }

    @Override
    public Pago obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, cuota_id, monto_pagado, metodo_pago, transaccion_id, fecha_pago, estado FROM pagos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pago(rs.getInt("id"), rs.getInt("cuota_id"), rs.getBigDecimal("monto_pagado"),
                            rs.getString("metodo_pago"), rs.getString("transaccion_id"),
                            rs.getTimestamp("fecha_pago").toLocalDateTime(), rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener pago: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Pago> listar() throws Exception {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT id, cuota_id, monto_pagado, metodo_pago, transaccion_id, fecha_pago, estado FROM pagos;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Pago(rs.getInt("id"), rs.getInt("cuota_id"), rs.getBigDecimal("monto_pagado"),
                        rs.getString("metodo_pago"), rs.getString("transaccion_id"),
                        rs.getTimestamp("fecha_pago").toLocalDateTime(), rs.getString("estado")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar pagos: " + e.getMessage(), e);
        }
        return lista;
    }
}
