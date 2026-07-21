package com.example.Adaptadores.Salida;

import com.example.Dominio.MatriculaPeriodo;
import com.example.Puertos.IMatriculaPeriodoRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresMatriculaPeriodoRepository implements IMatriculaPeriodoRepository {

    @Override
    public void guardar(MatriculaPeriodo mp) throws Exception {
        String sql = "INSERT INTO matriculas_periodo (matricula_carrera_id, periodo_academico_id, plan_pago_id, fecha_matricula, estado) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mp.getMatriculaCarreraId());
            ps.setInt(2, mp.getPeriodoAcademicoId());
            ps.setInt(3, mp.getPlanPagoId());
            ps.setTimestamp(4, Timestamp.valueOf(mp.getFechaMatricula()));
            ps.setString(5, mp.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mp.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar matrícula de periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(MatriculaPeriodo mp) throws Exception {
        String sql = "UPDATE matriculas_periodo SET matricula_carrera_id = ?, periodo_academico_id = ?, plan_pago_id = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mp.getMatriculaCarreraId());
            ps.setInt(2, mp.getPeriodoAcademicoId());
            ps.setInt(3, mp.getPlanPagoId());
            ps.setString(4, mp.getEstado());
            ps.setInt(5, mp.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar matrícula de periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM matriculas_periodo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar matrícula de periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public MatriculaPeriodo obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, matricula_carrera_id, periodo_academico_id, plan_pago_id, fecha_matricula, estado FROM matriculas_periodo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MatriculaPeriodo(rs.getInt("id"), rs.getInt("matricula_carrera_id"), rs.getInt("periodo_academico_id"),
                            rs.getInt("plan_pago_id"), rs.getTimestamp("fecha_matricula").toLocalDateTime(), rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener matrícula de periodo: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<MatriculaPeriodo> listar() throws Exception {
        List<MatriculaPeriodo> lista = new ArrayList<>();
        String sql = "SELECT id, matricula_carrera_id, periodo_academico_id, plan_pago_id, fecha_matricula, estado FROM matriculas_periodo;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MatriculaPeriodo(rs.getInt("id"), rs.getInt("matricula_carrera_id"), rs.getInt("periodo_academico_id"),
                        rs.getInt("plan_pago_id"), rs.getTimestamp("fecha_matricula").toLocalDateTime(), rs.getString("estado")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar matrículas de periodo: " + e.getMessage(), e);
        }
        return lista;
    }
}
