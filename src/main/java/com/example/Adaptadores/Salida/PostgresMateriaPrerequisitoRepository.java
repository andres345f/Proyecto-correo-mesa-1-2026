package com.example.Adaptadores.Salida;

import com.example.Dominio.MateriaPrerequisito;
import com.example.Puertos.IMateriaPrerequisitoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresMateriaPrerequisitoRepository implements IMateriaPrerequisitoRepository {

    @Override
    public void guardar(MateriaPrerequisito mp) throws Exception {
        String sql = "INSERT INTO materia_prerequisito (malla_curricular_id, prerequisito_malla_id) VALUES (?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mp.getMallaCurricularId());
            ps.setInt(2, mp.getPrerequisitoMallaId());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mp.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar prerrequisito: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(MateriaPrerequisito mp) throws Exception {
        String sql = "UPDATE materia_prerequisito SET malla_curricular_id = ?, prerequisito_malla_id = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mp.getMallaCurricularId());
            ps.setInt(2, mp.getPrerequisitoMallaId());
            ps.setInt(3, mp.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar prerrequisito: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM materia_prerequisito WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar prerrequisito: " + e.getMessage(), e);
        }
    }

    @Override
    public MateriaPrerequisito obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, malla_curricular_id, prerequisito_malla_id FROM materia_prerequisito WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MateriaPrerequisito(rs.getInt("id"), rs.getInt("malla_curricular_id"), rs.getInt("prerequisito_malla_id"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener prerrequisito: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<MateriaPrerequisito> listar() throws Exception {
        List<MateriaPrerequisito> lista = new ArrayList<>();
        String sql = "SELECT id, malla_curricular_id, prerequisito_malla_id FROM materia_prerequisito;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new MateriaPrerequisito(rs.getInt("id"), rs.getInt("malla_curricular_id"), rs.getInt("prerequisito_malla_id")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar prerrequisitos: " + e.getMessage(), e);
        }
        return lista;
    }
}
