package com.example.Aplicacion;

import com.example.Dominio.Cuota;
import com.example.Dominio.MatriculaCarrera;
import com.example.Dominio.MatriculaPeriodo;
import com.example.Dominio.Pago;
import com.example.Dominio.Usuario;
import com.example.Puertos.ICuotaRepository;
import com.example.Puertos.IMatriculaCarreraRepository;
import com.example.Puertos.IMatriculaPeriodoRepository;
import com.example.Puertos.IPagoRepository;
import com.example.Puertos.IUsuarioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.bebidas.infraestructure.servicioemail.PagoFacilGateway;
import org.bebidas.infraestructure.servicioemail.PagoFacilGateway.QrResult;

public class PagoService {
    private final IPagoRepository repository;
    private final ICuotaRepository cuotaRepository;
    private final IMatriculaPeriodoRepository matriculaPeriodoRepository;
    private final IMatriculaCarreraRepository matriculaCarreraRepository;
    private final IUsuarioRepository usuarioRepository;
    private final PagoFacilGateway pagoFacilGateway;

    public PagoService(IPagoRepository repository,
                       ICuotaRepository cuotaRepository,
                       IMatriculaPeriodoRepository matriculaPeriodoRepository,
                       IMatriculaCarreraRepository matriculaCarreraRepository,
                       IUsuarioRepository usuarioRepository,
                       PagoFacilGateway pagoFacilGateway) {
        this.repository = repository;
        this.cuotaRepository = cuotaRepository;
        this.matriculaPeriodoRepository = matriculaPeriodoRepository;
        this.matriculaCarreraRepository = matriculaCarreraRepository;
        this.usuarioRepository = usuarioRepository;
        this.pagoFacilGateway = pagoFacilGateway;
    }

    public String crearPago(int cuotaId) {
        try {
            // Buscar la cuota para obtener el monto
            Cuota cuota = cuotaRepository.obtenerPorId(cuotaId);
            if (cuota == null) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: Cuota con ID " + cuotaId + " no encontrada.</p>";
            }

            BigDecimal montoPagado = cuota.getMonto();

            // Buscar datos del estudiante para generar el QR
            MatriculaPeriodo mp = matriculaPeriodoRepository.obtenerPorId(cuota.getMatriculaPeriodoId());
            if (mp == null) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: Matrícula de periodo no encontrada.</p>";
            }

            MatriculaCarrera mc = matriculaCarreraRepository.obtenerPorId(mp.getMatriculaCarreraId());
            if (mc == null) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: Matrícula de carrera no encontrada.</p>";
            }

            Usuario usuario = usuarioRepository.obtenerPorId(mc.getUsuarioId());
            if (usuario == null) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: Usuario no encontrado.</p>";
            }

            // Generar QR via PagoFacil
            String paymentNumber = "PAG-" + System.currentTimeMillis();
            String concepto = cuota.getDescripcion() + " - CuotaID " + cuotaId;
            String nombreCliente = usuario.getName() != null ? usuario.getName() : "Estudiante";
            String emailCliente = usuario.getEmail() != null ? usuario.getEmail() : "";
            // Usar ID como fallback mientras no se almacene CI/teléfono
            String ciCliente = String.valueOf(mc.getUsuarioId());
            String telefonoCliente = "";

            QrResult qr = pagoFacilGateway.generarQr(
                paymentNumber, montoPagado, concepto,
                (long) mc.getUsuarioId(), nombreCliente, ciCliente,
                telefonoCliente, emailCliente
            );

            // Usar el transactionId real devuelto por PagoFacil
            String realTransaccionId = qr.transactionId;

            Pago pago = new Pago(0, cuotaId, montoPagado, "qr_pagofacil", realTransaccionId, LocalDateTime.now(), "pendiente");
            repository.guardar(pago);

            // Construir HTML con el QR incrustado
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html>\n<head>\n");
            html.append("<meta charset='UTF-8'>\n");
            html.append("<style>\n");
            html.append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n");
            html.append(".container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); text-align: center; }\n");
            html.append("h2 { color: #27ae60; }\n");
            html.append(".qr-img { max-width: 300px; margin: 20px 0; }\n");
            html.append(".info { text-align: left; background: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }\n");
            html.append(".info label { font-weight: bold; color: #2c3e50; }\n");
            html.append("</style>\n");
            html.append("</head>\n<body>\n");
            html.append("<div class='container'>\n");
            html.append("<h2>✅ Pago Registrado</h2>\n");
            html.append("<div class='info'>\n");
            html.append("<p><label>ID Pago:</label> ").append(pago.getId()).append("</p>\n");
            html.append("<p><label>Cuota:</label> ").append(cuota.getDescripcion()).append(" (ID ").append(cuotaId).append(")</p>\n");
            html.append("<p><label>Monto:</label> $").append(montoPagado.stripTrailingZeros().toPlainString()).append("</p>\n");
            html.append("<p><label>Método:</label> PagoFácil QR</p>\n");
            html.append("<p><label>Transacción:</label> ").append(realTransaccionId).append("</p>\n");
            html.append("<p><label>Estado:</label> pendiente</p>\n");
            html.append("<p><label>Estudiante:</label> ").append(nombreCliente).append("</p>\n");
            html.append("</div>\n");
            html.append("<h3>📱 Escanea el código QR para pagar</h3>\n");
            html.append("<img class='qr-img' src=\"data:image/png;base64,").append(qr.qrImage).append("\" alt=\"QR de pago\" />\n");
            html.append("<p style='color: #7f8c8d; font-size: 12px;'>Número de pago: ").append(paymentNumber).append("</p>\n");
            html.append("</div>\n</body>\n</html>");

            return html.toString();
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al registrar pago: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarPago(int id, int cuotaId, BigDecimal montoPagado, String metodoPago,
                                String transaccionId, String estado) {
        try {
            Pago pago = repository.obtenerPorId(id);
            if (pago == null) {
                return "<p style='color: #e74c3c;'>Pago con ID " + id + " no encontrado.</p>";
            }
            pago.setCuotaId(cuotaId);
            pago.setMontoPagado(montoPagado);
            pago.setMetodoPago(metodoPago);
            pago.setTransaccionId(transaccionId);
            pago.setEstado(estado);
            repository.actualizar(pago);
            return "<p style='color: #27ae60; font-weight: bold;'>Pago actualizado exitosamente.</p>";
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar pago: " + e.getMessage() + "</p>";
        }
    }

    public String consultarEstadoPago(int id) {
        try {
            Pago pago = repository.obtenerPorId(id);
            if (pago == null) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: Pago con ID " + id + " no encontrado.</p>";
            }

            String transaccionId = pago.getTransaccionId();
            if (transaccionId == null || transaccionId.trim().isEmpty()) {
                return "<p style='color: #e74c3c; font-weight: bold;'>Error: El pago no tiene transacción asociada.</p>";
            }

            // Consultar estado en PagoFacil
            PagoFacilGateway.QueryResult result = pagoFacilGateway.consultarTransaccion(transaccionId);

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset='UTF-8'>\n");
            html.append("<style>\n");
            html.append("body { font-family: Arial, sans-serif; padding: 20px; }\n");
            html.append(".info { background: #f9f9f9; padding: 15px; border-radius: 5px; margin: 10px 0; }\n");
            html.append(".info label { font-weight: bold; color: #2c3e50; }\n");
            html.append(".pagado { color: #27ae60; font-weight: bold; }\n");
            html.append(".pendiente { color: #f39c12; font-weight: bold; }\n");
            html.append(".cancelado { color: #e74c3c; font-weight: bold; }\n");
            html.append("</style>\n</head>\n<body>\n");
            html.append("<h2>Estado del Pago #").append(id).append("</h2>\n");
            html.append("<div class='info'>\n");
            html.append("<p><label>Transacción:</label> ").append(transaccionId).append("</p>\n");
            html.append("<p><label>Estado en BD:</label> ").append(pago.getEstado()).append("</p>\n");
            html.append("<p><label>Estado en PagoFácil:</label> ").append(result.status).append("</p>\n");
            html.append("<p><label>Descripción:</label> ").append(result.description != null ? result.description : "").append("</p>\n");

            if ("PAGADO".equalsIgnoreCase(result.status)) {
                pago.setEstado("completado");
                repository.actualizar(pago);

                // Actualizar estado de la cuota a pagado
                Cuota cuota = cuotaRepository.obtenerPorId(pago.getCuotaId());
                if (cuota != null) {
                    cuota.setEstado("pagado");
                    cuotaRepository.actualizar(cuota);
                }

                html.append("<p class='pagado'>✅ Pago CONFIRMADO - Estado actualizado a 'completado'</p>\n");
                html.append("<p><label>Cuota:</label> ").append(cuota != null ? cuota.getDescripcion() + " → pagado" : "ID " + pago.getCuotaId()).append("</p>\n");
                html.append("<p><label>Pagador:</label> ").append(result.payerName != null ? result.payerName : "").append("</p>\n");
                html.append("<p><label>Banco:</label> ").append(result.payerBank != null ? result.payerBank : "").append("</p>\n");
                html.append("<p><label>Observaciones:</label> Confirmado mediante consulta a PagoFácil API" +
                    (result.paymentDate != null ? " el " + result.paymentDate + " " + (result.paymentTime != null ? result.paymentTime : "") : "") +
                    "</p>\n");
            } else if ("CANCELADO".equalsIgnoreCase(result.status)) {
                pago.setEstado("cancelado");
                repository.actualizar(pago);
                html.append("<p class='cancelado'>❌ Pago CANCELADO</p>\n");
            } else {
                html.append("<p class='pendiente'>⏳ Pago aún PENDIENTE en PagoFácil</p>\n");
            }

            html.append("</div>\n</body>\n</html>");
            return html.toString();
        } catch (IllegalArgumentException e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Regla de Negocio: " + e.getMessage() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al consultar estado del pago: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarPago(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Pago con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar pago: " + e.getMessage() + "</p>";
        }
    }

    public Pago obtenerPago(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<Pago> listarPagos() throws Exception {
        return repository.listar();
    }
}
