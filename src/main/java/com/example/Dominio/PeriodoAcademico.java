package com.example.Dominio;

import java.time.LocalDate;

public class PeriodoAcademico {
    private int id;
    private int ofertaAcademicaId;
    private String nombre;
    private String tipo;
    private LocalDate fechaInicioInscripcion;
    private LocalDate fechaFinInscripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaInicioCierre;
    private LocalDate fechaFinCierre;
    private LocalDate fechaInicioRetiro;
    private LocalDate fechaFinRetiro;
    private Integer numeroMaximoMaterias;
    private String estado;

    public PeriodoAcademico() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOfertaAcademicaId() { return ofertaAcademicaId; }
    public void setOfertaAcademicaId(int ofertaAcademicaId) { this.ofertaAcademicaId = ofertaAcademicaId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del periodo es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de periodo es obligatorio.");
        }
        this.tipo = tipo.trim();
    }

    public LocalDate getFechaInicioInscripcion() { return fechaInicioInscripcion; }
    public void setFechaInicioInscripcion(LocalDate fechaInicioInscripcion) { this.fechaInicioInscripcion = fechaInicioInscripcion; }

    public LocalDate getFechaFinInscripcion() { return fechaFinInscripcion; }
    public void setFechaFinInscripcion(LocalDate fechaFinInscripcion) { this.fechaFinInscripcion = fechaFinInscripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public LocalDate getFechaInicioCierre() { return fechaInicioCierre; }
    public void setFechaInicioCierre(LocalDate fechaInicioCierre) { this.fechaInicioCierre = fechaInicioCierre; }

    public LocalDate getFechaFinCierre() { return fechaFinCierre; }
    public void setFechaFinCierre(LocalDate fechaFinCierre) { this.fechaFinCierre = fechaFinCierre; }

    public LocalDate getFechaInicioRetiro() { return fechaInicioRetiro; }
    public void setFechaInicioRetiro(LocalDate fechaInicioRetiro) { this.fechaInicioRetiro = fechaInicioRetiro; }

    public LocalDate getFechaFinRetiro() { return fechaFinRetiro; }
    public void setFechaFinRetiro(LocalDate fechaFinRetiro) { this.fechaFinRetiro = fechaFinRetiro; }

    public Integer getNumeroMaximoMaterias() { return numeroMaximoMaterias; }
    public void setNumeroMaximoMaterias(Integer numeroMaximoMaterias) { this.numeroMaximoMaterias = numeroMaximoMaterias; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
