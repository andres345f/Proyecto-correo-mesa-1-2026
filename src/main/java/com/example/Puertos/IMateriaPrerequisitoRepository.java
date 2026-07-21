package com.example.Puertos;

import com.example.Dominio.MateriaPrerequisito;
import java.util.List;

public interface IMateriaPrerequisitoRepository {
    void guardar(MateriaPrerequisito prerequisito) throws Exception;
    void actualizar(MateriaPrerequisito prerequisito) throws Exception;
    void eliminar(int id) throws Exception;
    MateriaPrerequisito obtenerPorId(int id) throws Exception;
    List<MateriaPrerequisito> listar() throws Exception;
}
