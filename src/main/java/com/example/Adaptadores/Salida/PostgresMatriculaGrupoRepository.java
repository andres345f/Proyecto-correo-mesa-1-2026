package com.example.Adaptadores.Salida;

import com.example.Dominio.MatriculaGrupo;
import com.example.Puertos.IMatriculaGrupoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresMatriculaGrupoRepository implements IMatriculaGrupoRepository {

    @Override
    public void guardar(MatriculaGrupo mg) throws Exception {
        String sql = "INSERT INTO matriculas_grupo (matricula_periodo_id, grupo_periodo_id, estado) VALUES (?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mg.getMatriculaPeriodoId());
            ps.setInt(2, mg.getGrupoPeriodoId());
            ps.setString(3, mg.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mg.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al inscribir alumno en grupo: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(MatriculaGrupo mg) throws Exception {
        String sql = "UPDATE matriculas_grupo SET nota_final = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (mg.getNotaFinal() != null) {
                ps.setBigDecimal(1, mg.getNotaFinal());
            } else {
                ps.setNull(1, Types.NUMERIC);
            }
            ps.setString(2, mg.getEstado());
            ps.setInt(3, mg.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar inscripción: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM matriculas_grupo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar inscripción: " + e.getMessage(), e);
        }
    }

    @Override
    public MatriculaGrupo obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, matricula_periodo_id, grupo_periodo_id, nota_final, estado FROM matriculas_grupo WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MatriculaGrupo(rs.getInt("id"), rs.getInt("matricula_periodo_id"), rs.getInt("grupo_periodo_id"),
                            rs.getBigDecimal("nota_final"), rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener inscripción: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<MatriculaGrupo> listar() throws Exception {
        List<MatriculaGrupo> lista = new ArrayList<>();
        String sql = "SELECT id, matricula_periodo_id, grupo_periodo_id, nota_final, estado FROM matriculas_grupo;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MatriculaGrupo(rs.getInt("id"), rs.getInt("matricula_periodo_id"), rs.getInt("grupo_periodo_id"),
                        rs.getBigDecimal("nota_final"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar inscripciones: " + e.getMessage(), e);
        }
        return lista;
    }
}
