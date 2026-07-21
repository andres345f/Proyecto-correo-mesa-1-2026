package com.example.Puertos;

import com.example.Dominio.MatriculaPeriodo;
import java.util.List;

public interface IMatriculaPeriodoRepository {
    void guardar(MatriculaPeriodo mp) throws Exception;
    void actualizar(MatriculaPeriodo mp) throws Exception;
    void eliminar(int id) throws Exception;
    MatriculaPeriodo obtenerPorId(int id) throws Exception;
    List<MatriculaPeriodo> listar() throws Exception;
}
