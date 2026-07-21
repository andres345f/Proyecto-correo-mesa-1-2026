package com.example.Puertos;

import com.example.Dominio.Cuota;
import java.util.List;

public interface ICuotaRepository {
    void guardar(Cuota cuota) throws Exception;
    void actualizar(Cuota cuota) throws Exception;
    void eliminar(int id) throws Exception;
    Cuota obtenerPorId(int id) throws Exception;
    List<Cuota> listar() throws Exception;
}
