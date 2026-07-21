package com.example.Puertos;

import com.example.Dominio.Tarea;
import java.util.List;

public interface ITareaRepository {
    void guardar(Tarea tarea) throws Exception;
    void actualizar(Tarea tarea) throws Exception;
    void eliminar(int id) throws Exception;
    Tarea obtenerPorId(int id) throws Exception;
    List<Tarea> listar() throws Exception;
}
