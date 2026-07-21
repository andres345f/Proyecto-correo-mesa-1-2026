package com.example.Puertos;

import com.example.Dominio.PlanPago;
import java.util.List;

public interface IPlanPagoRepository {
    void guardar(PlanPago plan) throws Exception;
    void actualizar(PlanPago plan) throws Exception;
    void eliminar(int id) throws Exception;
    PlanPago obtenerPorId(int id) throws Exception;
    List<PlanPago> listar() throws Exception;
}
