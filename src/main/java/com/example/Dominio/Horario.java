package com.example.Dominio;

import java.time.LocalTime;

public class Horario {
    private int id;
    private int grupoPeriodoId;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int aulaId;

    public Horario() {}

    public Horario(int id, int grupoPeriodoId, String dia, LocalTime horaInicio, LocalTime horaFin, int aulaId) {
        this.id = id;
        this.grupoPeriodoId = grupoPeriodoId;
        setDia(dia);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        this.aulaId = aulaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGrupoPeriodoId() { return grupoPeriodoId; }
    public void setGrupoPeriodoId(int grupoPeriodoId) { this.grupoPeriodoId = grupoPeriodoId; }

    public String getDia() { return dia; }
    public void setDia(String dia) {
        if (dia == null || dia.trim().isEmpty()) {
            throw new IllegalArgumentException("El día es obligatorio.");
        }
        this.dia = dia.trim();
    }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) {
        if (horaInicio == null) {
            throw new IllegalArgumentException("La hora de inicio es obligatoria.");
        }
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) {
        if (horaFin == null) {
            throw new IllegalArgumentException("La hora de fin es obligatoria.");
        }
        if (horaInicio != null && horaFin.isBefore(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
        }
        this.horaFin = horaFin;
    }

    public int getAulaId() { return aulaId; }
    public void setAulaId(int aulaId) { this.aulaId = aulaId; }
}
