package com.example.Adaptadores.Salida;

import com.example.Dominio.MallaCurricular;
import com.example.Puertos.IMallaCurricularRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresMallaCurricularRepository implements IMallaCurricularRepository {

    @Override
    public void guardar(MallaCurricular malla) throws Exception {
        String sql = "INSERT INTO malla_curricular (oferta_academica_id, materia_id, semestre_orden) VALUES (?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, malla.getOfertaAcademicaId());
            ps.setInt(2, malla.getMateriaId());
            ps.setInt(3, malla.getSemestreOrden());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    malla.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar malla curricular: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(MallaCurricular malla) throws Exception {
        String sql = "UPDATE malla_curricular SET oferta_academica_id = ?, materia_id = ?, semestre_orden = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, malla.getOfertaAcademicaId());
            ps.setInt(2, malla.getMateriaId());
            ps.setInt(3, malla.getSemestreOrden());
            ps.setInt(4, malla.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar malla curricular: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM malla_curricular WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar malla curricular: " + e.getMessage(), e);
        }
    }

    @Override
    public MallaCurricular obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, oferta_academica_id, materia_id, semestre_orden FROM malla_curricular WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MallaCurricular(rs.getInt("id"), rs.getInt("oferta_academica_id"), rs.getInt("materia_id"), rs.getInt("semestre_orden"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener malla curricular: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<MallaCurricular> listar() throws Exception {
        List<MallaCurricular> lista = new ArrayList<>();
        String sql = "SELECT id, oferta_academica_id, materia_id, semestre_orden FROM malla_curricular;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MallaCurricular(rs.getInt("id"), rs.getInt("oferta_academica_id"), rs.getInt("materia_id"), rs.getInt("semestre_orden")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar mallas curriculares: " + e.getMessage(), e);
        }
        return lista;
    }
}
