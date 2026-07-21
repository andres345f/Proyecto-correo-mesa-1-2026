package com.example.Adaptadores.Salida;

import com.example.Dominio.GrupoPeriodo;
import com.example.Puertos.IGrupoPeriodoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresGrupoPeriodoRepository implements IGrupoPeriodoRepository {

    @Override
    public void guardar(GrupoPeriodo gp) throws Exception {
        String sql = "INSERT INTO grupo_periodo (grupo_id, periodo_academico_id, docente_id, cupo_maximo) VALUES (?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, gp.getGrupoId());
            ps.setInt(2, gp.getPeriodoAcademicoId());
            if (gp.getDocenteId() != null) {
                ps.setInt(3, gp.getDocenteId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, gp.getCupoMaximo());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gp.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar grupo-periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(GrupoPeriodo gp) throws Exception {
        String sql = "UPDATE grupo_periodo SET grupo_id = ?, periodo_academico_id = ?, docente_id = ?, cupo_maximo = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gp.getGrupoId());
            ps.setInt(2, gp.getPeriodoAcademicoId());
            if (gp.getDocenteId() != null) {
                ps.setInt(3, gp.getDocenteId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, gp.getCupoMaximo());
            ps.setInt(5, gp.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar grupo-periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM grupo_periodo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar grupo-periodo: " + e.getMessage(), e);
        }
    }

    @Override
    public GrupoPeriodo obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, grupo_id, periodo_academico_id, docente_id, cupo_maximo FROM grupo_periodo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer docenteId = rs.getObject("docente_id") != null ? rs.getInt("docente_id") : null;
                    return new GrupoPeriodo(rs.getInt("id"), rs.getInt("grupo_id"), rs.getInt("periodo_academico_id"), docenteId, rs.getInt("cupo_maximo"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener grupo-periodo: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<GrupoPeriodo> listar() throws Exception {
        List<GrupoPeriodo> lista = new ArrayList<>();
        String sql = "SELECT id, grupo_id, periodo_academico_id, docente_id, cupo_maximo FROM grupo_periodo;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Integer docenteId = rs.getObject("docente_id") != null ? rs.getInt("docente_id") : null;
                lista.add(new GrupoPeriodo(rs.getInt("id"), rs.getInt("grupo_id"), rs.getInt("periodo_academico_id"), docenteId, rs.getInt("cupo_maximo")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar grupos-periodo: " + e.getMessage(), e);
        }
        return lista;
    }
}
