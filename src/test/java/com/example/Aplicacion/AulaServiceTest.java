package com.example.Aplicacion;

import com.example.Dominio.Aula;
import com.example.Puertos.IAulaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AulaServiceTest {

    @Test
    void crearAulaConDatosValidos() {
        IAulaRepository repo = new IAulaRepository() {
            private Aula saved;

            @Override
            public void guardar(Aula aula) {
                saved = aula;
            }

            @Override
            public void actualizar(Aula aula) {}

            @Override
            public void eliminar(int id) {}

            @Override
            public Aula obtenerPorId(int id) {
                return saved;
            }

            @Override
            public java.util.List<Aula> listar() {
                return null;
            }
        };

        AulaService service = new AulaService(repo);
        String result = service.crearAula("Aula 101", "A101", 30);

        assertTrue(result.contains("creada"));
    }

    @Test
    void crearAulaConNombreVacioRetornaError() {
        IAulaRepository repo = new IAulaRepository() {
            @Override
            public void guardar(Aula aula) {}

            @Override
            public void actualizar(Aula aula) {}

            @Override
            public void eliminar(int id) {}

            @Override
            public Aula obtenerPorId(int id) { return null; }

            @Override
            public java.util.List<Aula> listar() { return null; }
        };

        AulaService service = new AulaService(repo);
        String result = service.crearAula("", "A101", 30);

        assertTrue(result.contains("Regla de Negocio"));
    }
}
