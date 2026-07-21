package com.example.Adaptadores.Salida;

import com.example.Dominio.Horario;
import com.example.Puertos.IHorarioRepository;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresHorarioRepository implements IHorarioRepository {

    @Override
    public void guardar(Horario horario) throws Exception {
        String sql = "INSERT INTO horarios (grupo_periodo_id, dia, hora_inicio, hora_fin, aula_id) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, horario.getGrupoPeriodoId());
            ps.setString(2, horario.getDia());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraFin()));
            ps.setInt(5, horario.getAulaId());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    horario.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar horario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Horario horario) throws Exception {
        String sql = "UPDATE horarios SET grupo_periodo_id = ?, dia = ?, hora_inicio = ?, hora_fin = ?, aula_id = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, horario.getGrupoPeriodoId());
            ps.setString(2, horario.getDia());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraFin()));
            ps.setInt(5, horario.getAulaId());
            ps.setInt(6, horario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar horario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM horarios WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar horario: " + e.getMessage(), e);
        }
    }

    @Override
    public Horario obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, grupo_periodo_id, dia, hora_inicio, hora_fin, aula_id FROM horarios WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Horario(rs.getInt("id"), rs.getInt("grupo_periodo_id"), rs.getString("dia"),
                            rs.getTime("hora_inicio").toLocalTime(), rs.getTime("hora_fin").toLocalTime(), rs.getInt("aula_id"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener horario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Horario> listar() throws Exception {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT id, grupo_periodo_id, dia, hora_inicio, hora_fin, aula_id FROM horarios;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Horario(rs.getInt("id"), rs.getInt("grupo_periodo_id"), rs.getString("dia"),
                        rs.getTime("hora_inicio").toLocalTime(), rs.getTime("hora_fin").toLocalTime(), rs.getInt("aula_id")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar horarios: " + e.getMessage(), e);
        }
        return lista;
    }
}
