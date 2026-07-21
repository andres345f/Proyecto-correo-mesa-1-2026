package com.example.Puertos;

import com.example.Dominio.Pago;
import java.util.List;

public interface IPagoRepository {
    void guardar(Pago pago) throws Exception;
    void actualizar(Pago pago) throws Exception;
    void eliminar(int id) throws Exception;
    Pago obtenerPorId(int id) throws Exception;
    List<Pago> listar() throws Exception;
}
