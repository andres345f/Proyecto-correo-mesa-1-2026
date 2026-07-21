package com.example.Dominio;

public class Aula {
    private int id;
    private String nombre;
    private String codigo;
    private int capacidad;

    public Aula() {}

    public Aula(int id, String nombre, String codigo, int capacidad) {
        this.id = id;
        setNombre(nombre);
        setCodigo(codigo);
        setCapacidad(capacidad);
    }

    public Aula(String nombre, String codigo, int capacidad) {
        this(0, nombre, codigo, capacidad);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del aula es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del aula es obligatorio.");
        }
        this.codigo = codigo.trim();
    }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero.");
        }
        this.capacidad = capacidad;
    }
}
