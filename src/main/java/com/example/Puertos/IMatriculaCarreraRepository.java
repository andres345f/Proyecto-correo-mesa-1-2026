package com.example.Puertos;

import com.example.Dominio.MatriculaCarrera;
import java.util.List;

public interface IMatriculaCarreraRepository {
    void guardar(MatriculaCarrera mc) throws Exception;
    void actualizar(MatriculaCarrera mc) throws Exception;
    void eliminar(int id) throws Exception;
    MatriculaCarrera obtenerPorId(int id) throws Exception;
    List<MatriculaCarrera> listar() throws Exception;
}
