package com.example.Adaptadores.Salida;

import com.example.Dominio.PeriodoAcademico;
import com.example.Puertos.IPeriodoAcademicoRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostgresPeriodoAcademicoRepository implements IPeriodoAcademicoRepository {

    @Override
    public void guardar(PeriodoAcademico p) throws Exception {
        String sql = "INSERT INTO periodos_academicos (oferta_academica_id, nombre, tipo, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getOfertaAcademicaId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTipo());
            ps.setDate(4, p.getFechaInicio() != null ? Date.valueOf(p.getFechaInicio()) : null);
            ps.setDate(5, p.getFechaFin() != null ? Date.valueOf(p.getFechaFin()) : null);
            ps.setString(6, p.getEstado());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar periodo académico: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(PeriodoAcademico p) throws Exception {
        String sql = "UPDATE periodos_academicos SET oferta_academica_id = ?, nombre = ?, tipo = ?, " +
                "fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getOfertaAcademicaId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTipo());
            ps.setDate(4, p.getFechaInicio() != null ? Date.valueOf(p.getFechaInicio()) : null);
            ps.setDate(5, p.getFechaFin() != null ? Date.valueOf(p.getFechaFin()) : null);
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar periodo académico: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM periodos_academicos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar periodo académico: " + e.getMessage(), e);
        }
    }

    @Override
    public PeriodoAcademico obtenerPorId(int id) throws Exception {
        String sql = "SELECT id, oferta_academica_id, nombre, tipo, fecha_inicio, fecha_fin, " +
                "fecha_inicio_inscripcion, fecha_fin_inscripcion, fecha_inicio_cierre, fecha_fin_cierre, " +
                "fecha_inicio_retiro, fecha_fin_retiro, numero_maximo_materias, estado " +
                "FROM periodos_academicos WHERE id = ?;";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PeriodoAcademico p = new PeriodoAcademico();
                    p.setId(rs.getInt("id"));
                    p.setOfertaAcademicaId(rs.getInt("oferta_academica_id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setTipo(rs.getString("tipo"));
                    p.setFechaInicio(
                            rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    p.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);
                    p.setFechaInicioInscripcion(rs.getDate("fecha_inicio_inscripcion") != null
                            ? rs.getDate("fecha_inicio_inscripcion").toLocalDate()
                            : null);
                    p.setFechaFinInscripcion(rs.getDate("fecha_fin_inscripcion") != null
                            ? rs.getDate("fecha_fin_inscripcion").toLocalDate()
                            : null);
                    p.setFechaInicioCierre(
                            rs.getDate("fecha_inicio_cierre") != null ? rs.getDate("fecha_inicio_cierre").toLocalDate()
                                    : null);
                    p.setFechaFinCierre(
                            rs.getDate("fecha_fin_cierre") != null ? rs.getDate("fecha_fin_cierre").toLocalDate()
                                    : null);
                    p.setFechaInicioRetiro(
                            rs.getDate("fecha_inicio_retiro") != null ? rs.getDate("fecha_inicio_retiro").toLocalDate()
                                    : null);
                    p.setFechaFinRetiro(
                            rs.getDate("fecha_fin_retiro") != null ? rs.getDate("fecha_fin_retiro").toLocalDate()
                                    : null);
                    p.setNumeroMaximoMaterias(
                            rs.getObject("numero_maximo_materias") != null ? rs.getInt("numero_maximo_materias")
                                    : null);
                    p.setEstado(rs.getString("estado"));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener periodo académico: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<PeriodoAcademico> listar() throws Exception {
        List<PeriodoAcademico> lista = new ArrayList<>();
        String sql = "SELECT id, oferta_academica_id, nombre, tipo, fecha_inicio, fecha_fin, " +
                "fecha_inicio_inscripcion, fecha_fin_inscripcion, fecha_inicio_cierre, fecha_fin_cierre, " +
                "fecha_inicio_retiro, fecha_fin_retiro, numero_maximo_materias, estado FROM periodos_academicos;";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PeriodoAcademico p = new PeriodoAcademico();
                p.setId(rs.getInt("id"));
                p.setOfertaAcademicaId(rs.getInt("oferta_academica_id"));
                p.setNombre(rs.getString("nombre"));
                p.setTipo(rs.getString("tipo"));
                p.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                p.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);
                p.setEstado(rs.getString("estado"));
                p.setFechaInicioInscripcion(rs.getDate("fecha_inicio_inscripcion") != null
                        ? rs.getDate("fecha_inicio_inscripcion").toLocalDate()
                        : null);
                p.setFechaFinInscripcion(
                        rs.getDate("fecha_fin_inscripcion") != null ? rs.getDate("fecha_fin_inscripcion").toLocalDate()
                                : null);
                p.setFechaInicioCierre(
                        rs.getDate("fecha_inicio_cierre") != null ? rs.getDate("fecha_inicio_cierre").toLocalDate()
                                : null);
                p.setFechaFinCierre(
                        rs.getDate("fecha_fin_cierre") != null ? rs.getDate("fecha_fin_cierre").toLocalDate() : null);
                p.setFechaInicioRetiro(
                        rs.getDate("fecha_inicio_retiro") != null ? rs.getDate("fecha_inicio_retiro").toLocalDate()
                                : null);
                p.setFechaFinRetiro(
                        rs.getDate("fecha_fin_retiro") != null ? rs.getDate("fecha_fin_retiro").toLocalDate() : null);
                p.setNumeroMaximoMaterias(
                        rs.getObject("numero_maximo_materias") != null ? rs.getInt("numero_maximo_materias") : null);
                System.out.println("Periodo académico encontrado: " + p.getId() + " " + p.getNombre() + " "
                        + p.getTipo() + " " + p.getFechaInicio() + " " + p.getFechaFin() + " " + p.getEstado() + " "
                        + p.getFechaInicioInscripcion() + " " + p.getFechaFinInscripcion() + " "
                        + p.getFechaInicioCierre() + " " + p.getFechaFinCierre() + " " + p.getFechaInicioRetiro() + " "
                        + p.getFechaFinRetiro() + " " + p.getNumeroMaximoMaterias());
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar periodos académicos: " + e.getMessage(), e);
        }
        return lista;
    }
}
