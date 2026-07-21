package com.example.Puertos;

import com.example.Dominio.OfertaAcademica;
import java.util.List;

public interface IOfertaAcademicaRepository {
    void guardar(OfertaAcademica oferta) throws Exception;
    void actualizar(OfertaAcademica oferta) throws Exception;
    void eliminar(int id) throws Exception;
    OfertaAcademica obtenerPorId(int id) throws Exception;
    List<OfertaAcademica> listar() throws Exception;
}
