package com.example.Dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pago {
    private int id;
    private int cuotaId;
    private BigDecimal montoPagado;
    private String metodoPago;
    private String transaccionId;
    private LocalDateTime fechaPago;
    private String estado;

    public Pago() {}

    public Pago(int id, int cuotaId, BigDecimal montoPagado, String metodoPago,
               String transaccionId, LocalDateTime fechaPago, String estado) {
        this.id = id;
        this.cuotaId = cuotaId;
        setMontoPagado(montoPagado);
        this.metodoPago = metodoPago;
        setTransaccionId(transaccionId);
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCuotaId() { return cuotaId; }
    public void setCuotaId(int cuotaId) { this.cuotaId = cuotaId; }

    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) {
        if (montoPagado == null || montoPagado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto pagado debe ser mayor a cero.");
        }
        this.montoPagado = montoPagado;
    }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getTransaccionId() { return transaccionId; }
    public void setTransaccionId(String transaccionId) {
        if (transaccionId == null || transaccionId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de transacción es obligatorio.");
        }
        this.transaccionId = transaccionId.trim();
    }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
