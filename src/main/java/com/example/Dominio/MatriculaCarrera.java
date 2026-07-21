package com.example.Dominio;

import java.time.LocalDateTime;

public class MatriculaCarrera {
    private int id;
    private int usuarioId;
    private int ofertaAcademicaId;
    private LocalDateTime fechaMatricula;
    private String estado;

    public MatriculaCarrera() {}

    public MatriculaCarrera(int id, int usuarioId, int ofertaAcademicaId, LocalDateTime fechaMatricula, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.ofertaAcademicaId = ofertaAcademicaId;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getOfertaAcademicaId() { return ofertaAcademicaId; }
    public void setOfertaAcademicaId(int ofertaAcademicaId) { this.ofertaAcademicaId = ofertaAcademicaId; }

    public LocalDateTime getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDateTime fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
