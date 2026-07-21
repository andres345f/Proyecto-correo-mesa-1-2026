package com.example.Aplicacion;

import com.example.Dominio.Cuota;
import com.example.Dominio.MatriculaPeriodo;
import com.example.Dominio.PlanPago;
import com.example.Puertos.ICuotaRepository;
import com.example.Puertos.IMatriculaPeriodoRepository;
import com.example.Puertos.IPlanPagoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MatriculaPeriodoService {
    private final IMatriculaPeriodoRepository repository;
    private final IPlanPagoRepository planPagoRepository;
    private final ICuotaRepository cuotaRepository;

    public MatriculaPeriodoService(IMatriculaPeriodoRepository repository,
                                    IPlanPagoRepository planPagoRepository,
                                    ICuotaRepository cuotaRepository) {
        this.repository = repository;
        this.planPagoRepository = planPagoRepository;
        this.cuotaRepository = cuotaRepository;
    }

    public String crearMatriculaPeriodo(int matriculaCarreraId, int periodoAcademicoId, int planPagoId) {
        try {
            MatriculaPeriodo mp = new MatriculaPeriodo(0, matriculaCarreraId, periodoAcademicoId, planPagoId, LocalDateTime.now(), "activo");
            repository.guardar(mp);

            // Auto-generar cuotas según el plan de pago
            int cuotasGeneradas = 0;
            BigDecimal totalCuotas = BigDecimal.ZERO;
            StringBuilder detalleCuotas = new StringBuilder();
            PlanPago plan = planPagoRepository.obtenerPorId(planPagoId);
            if (plan != null) {
                LocalDate baseDate = mp.getFechaMatricula().toLocalDate();

                // 1. Cuota de matrícula (vence 1 mes después de la inscripción)
                Cuota cuotaMat = new Cuota(0, mp.getId(), "Matrícula",
                    plan.getMontoMatricula(), baseDate.plusMonths(1), "pendiente");
                cuotaRepository.guardar(cuotaMat);
                cuotasGeneradas++;
                totalCuotas = totalCuotas.add(plan.getMontoMatricula());
                String montoMatStr = plan.getMontoMatricula().stripTrailingZeros().toPlainString();
                detalleCuotas.append("<tr><td>").append(cuotaMat.getId()).append("</td><td>Matrícula</td><td>$")
                    .append(montoMatStr).append("</td><td>").append(baseDate.plusMonths(1)).append("</td></tr>\n");

                // 2. Cuotas mensuales
                for (int i = 1; i <= plan.getCantidadCuotas(); i++) {
                    Cuota cuota = new Cuota(0, mp.getId(), "Cuota " + i,
                        plan.getMontoCuota(), baseDate.plusMonths(1 + i), "pendiente");
                    cuotaRepository.guardar(cuota);
                    cuotasGeneradas++;
                    totalCuotas = totalCuotas.add(plan.getMontoCuota());
                    String montoCuotaStr = plan.getMontoCuota().stripTrailingZeros().toPlainString();
                    detalleCuotas.append("<tr><td>").append(cuota.getId()).append("</td><td>Cuota ").append(i)
                        .append("</td><td>$").append(montoCuotaStr).append("</td><td>")
                        .append(baseDate.plusMonths(1 + i)).append("</td></tr>\n");
                }
            }

            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de periodo creada: ID=" + mp.getId()
                + ", MatCarreraID=" + mp.getMatriculaCarreraId()
                + ", PeriodoID=" + mp.getPeriodoAcademicoId()
                + ", PlanPagoID=" + mp.getPlanPagoId()
                + ", Estado=" + mp.getEstado()
                + "</p><p style='color: #2980b9; font-weight: bold;'>📋 " + cuotasGeneradas
                + " cuotas generadas automáticamente por un total de $" + totalCuotas.stripTrailingZeros().toPlainString() + "</p>"
                + "<table border='1' style='border-collapse: collapse; margin-top: 10px; width: 100%;'>"
                + "<thead><tr style='background-color: #2980b9; color: white;'>"
                + "<th style='padding: 6px;'>#</th><th style='padding: 6px;'>Descripción</th>"
                + "<th style='padding: 6px;'>Monto</th><th style='padding: 6px;'>Vencimiento</th>"
                + "</tr></thead><tbody>" + detalleCuotas.toString() + "</tbody></table>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar matrícula de periodo: " + e.getMessage() + "</p>";
        }
    }

    public String actualizarMatriculaPeriodo(int id, int matriculaCarreraId, int periodoAcademicoId, int planPagoId, String estado) {
        try {
            MatriculaPeriodo mp = repository.obtenerPorId(id);
            if (mp == null) {
                return "<p style='color: #e74c3c;'>Matrícula de periodo con ID " + id + " no encontrada.</p>";
            }
            mp.setMatriculaCarreraId(matriculaCarreraId);
            mp.setPeriodoAcademicoId(periodoAcademicoId);
            mp.setPlanPagoId(planPagoId);
            mp.setEstado(estado);
            repository.actualizar(mp);
            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de periodo actualizada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al actualizar matrícula de periodo: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarMatriculaPeriodo(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Matrícula de periodo con ID " + id + " eliminada exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar matrícula de periodo: " + e.getMessage() + "</p>";
        }
    }

    public MatriculaPeriodo obtenerMatriculaPeriodo(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<MatriculaPeriodo> listarMatriculasPeriodo() throws Exception {
        return repository.listar();
    }
}
