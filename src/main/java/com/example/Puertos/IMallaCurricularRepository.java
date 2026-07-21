package com.example.Puertos;

import com.example.Dominio.MallaCurricular;
import java.util.List;

public interface IMallaCurricularRepository {
    void guardar(MallaCurricular malla) throws Exception;
    void actualizar(MallaCurricular malla) throws Exception;
    void eliminar(int id) throws Exception;
    MallaCurricular obtenerPorId(int id) throws Exception;
    List<MallaCurricular> listar() throws Exception;
}
