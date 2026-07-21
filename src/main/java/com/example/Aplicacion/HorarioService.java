package com.example.Aplicacion;

import com.example.Dominio.Horario;
import com.example.Puertos.IHorarioRepository;
import java.time.LocalTime;
import java.util.List;

public class HorarioService {
    private final IHorarioRepository repository;

    public HorarioService(IHorarioRepository repository) {
        this.repository = repository;
    }

    public String crearHorario(int grupoPeriodoId, String dia, LocalTime horaInicio, LocalTime horaFin, int aulaId) {
        try {
            Horario horario = new Horario(0, grupoPeriodoId, dia, horaInicio, horaFin, aulaId);
            horario.setDia(dia);
            horario.setHoraInicio(horaInicio);
            horario.setHoraFin(horaFin);
            repository.guardar(horario);
            return "<p style='color: #27ae60; font-weight: bold;'>Horario creado: ID=" + horario.getId() + ", Grupo Periodo ID=" + horario.getGrupoPeriodoId() + ", Día=" + horario.getDia() + ", Hora Inicio=" + horario.getHoraInicio() + ", Hora Fin=" + horario.getHoraFin() + ", Aula ID=" + horario.getAulaId() + "</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar horario: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarHorario(int id, int grupoPeriodoId, String dia, LocalTime horaInicio, LocalTime horaFin, int aulaId) {
        try {
            Horario horario = repository.obtenerPorId(id);
            if (horario == null) {
                return "<p style='color: #e74c3c;'>Horario con ID " + id + " no encontrado.</p>";
            }
            horario.setGrupoPeriodoId(grupoPeriodoId);
            horario.setDia(dia);
            horario.setHoraInicio(horaInicio);
            horario.setHoraFin(horaFin);
            horario.setAulaId(aulaId);
            repository.actualizar(horario);
            return "<p style='color: #27ae60; font-weight: bold;'>Horario actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar horario: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarHorario(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Horario con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar horario: " + e.getMessage() + "</p>";
        }
    }

    public Horario obtenerHorario(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Horario> listarHorarios() throws Exception {
        return repository.listar();
    }
}
