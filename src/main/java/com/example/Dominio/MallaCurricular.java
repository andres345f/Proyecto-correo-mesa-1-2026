package com.example.Dominio;

public class MallaCurricular {
    private int id;
    private int ofertaAcademicaId;
    private int materiaId;
    private int semestreOrden;

    public MallaCurricular() {}

    public MallaCurricular(int id, int ofertaAcademicaId, int materiaId, int semestreOrden) {
        this.id = id;
        this.ofertaAcademicaId = ofertaAcademicaId;
        this.materiaId = materiaId;
        setSemestreOrden(semestreOrden);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOfertaAcademicaId() { return ofertaAcademicaId; }
    public void setOfertaAcademicaId(int ofertaAcademicaId) { this.ofertaAcademicaId = ofertaAcademicaId; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public int getSemestreOrden() { return semestreOrden; }
    public void setSemestreOrden(int semestreOrden) {
        if (semestreOrden <= 0) {
            throw new IllegalArgumentException("El semestre debe ser un número positivo.");
        }
        this.semestreOrden = semestreOrden;
    }
}
