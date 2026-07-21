package com.example.Puertos;

import com.example.Dominio.PeriodoAcademico;
import java.util.List;

public interface IPeriodoAcademicoRepository {
    void guardar(PeriodoAcademico periodo) throws Exception;
    void actualizar(PeriodoAcademico periodo) throws Exception;
    void eliminar(int id) throws Exception;
    PeriodoAcademico obtenerPorId(int id) throws Exception;
    List<PeriodoAcademico> listar() throws Exception;
}
