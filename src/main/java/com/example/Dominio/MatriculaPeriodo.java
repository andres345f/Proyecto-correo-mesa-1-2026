package com.example.Dominio;

import java.time.LocalDateTime;

public class MatriculaPeriodo {
    private int id;
    private int matriculaCarreraId;
    private int periodoAcademicoId;
    private int planPagoId;
    private LocalDateTime fechaMatricula;
    private String estado;

    public MatriculaPeriodo() {}

    public MatriculaPeriodo(int id, int matriculaCarreraId, int periodoAcademicoId, int planPagoId,
                           LocalDateTime fechaMatricula, String estado) {
        this.id = id;
        this.matriculaCarreraId = matriculaCarreraId;
        this.periodoAcademicoId = periodoAcademicoId;
        this.planPagoId = planPagoId;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMatriculaCarreraId() { return matriculaCarreraId; }
    public void setMatriculaCarreraId(int matriculaCarreraId) { this.matriculaCarreraId = matriculaCarreraId; }

    public int getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(int periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }

    public int getPlanPagoId() { return planPagoId; }
    public void setPlanPagoId(int planPagoId) { this.planPagoId = planPagoId; }

    public LocalDateTime getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDateTime fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
