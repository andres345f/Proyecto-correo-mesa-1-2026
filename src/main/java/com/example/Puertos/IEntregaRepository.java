package com.example.Puertos;

import com.example.Dominio.Entrega;
import java.util.List;

public interface IEntregaRepository {
    void guardar(Entrega entrega) throws Exception;
    void actualizar(Entrega entrega) throws Exception;
    void eliminar(int id) throws Exception;
    Entrega obtenerPorId(int id) throws Exception;
    List<Entrega> listar() throws Exception;
}
