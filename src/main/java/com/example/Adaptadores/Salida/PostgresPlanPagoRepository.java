package com.example.Adaptadores.Salida;

import com.example.Dominio.PlanPago;
import com.example.Puertos.IPlanPagoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresPlanPagoRepository implements IPlanPagoRepository {

    @Override
    public void guardar(PlanPago plan) throws Exception {
        String sql = "INSERT INTO planes_pago (oferta_academica_id, nombre, tipo, monto_matricula, monto_cuota, cantidad_cuotas) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, plan.getOfertaAcademicaId());
            ps.setString(2, plan.getNombre());
            ps.setString(3, plan.getTipo());
            ps.setBigDecimal(4, plan.getMontoMatricula());
            ps.setBigDecimal(5, plan.getMontoCuota());
            ps.setInt(6, plan.getCantidadCuotas());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    plan.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar plan de pago: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(PlanPago plan) throws Exception {
        String sql = "UPDATE planes_pago SET oferta_academica_id = ?, nombre = ?, tipo = ?, monto_matricula = ?, monto_cuota = ?, cantidad_cuotas = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, plan.getOfertaAcademicaId());
            ps.setString(2, plan.getNombre());
            ps.setString(3, plan.getTipo());
            ps.setBigDecimal(4, plan.getMontoMatricula());
            ps.setBigDecimal(5, plan.getMontoCuota());
            ps.setInt(6, plan.getCantidadCuotas());
            ps.setInt(7, plan.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar plan de pago: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM planes_pago WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar plan de pago: " + e.getMessage(), e);
        }
    }

    @Override
    public PlanPago obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, oferta_academica_id, nombre, tipo, monto_matricula, monto_cuota, cantidad_cuotas FROM planes_pago WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PlanPago(rs.getInt("id"), rs.getInt("oferta_academica_id"), rs.getString("nombre"),
                            rs.getString("tipo"), rs.getBigDecimal("monto_matricula"), rs.getBigDecimal("monto_cuota"), rs.getInt("cantidad_cuotas"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener plan de pago: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<PlanPago> listar() throws Exception {
        List<PlanPago> lista = new ArrayList<>();
        String sql = "SELECT id, oferta_academica_id, nombre, tipo, monto_matricula, monto_cuota, cantidad_cuotas FROM planes_pago;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new PlanPago(rs.getInt("id"), rs.getInt("oferta_academica_id"), rs.getString("nombre"),
                        rs.getString("tipo"), rs.getBigDecimal("monto_matricula"), rs.getBigDecimal("monto_cuota"), rs.getInt("cantidad_cuotas")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar planes de pago: " + e.getMessage(), e);
        }
        return lista;
    }
}
