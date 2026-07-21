package com.example.Dominio;

import java.math.BigDecimal;

public class MatriculaGrupo {
    private int id;
    private int matriculaPeriodoId;
    private int grupoPeriodoId;
    private BigDecimal notaFinal;
    private String estado;

    public MatriculaGrupo() {}

    public MatriculaGrupo(int id, int matriculaPeriodoId, int grupoPeriodoId, BigDecimal notaFinal, String estado) {
        this.id = id;
        this.matriculaPeriodoId = matriculaPeriodoId;
        this.grupoPeriodoId = grupoPeriodoId;
        this.notaFinal = notaFinal;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMatriculaPeriodoId() { return matriculaPeriodoId; }
    public void setMatriculaPeriodoId(int matriculaPeriodoId) { this.matriculaPeriodoId = matriculaPeriodoId; }

    public int getGrupoPeriodoId() { return grupoPeriodoId; }
    public void setGrupoPeriodoId(int grupoPeriodoId) { this.grupoPeriodoId = grupoPeriodoId; }

    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
