package com.example.Dominio;

public class OfertaAcademica {
    private int id;
    private String nombre;
    private String codigo;
    private String descripcion;

    public OfertaAcademica() {}

    public OfertaAcademica(int id, String nombre, String codigo, String descripcion) {
        this.id = id;
        setNombre(nombre);
        setCodigo(codigo);
        this.descripcion = descripcion;
    }

    public OfertaAcademica(String nombre, String codigo) {
        this(0, nombre, codigo, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la oferta académica es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la oferta académica es obligatorio.");
        }
        this.codigo = codigo.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
