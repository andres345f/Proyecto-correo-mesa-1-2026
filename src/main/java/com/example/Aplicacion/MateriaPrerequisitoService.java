package com.example.Aplicacion;

import com.example.Dominio.MateriaPrerequisito;
import com.example.Puertos.IMateriaPrerequisitoRepository;
import java.util.List;

public class MateriaPrerequisitoService {
    private final IMateriaPrerequisitoRepository repository;

    public MateriaPrerequisitoService(IMateriaPrerequisitoRepository repository) {
        this.repository = repository;
    }

    public String crearPrerequisito(int mallaCurricularId, int prerequisitoMallaId) {
        try {
            MateriaPrerequisito mp = new MateriaPrerequisito(0, mallaCurricularId, prerequisitoMallaId);
            repository.guardar(mp);
            return "<p style='color: #27ae60; font-weight: bold;'>Prerrequisito creado: ID=" + mp.getId() + ", Malla ID=" + mp.getMallaCurricularId() + ", Prerrequisito Malla ID=" + mp.getPrerequisitoMallaId() + "</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al guardar prerrequisito: " + e.getMessage() + "</p>";
        }
    }

    public String eliminarPrerequisito(int id) {
        try {
            repository.eliminar(id);
            return "<p style='color: #27ae60; font-weight: bold;'>Prerrequisito con ID " + id + " eliminado exitosamente.</p>";
        } catch (Exception e) {
            return "<p style='color: #e74c3c; font-weight: bold;'>Error al eliminar prerrequisito: " + e.getMessage() + "</p>";
        }
    }

    public MateriaPrerequisito obtenerPrerequisito(int id) throws Exception {
        return repository.obtenerPorId(id);
    }

    public List<MateriaPrerequisito> listarPrerequisitos() throws Exception {
        return repository.listar();
    }
}
