package com.example.Puertos;

import com.example.Dominio.MatriculaGrupo;
import java.util.List;

public interface IMatriculaGrupoRepository {
    void guardar(MatriculaGrupo mg) throws Exception;
    void actualizar(MatriculaGrupo mg) throws Exception;
    void eliminar(int id) throws Exception;
    MatriculaGrupo obtenerPorId(int id) throws Exception;
    List<MatriculaGrupo> listar() throws Exception;
}
