package com.example.Adaptadores.Salida;

import com.example.Dominio.Tarea;
import com.example.Puertos.ITareaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresTareaRepository implements ITareaRepository {

    @Override
    public void guardar(Tarea tarea) throws Exception {
        String sql = "INSERT INTO tareas (grupo_periodo_id, titulo, descripcion, fecha_vencimiento, puntaje_maximo) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, tarea.getGrupoPeriodoId());
            ps.setString(2, tarea.getTitulo());
            ps.setString(3, tarea.getDescripcion());
            ps.setTimestamp(4, Timestamp.valueOf(tarea.getFechaVencimiento()));
            ps.setBigDecimal(5, tarea.getPuntajeMaximo());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tarea.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar tarea: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Tarea tarea) throws Exception {
        String sql = "UPDATE tareas SET grupo_periodo_id = ?, titulo = ?, descripcion = ?, fecha_vencimiento = ?, puntaje_maximo = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tarea.getGrupoPeriodoId());
            ps.setString(2, tarea.getTitulo());
            ps.setString(3, tarea.getDescripcion());
            ps.setTimestamp(4, Timestamp.valueOf(tarea.getFechaVencimiento()));
            ps.setBigDecimal(5, tarea.getPuntajeMaximo());
            ps.setInt(6, tarea.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar tarea: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM tareas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar tarea: " + e.getMessage(), e);
        }
    }

    @Override
    public Tarea obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, grupo_periodo_id, titulo, descripcion, fecha_vencimiento, puntaje_maximo FROM tareas WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Tarea(rs.getInt("id"), rs.getInt("grupo_periodo_id"), rs.getString("titulo"),
                            rs.getString("descripcion"), rs.getTimestamp("fecha_vencimiento").toLocalDateTime(),
                            rs.getBigDecimal("puntaje_maximo"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener tarea: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Tarea> listar() throws Exception {
        List<Tarea> lista = new ArrayList<>();
        String sql = "SELECT id, grupo_periodo_id, titulo, descripcion, fecha_vencimiento, puntaje_maximo FROM tareas;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Tarea(rs.getInt("id"), rs.getInt("grupo_periodo_id"), rs.getString("titulo"),
                        rs.getString("descripcion"), rs.getTimestamp("fecha_vencimiento").toLocalDateTime(),
                        rs.getBigDecimal("puntaje_maximo")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar tareas: " + e.getMessage(), e);
        }
        return lista;
    }
}
