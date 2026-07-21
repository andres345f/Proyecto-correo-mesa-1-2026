package com.example.Adaptadores.Salida;

import com.example.Dominio.OfertaAcademica;
import com.example.Puertos.IOfertaAcademicaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresOfertaAcademicaRepository implements IOfertaAcademicaRepository {

    @Override
    public void guardar(OfertaAcademica oferta) throws Exception {
        String sql = "INSERT INTO ofertas_academicas (nombre, codigo, descripcion) VALUES (?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, oferta.getNombre());
            ps.setString(2, oferta.getCodigo());
            ps.setString(3, oferta.getDescripcion());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    oferta.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar oferta académica: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(OfertaAcademica oferta) throws Exception {
        String sql = "UPDATE ofertas_academicas SET nombre = ?, codigo = ?, descripcion = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, oferta.getNombre());
            ps.setString(2, oferta.getCodigo());
            ps.setString(3, oferta.getDescripcion());
            ps.setInt(4, oferta.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar oferta académica: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM ofertas_academicas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar oferta académica: " + e.getMessage(), e);
        }
    }

    @Override
    public OfertaAcademica obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, nombre, codigo, descripcion FROM ofertas_academicas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OfertaAcademica(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo"), rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener oferta académica: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<OfertaAcademica> listar() throws Exception {
        List<OfertaAcademica> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, codigo, descripcion FROM ofertas_academicas;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new OfertaAcademica(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar ofertas académicas: " + e.getMessage(), e);
        }
        return lista;
    }
}
