package com.example.Dominio;

public class GrupoPeriodo {
    private int id;
    private int grupoId;
    private int periodoAcademicoId;
    private Integer docenteId;
    private int cupoMaximo;

    public GrupoPeriodo() {}

    public GrupoPeriodo(int id, int grupoId, int periodoAcademicoId, Integer docenteId, int cupoMaximo) {
        this.id = id;
        this.grupoId = grupoId;
        this.periodoAcademicoId = periodoAcademicoId;
        this.docenteId = docenteId;
        setCupoMaximo(cupoMaximo);
    }

    public GrupoPeriodo(int grupoId, int periodoAcademicoId, int cupoMaximo) {
        this(0, grupoId, periodoAcademicoId, null, cupoMaximo);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGrupoId() { return grupoId; }
    public void setGrupoId(int grupoId) { this.grupoId = grupoId; }

    public int getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(int periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }

    public Integer getDocenteId() { return docenteId; }
    public void setDocenteId(Integer docenteId) { this.docenteId = docenteId; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) {
        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException("El cupo máximo debe ser mayor a cero.");
        }
        this.cupoMaximo = cupoMaximo;
    }
}
