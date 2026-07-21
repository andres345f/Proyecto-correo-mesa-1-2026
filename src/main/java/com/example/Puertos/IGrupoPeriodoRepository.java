package com.example.Puertos;

import com.example.Dominio.GrupoPeriodo;
import java.util.List;

public interface IGrupoPeriodoRepository {
    void guardar(GrupoPeriodo gp) throws Exception;
    void actualizar(GrupoPeriodo gp) throws Exception;
    void eliminar(int id) throws Exception;
    GrupoPeriodo obtenerPorId(int id) throws Exception;
    List<GrupoPeriodo> listar() throws Exception;
}
