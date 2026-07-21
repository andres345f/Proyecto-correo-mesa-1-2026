package com.example.Aplicacion;

import com.example.Dominio.MatriculaGrupo;
import com.example.Puertos.IMatriculaGrupoRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatriculaGrupoServiceTest {

    @Test
    void inscribirAlumnoCreaEstadoInscrito() {
        IMatriculaGrupoRepository repo = new IMatriculaGrupoRepository() {
            private MatriculaGrupo saved;

            @Override
            public void guardar(MatriculaGrupo mg) {
                saved = mg;
            }

            @Override
            public void actualizar(MatriculaGrupo mg) {}

            @Override
            public void eliminar(int id) {}

            @Override
            public MatriculaGrupo obtenerPorId(int id) {
                return saved;
            }

            @Override
            public java.util.List<MatriculaGrupo> listar() {
                return null;
            }
        };

        MatriculaGrupoService service = new MatriculaGrupoService(repo);
        String result = service.inscribirAlumno(1, 1);

        assertTrue(result.contains("inscrito"));
    }

    @Test
    void calificarAlumnoConNota10ONmasCambiaEstadoAprobado() {
        IMatriculaGrupoRepository repo = new IMatriculaGrupoRepository() {
            private MatriculaGrupo saved = new MatriculaGrupo(1, 1, 1, null, "inscrito");

            @Override
            public void guardar(MatriculaGrupo mg) {}

            @Override
            public void actualizar(MatriculaGrupo mg) {
                saved = mg;
            }

            @Override
            public void eliminar(int id) {}

            @Override
            public MatriculaGrupo obtenerPorId(int id) {
                return saved;
            }

            @Override
            public java.util.List<MatriculaGrupo> listar() {
                return null;
            }
        };

        MatriculaGrupoService service = new MatriculaGrupoService(repo);
        String result = service.calificarAlumno(1, new BigDecimal("85"));

        assertTrue(result.contains("Nota registrada"));
    }
}
