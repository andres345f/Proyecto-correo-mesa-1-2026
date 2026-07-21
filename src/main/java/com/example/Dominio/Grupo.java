package com.example.Dominio;

public class Grupo {
    private int id;
    private String codigo;
    private int materiaId;

    public Grupo() {}

    public Grupo(int id, String codigo, int materiaId) {
        this.id = id;
        setCodigo(codigo);
        this.materiaId = materiaId;
    }

    public Grupo(String codigo, int materiaId) {
        this(0, codigo, materiaId);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del grupo es obligatorio.");
        }
        this.codigo = codigo.trim();
    }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }
}
