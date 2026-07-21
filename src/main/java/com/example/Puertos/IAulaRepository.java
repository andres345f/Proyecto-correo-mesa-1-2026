package com.example.Puertos;

import com.example.Dominio.Aula;
import java.util.List;

public interface IAulaRepository {
    void guardar(Aula aula) throws Exception;
    void actualizar(Aula aula) throws Exception;
    void eliminar(int id) throws Exception;
    Aula obtenerPorId(int id) throws Exception;
    List<Aula> listar() throws Exception;
}
