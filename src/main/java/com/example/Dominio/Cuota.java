package com.example.Dominio;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cuota {
    private int id;
    private int matriculaPeriodoId;
    private String descripcion;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private String estado;

    public Cuota() {}

    public Cuota(int id, int matriculaPeriodoId, String descripcion, BigDecimal monto,
                LocalDate fechaVencimiento, String estado) {
        this.id = id;
        this.matriculaPeriodoId = matriculaPeriodoId;
        setDescripcion(descripcion);
        setMonto(monto);
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMatriculaPeriodoId() { return matriculaPeriodoId; }
    public void setMatriculaPeriodoId(int matriculaPeriodoId) { this.matriculaPeriodoId = matriculaPeriodoId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la cuota es obligatoria.");
        }
        this.descripcion = descripcion.trim();
    }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la cuota debe ser mayor a cero.");
        }
        this.monto = monto;
    }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
