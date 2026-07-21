package com.example.Dominio;

import java.math.BigDecimal;

public class PlanPago {
    private int id;
    private int ofertaAcademicaId;
    private String nombre;
    private String tipo;
    private BigDecimal montoMatricula;
    private BigDecimal montoCuota;
    private int cantidadCuotas;

    public PlanPago() {}

    public PlanPago(int id, int ofertaAcademicaId, String nombre, String tipo,
                   BigDecimal montoMatricula, BigDecimal montoCuota, int cantidadCuotas) {
        this.id = id;
        this.ofertaAcademicaId = ofertaAcademicaId;
        setNombre(nombre);
        setTipo(tipo);
        setMontoMatricula(montoMatricula);
        setMontoCuota(montoCuota);
        setCantidadCuotas(cantidadCuotas);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOfertaAcademicaId() { return ofertaAcademicaId; }
    public void setOfertaAcademicaId(int ofertaAcademicaId) { this.ofertaAcademicaId = ofertaAcademicaId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan de pago es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de plan de pago es obligatorio.");
        }
        this.tipo = tipo.trim();
    }

    public BigDecimal getMontoMatricula() { return montoMatricula; }
    public void setMontoMatricula(BigDecimal montoMatricula) {
        if (montoMatricula == null || montoMatricula.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto de matrícula no puede ser negativo.");
        }
        this.montoMatricula = montoMatricula;
    }

    public BigDecimal getMontoCuota() { return montoCuota; }
    public void setMontoCuota(BigDecimal montoCuota) {
        if (montoCuota == null || montoCuota.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto de cuota no puede ser negativo.");
        }
        this.montoCuota = montoCuota;
    }

    public int getCantidadCuotas() { return cantidadCuotas; }
    public void setCantidadCuotas(int cantidadCuotas) {
        if (cantidadCuotas <= 0) {
            throw new IllegalArgumentException("La cantidad de cuotas debe ser mayor a cero.");
        }
        this.cantidadCuotas = cantidadCuotas;
    }
}
