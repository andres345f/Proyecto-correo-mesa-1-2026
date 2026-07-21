package com.example.Dominio;

public class Materia {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;

    public Materia() {}

    public Materia(int id, String codigo, String nombre, String descripcion) {
        this.id = id;
        setCodigo(codigo);
        setNombre(nombre);
        this.descripcion = descripcion;
    }

    public Materia(String codigo, String nombre) {
        this(0, codigo, nombre, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la materia es obligatorio.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la materia es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
