package com.example.Aplicacion;

import java.util.ArrayList;
import java.util.List;

public class CommandHelpHTML {

    private static class CommandExample {
        String operation;
        String entity;
        String description;
        String parameters;
        String example;

        public CommandExample(String operation, String entity, String description, String parameters, String example) {
            this.operation = operation;
            this.entity = entity;
            this.description = description;
            this.parameters = parameters;
            this.example = example;
        }
    }

    private static final List<CommandExample> COMMANDS = new ArrayList<>();

    static {
        // CREATE commands - Academic Management
        COMMANDS.add(new CommandExample("CREATE", "AULAS",
            "Crear un aula", "nombre, codigo, capacidad",
            "CREATEAULAS[Aula 101, A101, 30]"));

        COMMANDS.add(new CommandExample("CREATE", "OFERTAS",
            "Crear una oferta académica", "nombre, codigo, [descripcion]",
            "CREATEOFERTAS[Ingeniería de Sistemas, SIS, Carrera de Ingeniería]"));

        COMMANDS.add(new CommandExample("CREATE", "PERIODOS",
            "Crear un periodo académico", "ofertaId, nombre, tipo, fechaInicio, fechaFin",
            "CREATEPERIODOS[1, 2026-1, SEMESTRAL, 2026-02-01, 2026-06-30]"));

        COMMANDS.add(new CommandExample("CREATE", "MATERIAS",
            "Crear una materia", "codigo, nombre, [descripcion]",
            "CREATEMATERIAS[INF101, Programación I, Introducción a la programación]"));

        COMMANDS.add(new CommandExample("CREATE", "MALLA",
            "Crear malla curricular", "ofertaId, materiaId, semestreOrden",
            "CREATEMALLA[1, 1, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "PREREQUISITOS",
            "Crear prerrequisito", "mallaId, prerequisitoMallaId",
            "CREATEPREREQUISITOS[2, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "GRUPOS",
            "Crear un grupo", "codigo, materiaId",
            "CREATEGRUPOS[Grupo A, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "GRUPOPERIODOS",
            "Asignar grupo a periodo con docente", "grupoId, periodoId, cupoMaximo, [docenteId]",
            "CREATEGRUPOPERIODOS[1, 1, 35, 2]"));

        COMMANDS.add(new CommandExample("CREATE", "HORARIOS",
            "Crear horario para grupo-periodo", "grupoPeriodoId, dia, horaInicio, horaFin, aulaId",
            "CREATEHORARIOS[1, LUNES, 08:00, 10:00, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "USUARIOS",
            "Crear un usuario", "name, email, password",
            "CREATEUSUARIOS[Juan Perez, juan@email.com, pass123]"));

        COMMANDS.add(new CommandExample("CREATE", "ESTUDIANTES",
            "Crear un estudiante (con isEstudiante=true)", "name, email, password, [codigoEstudiante]",
            "CREATEESTUDIANTES[Juan Perez, juan@email.com, pass123, EST-001]"));

        COMMANDS.add(new CommandExample("CREATE", "MATRICARRERAS",
            "Matricular estudiante en carrera", "usuarioId, ofertaId",
            "CREATEMATRICARRERAS[1, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "MATRICPERIODOS",
            "Matricular en periodo", "matriculaCarreraId, periodoId, planPagoId",
            "CREATEMATRICPERIODOS[1, 1, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "INSCRIPCIONES",
            "Inscribir alumno en grupo", "matriculaPeriodoId, grupoPeriodoId",
            "CREATEINSCRIPCIONES[1, 1]"));

        COMMANDS.add(new CommandExample("CREATE", "PLANESPAGO",
            "Crear plan de pago", "ofertaId, nombre, tipo, montoMatricula, montoCuota, cantidadCuotas",
            "CREATEPLANESPAGO[1, Plan Estandar, MENSUAL, 500, 300, 6]"));

        COMMANDS.add(new CommandExample("CREATE", "CUOTAS",
            "Crear cuota", "matriculaPeriodoId, descripcion, monto, fechaVencimiento",
            "CREATECUOTAS[1, Cuota 1, 300, 2026-03-15]"));

        COMMANDS.add(new CommandExample("CREATE", "PAGOS",
            "Generar QR de pago para una cuota", "cuotaId",
            "CREATEPAGOS[1]"));

        COMMANDS.add(new CommandExample("CREATE", "TAREAS",
            "Crear tarea para grupo", "grupoPeriodoId, titulo, descripcion, fechaVencimiento, puntajeMaximo",
            "CREATETAREAS[1, Examen Final, Examen escrito, 2026-06-15T10:00:00, 100]"));

        COMMANDS.add(new CommandExample("CREATE", "ENTREGAS",
            "Registrar entrega de tarea", "tareaId, usuarioId, rutaArchivo",
            "CREATEENTREGAS[1, 1, tareas/examen1.pdf]"));

        // UPDATE commands
        COMMANDS.add(new CommandExample("UPDATE", "AULAS",
            "Actualizar aula", "id, nombre, codigo, capacidad",
            "UPDATEAULAS[1, Aula 102, A102, 35]"));

        COMMANDS.add(new CommandExample("UPDATE", "USUARIOS",
            "Actualizar usuario", "id, name, email, password",
            "UPDATEUSUARIOS[1, Juan Perez, juan@email.com, newpass]"));

        COMMANDS.add(new CommandExample("UPDATE", "ESTUDIANTES",
            "Actualizar estudiante", "id, name, email, password",
            "UPDATEESTUDIANTES[1, Juan Perez, juan@email.com, newpass]"));

        COMMANDS.add(new CommandExample("UPDATE", "INSCRIPCIONES",
            "Calificar alumno", "id, notaFinal",
            "UPDATEINSCRIPCIONES[1, 85]"));

        COMMANDS.add(new CommandExample("UPDATE", "ENTREGAS",
            "Calificar entrega", "id, nota, [retroalimentacion]",
            "UPDATEENTREGAS[1, 90, Buen trabajo]"));
    }

    public static String obtenerComandosDisponibles() {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n");
        html.append("<meta charset='UTF-8'>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n");
        html.append(".container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }\n");
        html.append("h1 { color: #2c3e50; text-align: center; border-bottom: 3px solid #3498db; padding-bottom: 15px; }\n");
        html.append("h2 { color: #34495e; margin-top: 30px; border-left: 4px solid #3498db; padding-left: 15px; }\n");
        html.append(".entity-list { background: #e8f4f8; padding: 15px; border-radius: 5px; margin: 10px 0; }\n");
        html.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }\n");
        html.append("th { background: #3498db; color: white; padding: 12px; text-align: left; font-weight: bold; }\n");
        html.append("td { padding: 12px; border-bottom: 1px solid #ddd; background: #fff; }\n");
        html.append("tr:hover td { background: #f5f5f5; }\n");
        html.append(".entity-name { font-weight: bold; color: #2c3e50; background: #ecf0f1 !important; padding: 10px; }\n");
        html.append(".example-code { font-family: monospace; color: #c0392b; background: #fef9e7; padding: 5px; border-radius: 3px; }\n");
        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<div class='container'>\n");

        html.append("<h1>SISTEMA DE GESTIÓN ACADÉMICA - COMANDOS</h1>\n");

        // General commands
        html.append("<h2>COMANDOS GENERALES</h2>\n");
        html.append("<table>\n");
        html.append("<thead><tr><th>Comando</th><th>Descripción</th><th>Ejemplo</th></tr></thead>\n");
        html.append("<tbody>\n");
        html.append("<tr><td class='entity-name'>LISTAR&lt;entidad&gt;[*]</td><td>Listar todos los registros</td><td><span class='example-code'>LISTARAULAS[*]</span></td></tr>\n");
        html.append("<tr><td class='entity-name'>CREATE&lt;entidad&gt;[params]</td><td>Crear un nuevo registro</td><td><span class='example-code'>CREATEAULAS[Aula 101, A101, 30]</span></td></tr>\n");
        html.append("<tr><td class='entity-name'>UPDATE&lt;entidad&gt;[id, params]</td><td>Actualizar un registro</td><td><span class='example-code'>UPDATEAULAS[1, Aula 102, A102, 35]</span></td></tr>\n");
        html.append("<tr><td class='entity-name'>DELETE&lt;entidad&gt;[id]</td><td>Eliminar un registro</td><td><span class='example-code'>DELETEAULAS[1]</span></td></tr>\n");
        html.append("<tr><td class='entity-name'>GET&lt;entidad&gt;[id]</td><td>Obtener registro por ID</td><td><span class='example-code'>GETAULAS[1]</span></td></tr>\n");
        html.append("<tr><td class='entity-name'>HELP</td><td>Mostrar esta ayuda</td><td><span class='example-code'>HELP</span></td></tr>\n");
        html.append("</tbody></table>\n");

        // Entities
        html.append("<h2>ENTIDADES DISPONIBLES</h2>\n");
        html.append("<div class='entity-list'>\n");
        html.append("<strong>AULAS</strong>, <strong>OFERTAS</strong>, <strong>PERIODOS</strong>, <strong>MATERIAS</strong>, ");
        html.append("<strong>MALLA</strong>, <strong>PREREQUISITOS</strong>, <strong>GRUPOS</strong>, <strong>GRUPOPERIODOS</strong>, ");
        html.append("<strong>HORARIOS</strong>, <strong>USUARIOS</strong> (o <strong>ESTUDIANTES</strong>), <strong>MATRICARRERAS</strong>, <strong>MATRICPERIODOS</strong>, ");
        html.append("<strong>INSCRIPCIONES</strong> (o MATRICGRUPOS), <strong>PLANESPAGO</strong>, <strong>CUOTAS</strong>, ");
        html.append("<strong>PAGOS</strong>, <strong>TAREAS</strong>, <strong>ENTREGAS</strong>\n");
        html.append("</div>\n");

        // CREATE commands
        html.append("<h2>COMANDOS CREATE</h2>\n");
        html.append("<table>\n");
        html.append("<thead><tr><th>Entidad</th><th>Descripción</th><th>Parámetros</th><th>Ejemplo</th></tr></thead>\n");
        html.append("<tbody>\n");
        for (CommandExample cmd : COMMANDS) {
            if (cmd.operation.equals("CREATE")) {
                html.append("<tr>\n");
                html.append("<td class='entity-name'>").append(cmd.entity).append("</td>\n");
                html.append("<td>").append(cmd.description).append("</td>\n");
                html.append("<td>").append(cmd.parameters).append("</td>\n");
                html.append("<td><span class='example-code'>").append(cmd.example).append("</span></td>\n");
                html.append("</tr>\n");
            }
        }
        html.append("</tbody></table>\n");

        // UPDATE commands
        html.append("<h2>COMANDOS UPDATE</h2>\n");
        html.append("<table>\n");
        html.append("<thead><tr><th>Entidad</th><th>Descripción</th><th>Parámetros</th><th>Ejemplo</th></tr></thead>\n");
        html.append("<tbody>\n");
        for (CommandExample cmd : COMMANDS) {
            if (cmd.operation.equals("UPDATE")) {
                html.append("<tr>\n");
                html.append("<td class='entity-name'>").append(cmd.entity).append("</td>\n");
                html.append("<td>").append(cmd.description).append("</td>\n");
                html.append("<td>").append(cmd.parameters).append("</td>\n");
                html.append("<td><span class='example-code'>").append(cmd.example).append("</span></td>\n");
                html.append("</tr>\n");
            }
        }
        html.append("</tbody></table>\n");

        html.append("</div>\n</body>\n</html>");
        return html.toString();
    }

    public static String buscarComando(String entidad, String operacion) {
        for (CommandExample cmd : COMMANDS) {
            if (cmd.entity.equalsIgnoreCase(entidad) && cmd.operation.equalsIgnoreCase(operacion)) {
                return cmd.description + " -> " + cmd.example;
            }
        }
        return "<p style='color: red;'>Comando no encontrado para " + operacion + " " + entidad + "</p>";
    }
}
