package com.example.Dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Entrega {
    private int id;
    private int tareaId;
    private int usuarioId;
    private String rutaArchivo;
    private LocalDateTime fechaEntrega;
    private BigDecimal nota;
    private String retroalimentacion;

    public Entrega() {}

    public Entrega(int id, int tareaId, int usuarioId, String rutaArchivo, LocalDateTime fechaEntrega,
                  BigDecimal nota, String retroalimentacion) {
        this.id = id;
        this.tareaId = tareaId;
        this.usuarioId = usuarioId;
        this.rutaArchivo = rutaArchivo;
        this.fechaEntrega = fechaEntrega;
        this.nota = nota;
        this.retroalimentacion = retroalimentacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTareaId() { return tareaId; }
    public void setTareaId(int tareaId) { this.tareaId = tareaId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public BigDecimal getNota() { return nota; }
    public void setNota(BigDecimal nota) { this.nota = nota; }

    public String getRetroalimentacion() { return retroalimentacion; }
    public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
}
