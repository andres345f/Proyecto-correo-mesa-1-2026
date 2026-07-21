package com.example.Adaptadores.Salida;

import com.example.Dominio.MatriculaCarrera;
import com.example.Puertos.IMatriculaCarreraRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresMatriculaCarreraRepository implements IMatriculaCarreraRepository {

    @Override
    public void guardar(MatriculaCarrera mc) throws Exception {
        String sql = "INSERT INTO matriculas_carrera (usuario_id, oferta_academica_id, fecha_matricula, estado) VALUES (?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mc.getUsuarioId());
            ps.setInt(2, mc.getOfertaAcademicaId());
            ps.setTimestamp(3, Timestamp.valueOf(mc.getFechaMatricula()));
            ps.setString(4, mc.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mc.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar matrícula de carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(MatriculaCarrera mc) throws Exception {
        String sql = "UPDATE matriculas_carrera SET usuario_id = ?, oferta_academica_id = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mc.getUsuarioId());
            ps.setInt(2, mc.getOfertaAcademicaId());
            ps.setString(3, mc.getEstado());
            ps.setInt(4, mc.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar matrícula de carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM matriculas_carrera WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar matrícula de carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public MatriculaCarrera obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, usuario_id, oferta_academica_id, fecha_matricula, estado FROM matriculas_carrera WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MatriculaCarrera(rs.getInt("id"), rs.getInt("usuario_id"), rs.getInt("oferta_academica_id"),
                            rs.getTimestamp("fecha_matricula").toLocalDateTime(), rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener matrícula de carrera: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<MatriculaCarrera> listar() throws Exception {
        List<MatriculaCarrera> lista = new ArrayList<>();
        String sql = "SELECT id, usuario_id, oferta_academica_id, fecha_matricula, estado FROM matriculas_carrera;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MatriculaCarrera(rs.getInt("id"), rs.getInt("usuario_id"), rs.getInt("oferta_academica_id"),
                        rs.getTimestamp("fecha_matricula").toLocalDateTime(), rs.getString("estado")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar matrículas de carrera: " + e.getMessage(), e);
        }
        return lista;
    }
}
