package com.example.Dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Tarea {
    private int id;
    private int grupoPeriodoId;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaVencimiento;
    private BigDecimal puntajeMaximo;

    public Tarea() {}

    public Tarea(int id, int grupoPeriodoId, String titulo, String descripcion,
                LocalDateTime fechaVencimiento, BigDecimal puntajeMaximo) {
        this.id = id;
        this.grupoPeriodoId = grupoPeriodoId;
        setTitulo(titulo);
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        setPuntajeMaximo(puntajeMaximo);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGrupoPeriodoId() { return grupoPeriodoId; }
    public void setGrupoPeriodoId(int grupoPeriodoId) { this.grupoPeriodoId = grupoPeriodoId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio.");
        }
        this.titulo = titulo.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) {
        if (puntajeMaximo == null || puntajeMaximo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El puntaje máximo debe ser mayor a cero.");
        }
        this.puntajeMaximo = puntajeMaximo;
    }
}
