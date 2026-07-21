package com.example.Aplicacion;

import com.example.Dominio.*;
import com.example.Puertos.IEmailResponderPort;
import com.example.Puertos.IProcesarComandoUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ComandoOrchestratorService implements IProcesarComandoUseCase {

    private final AulaService aulaService;
    private final OfertaAcademicaService ofertaService;
    private final PeriodoAcademicoService periodoService;
    private final MateriaService materiaService;
    private final MallaCurricularService mallaService;
    private final MateriaPrerequisitoService prerequisitoService;
    private final GrupoService grupoService;
    private final GrupoPeriodoService grupoPeriodoService;
    private final HorarioService horarioService;
    private final UsuarioService usuarioService;
    private final MatriculaCarreraService matriculaCarreraService;
    private final MatriculaPeriodoService matriculaPeriodoService;
    private final MatriculaGrupoService matriculaGrupoService;
    private final PlanPagoService planPagoService;
    private final CuotaService cuotaService;
    private final PagoService pagoService;
    private final TareaService tareaService;
    private final EntregaService entregaService;
    private final IEmailResponderPort notificador;

    public ComandoOrchestratorService(
            AulaService aulaService,
            OfertaAcademicaService ofertaService,
            PeriodoAcademicoService periodoService,
            MateriaService materiaService,
            MallaCurricularService mallaService,
            MateriaPrerequisitoService prerequisitoService,
            GrupoService grupoService,
            GrupoPeriodoService grupoPeriodoService,
            HorarioService horarioService,
            UsuarioService usuarioService,
            MatriculaCarreraService matriculaCarreraService,
            MatriculaPeriodoService matriculaPeriodoService,
            MatriculaGrupoService matriculaGrupoService,
            PlanPagoService planPagoService,
            CuotaService cuotaService,
            PagoService pagoService,
            TareaService tareaService,
            EntregaService entregaService,
            IEmailResponderPort notificador) {
        this.aulaService = aulaService;
        this.ofertaService = ofertaService;
        this.periodoService = periodoService;
        this.materiaService = materiaService;
        this.mallaService = mallaService;
        this.prerequisitoService = prerequisitoService;
        this.grupoService = grupoService;
        this.grupoPeriodoService = grupoPeriodoService;
        this.horarioService = horarioService;
        this.usuarioService = usuarioService;
        this.matriculaCarreraService = matriculaCarreraService;
        this.matriculaPeriodoService = matriculaPeriodoService;
        this.matriculaGrupoService = matriculaGrupoService;
        this.planPagoService = planPagoService;
        this.cuotaService = cuotaService;
        this.pagoService = pagoService;
        this.tareaService = tareaService;
        this.entregaService = entregaService;
        this.notificador = notificador;
    }

    @Override
    public void ejecutarOperacion(ComandoDTO cmd) {
        if (cmd == null) return;

        System.out.println("S : Ejecutando operación: " + cmd.accion + " " + cmd.entidadObjetivo);
        String resultado;

        try {
            switch (cmd.accion.toUpperCase()) {
                case "HELP":
                    resultado = CommandHelpHTML.obtenerComandosDisponibles();
                    break;
                case "LISTAR":
                    resultado = ejecutarListar(cmd.entidadObjetivo);
                    break;
                case "GET":
                    resultado = ejecutarGet(cmd.entidadObjetivo, cmd.parametros.get("raw"));
                    break;
                case "DELETE":
                    resultado = ejecutarDelete(cmd.entidadObjetivo, cmd.parametros.get("raw"));
                    break;
                case "CREATE":
                    resultado = ejecutarCreate(cmd.entidadObjetivo, cmd.parametros.get("raw"));
                    break;
                case "UPDATE":
                    resultado = ejecutarUpdate(cmd.entidadObjetivo, cmd.parametros.get("raw"));
                    break;
                default:
                    resultado = "<p style='color: #e74c3c; font-weight: bold;'>Comando no reconocido o formato inválido.</p>";
                    break;
            }
        } catch (Exception e) {
            resultado = "<p style='color: #e74c3c; font-weight: bold;'>Error interno al procesar: " + e.getMessage() + "</p>";
        }

        String htmlFinal = wrapInHTML(resultado);
        notificador.enviarRespuesta(cmd.emailRemitente, htmlFinal);
    }

    private String ejecutarListar(String entidad) throws Exception {
        String entUpper = entidad.toUpperCase();
        switch (entUpper) {
            case "AULAS":
                return formatListToHTML(aulaService.listarAulas(),
                    new String[]{"ID", "Nombre", "Código", "Capacidad"},
                    a -> new Object[]{a.getId(), a.getNombre(), a.getCodigo(), a.getCapacidad()});
            case "OFERTAS":
                return formatListToHTML(ofertaService.listarOfertas(),
                    new String[]{"ID", "Nombre", "Código", "Descripción"},
                    o -> new Object[]{o.getId(), o.getNombre(), o.getCodigo(), o.getDescripcion()});
            case "PERIODOS":
                return formatListToHTML(periodoService.listarPeriodos(),
                    new String[]{"ID", "Oferta ID", "Nombre", "Tipo", "Inicio", "Fin",
                        "Inicio Insc.", "Fin Insc.", "Inicio Cierre", "Fin Cierre",
                        "Inicio Retiro", "Fin Retiro", "Máx. Materias", "Estado"},
                    p -> new Object[]{p.getId(), p.getOfertaAcademicaId(), p.getNombre(), p.getTipo(),
                        p.getFechaInicio(), p.getFechaFin(),
                        p.getFechaInicioInscripcion(), p.getFechaFinInscripcion(),
                        p.getFechaInicioCierre(), p.getFechaFinCierre(),
                        p.getFechaInicioRetiro(), p.getFechaFinRetiro(),
                        p.getNumeroMaximoMaterias(), p.getEstado()});
            case "MATERIAS":
                return formatListToHTML(materiaService.listarMaterias(),
                    new String[]{"ID", "Código", "Nombre", "Descripción"},
                    m -> new Object[]{m.getId(), m.getCodigo(), m.getNombre(), m.getDescripcion()});
            case "MALLA":
                return formatListToHTML(mallaService.listarMallas(),
                    new String[]{"ID", "Oferta ID", "Materia ID", "Semestre"},
                    m -> new Object[]{m.getId(), m.getOfertaAcademicaId(), m.getMateriaId(), m.getSemestreOrden()});
            case "PREREQUISITOS":
                return formatListToHTML(prerequisitoService.listarPrerequisitos(),
                    new String[]{"ID", "Malla ID", "Prerrequisito Malla ID"},
                    p -> new Object[]{p.getId(), p.getMallaCurricularId(), p.getPrerequisitoMallaId()});
            case "GRUPOS":
                return formatListToHTML(grupoService.listarGrupos(),
                    new String[]{"ID", "Código", "Materia ID"},
                    g -> new Object[]{g.getId(), g.getCodigo(), g.getMateriaId()});
            case "GRUPOPERIODOS":
                return formatListToHTML(grupoPeriodoService.listarGruposPeriodo(),
                    new String[]{"ID", "Grupo ID", "Periodo ID", "Docente ID", "Cupo Máximo"},
                    gp -> new Object[]{gp.getId(), gp.getGrupoId(), gp.getPeriodoAcademicoId(), gp.getDocenteId(), gp.getCupoMaximo()});
            case "HORARIOS":
                return formatListToHTML(horarioService.listarHorarios(),
                    new String[]{"ID", "GrupoPeriodo ID", "Día", "Inicio", "Fin", "Aula ID"},
                    h -> new Object[]{h.getId(), h.getGrupoPeriodoId(), h.getDia(), h.getHoraInicio(), h.getHoraFin(), h.getAulaId()});
            case "USUARIOS":
            case "ESTUDIANTES":
                return formatListToHTML(usuarioService.listarUsuarios(),
                    new String[]{"ID", "Nombre", "Email", "Profesor", "Estudiante", "Activo"},
                    u -> new Object[]{u.getId(), u.getName(), u.getEmail(), u.isProfesor(), u.isEstudiante(), u.isActivo()});
            case "MATRICARRERAS":
                return formatListToHTML(matriculaCarreraService.listarMatriculasCarrera(),
                    new String[]{"ID", "Usuario ID", "Oferta ID", "Fecha", "Estado"},
                    m -> new Object[]{m.getId(), m.getUsuarioId(), m.getOfertaAcademicaId(), m.getFechaMatricula(), m.getEstado()});
            case "MATRICPERIODOS":
                return formatListToHTML(matriculaPeriodoService.listarMatriculasPeriodo(),
                    new String[]{"ID", "MatCarrera ID", "Periodo ID", "PlanPago ID", "Fecha", "Estado"},
                    m -> new Object[]{m.getId(), m.getMatriculaCarreraId(), m.getPeriodoAcademicoId(), m.getPlanPagoId(), m.getFechaMatricula(), m.getEstado()});
            case "MATRICGRUPOS":
            case "INSCRIPCIONES":
                return formatListToHTML(matriculaGrupoService.listarMatriculasGrupo(),
                    new String[]{"ID", "MatPeriodo ID", "GrupoPeriodo ID", "Nota Final", "Estado"},
                    m -> new Object[]{m.getId(), m.getMatriculaPeriodoId(), m.getGrupoPeriodoId(), m.getNotaFinal(), m.getEstado()});
            case "PLANESPAGO":
                return formatListToHTML(planPagoService.listarPlanesPago(),
                    new String[]{"ID", "Oferta ID", "Nombre", "Tipo", "Matrícula", "Cuota", "Cant. Cuotas"},
                    p -> new Object[]{p.getId(), p.getOfertaAcademicaId(), p.getNombre(), p.getTipo(), p.getMontoMatricula(), p.getMontoCuota(), p.getCantidadCuotas()});
            case "CUOTAS":
                return formatListToHTML(cuotaService.listarCuotas(),
                    new String[]{"ID", "MatPeriodo ID", "Descripción", "Monto", "Vencimiento", "Estado"},
                    c -> new Object[]{c.getId(), c.getMatriculaPeriodoId(), c.getDescripcion(), c.getMonto(), c.getFechaVencimiento(), c.getEstado()});
            case "PAGOS":
                return formatListToHTML(pagoService.listarPagos(),
                    new String[]{"ID", "Cuota ID", "Monto", "Método", "Transacción", "Fecha", "Estado"},
                    p -> new Object[]{p.getId(), p.getCuotaId(), p.getMontoPagado(), p.getMetodoPago(), p.getTransaccionId(), p.getFechaPago(), p.getEstado()});
            case "TAREAS":
                return formatListToHTML(tareaService.listarTareas(),
                    new String[]{"ID", "GrupoPeriodo ID", "Título", "Vencimiento", "Puntaje Máx."},
                    t -> new Object[]{t.getId(), t.getGrupoPeriodoId(), t.getTitulo(), t.getFechaVencimiento(), t.getPuntajeMaximo()});
            case "ENTREGAS":
                return formatListToHTML(entregaService.listarEntregas(),
                    new String[]{"ID", "Tarea ID", "Usuario ID", "Archivo", "Fecha", "Nota"},
                    e -> new Object[]{e.getId(), e.getTareaId(), e.getUsuarioId(), e.getRutaArchivo(), e.getFechaEntrega(), e.getNota()});
            default:
                return "<p style='color: #e74c3c;'>Entidad no soportada para listar: " + entidad + "</p>";
        }
    }

    private String ejecutarGet(String entidad, String rawParam) throws Exception {
        if (rawParam == null || rawParam.trim().isEmpty()) {
            return "<p style='color: #e74c3c;'>Error: ID requerido.</p>";
        }
        int id = Integer.parseInt(rawParam.trim());
        String entUpper = entidad.toUpperCase();

        switch (entUpper) {
            case "AULAS": {
                List<Aula> list = new ArrayList<>();
                Aula a = aulaService.obtenerAula(id);
                if (a != null) list.add(a);
                return formatListToHTML(list, new String[]{"ID", "Nombre", "Código", "Capacidad"}, x -> new Object[]{x.getId(), x.getNombre(), x.getCodigo(), x.getCapacidad()});
            }
            case "OFERTAS": {
                List<OfertaAcademica> list = new ArrayList<>();
                OfertaAcademica o = ofertaService.obtenerOferta(id);
                if (o != null) list.add(o);
                return formatListToHTML(list, new String[]{"ID", "Nombre", "Código", "Descripción"}, x -> new Object[]{x.getId(), x.getNombre(), x.getCodigo(), x.getDescripcion()});
            }
            case "PERIODOS": {
                List<PeriodoAcademico> list = new ArrayList<>();
                PeriodoAcademico p = periodoService.obtenerPeriodo(id);
                if (p != null) list.add(p);
                return formatListToHTML(list, new String[]{"ID", "Oferta ID", "Nombre", "Tipo", "Inicio", "Fin",
                    "Inicio Insc.", "Fin Insc.", "Inicio Cierre", "Fin Cierre",
                    "Inicio Retiro", "Fin Retiro", "Máx. Materias", "Estado"},
                    x -> new Object[]{x.getId(), x.getOfertaAcademicaId(), x.getNombre(), x.getTipo(),
                        x.getFechaInicio(), x.getFechaFin(),
                        x.getFechaInicioInscripcion(), x.getFechaFinInscripcion(),
                        x.getFechaInicioCierre(), x.getFechaFinCierre(),
                        x.getFechaInicioRetiro(), x.getFechaFinRetiro(),
                        x.getNumeroMaximoMaterias(), x.getEstado()});
            }
            case "MATERIAS": {
                List<Materia> list = new ArrayList<>();
                Materia m = materiaService.obtenerMateria(id);
                if (m != null) list.add(m);
                return formatListToHTML(list, new String[]{"ID", "Código", "Nombre", "Descripción"}, x -> new Object[]{x.getId(), x.getCodigo(), x.getNombre(), x.getDescripcion()});
            }
            case "MALLA": {
                List<MallaCurricular> list = new ArrayList<>();
                MallaCurricular m = mallaService.obtenerMalla(id);
                if (m != null) list.add(m);
                return formatListToHTML(list, new String[]{"ID", "Oferta ID", "Materia ID", "Semestre"}, x -> new Object[]{x.getId(), x.getOfertaAcademicaId(), x.getMateriaId(), x.getSemestreOrden()});
            }
            case "PREREQUISITOS": {
                List<MateriaPrerequisito> list = new ArrayList<>();
                MateriaPrerequisito p = prerequisitoService.obtenerPrerequisito(id);
                if (p != null) list.add(p);
                return formatListToHTML(list, new String[]{"ID", "Malla ID", "Prerreq Malla ID"}, x -> new Object[]{x.getId(), x.getMallaCurricularId(), x.getPrerequisitoMallaId()});
            }
            case "GRUPOS": {
                List<Grupo> list = new ArrayList<>();
                Grupo g = grupoService.obtenerGrupo(id);
                if (g != null) list.add(g);
                return formatListToHTML(list, new String[]{"ID", "Código", "Materia ID"}, x -> new Object[]{x.getId(), x.getCodigo(), x.getMateriaId()});
            }
            case "GRUPOPERIODOS": {
                List<GrupoPeriodo> list = new ArrayList<>();
                GrupoPeriodo gp = grupoPeriodoService.obtenerGrupoPeriodo(id);
                if (gp != null) list.add(gp);
                return formatListToHTML(list, new String[]{"ID", "Grupo ID", "Periodo ID", "Docente ID", "Cupo"}, x -> new Object[]{x.getId(), x.getGrupoId(), x.getPeriodoAcademicoId(), x.getDocenteId(), x.getCupoMaximo()});
            }
            case "HORARIOS": {
                List<Horario> list = new ArrayList<>();
                Horario h = horarioService.obtenerHorario(id);
                if (h != null) list.add(h);
                return formatListToHTML(list, new String[]{"ID", "GrupoPeriodo ID", "Día", "Inicio", "Fin", "Aula ID"}, x -> new Object[]{x.getId(), x.getGrupoPeriodoId(), x.getDia(), x.getHoraInicio(), x.getHoraFin(), x.getAulaId()});
            }
            case "USUARIOS":
            case "ESTUDIANTES": {
                List<Usuario> list = new ArrayList<>();
                Usuario u = usuarioService.obtenerUsuario(id);
                if (u != null) list.add(u);
                return formatListToHTML(list, new String[]{"ID", "Nombre", "Email", "Profesor", "Estudiante", "Activo"}, x -> new Object[]{x.getId(), x.getName(), x.getEmail(), x.isProfesor(), x.isEstudiante(), x.isActivo()});
            }
            case "MATRICARRERAS": {
                List<MatriculaCarrera> list = new ArrayList<>();
                MatriculaCarrera m = matriculaCarreraService.obtenerMatriculaCarrera(id);
                if (m != null) list.add(m);
                return formatListToHTML(list, new String[]{"ID", "Usuario ID", "Oferta ID", "Fecha", "Estado"}, x -> new Object[]{x.getId(), x.getUsuarioId(), x.getOfertaAcademicaId(), x.getFechaMatricula(), x.getEstado()});
            }
            case "MATRICPERIODOS": {
                List<MatriculaPeriodo> list = new ArrayList<>();
                MatriculaPeriodo m = matriculaPeriodoService.obtenerMatriculaPeriodo(id);
                if (m != null) list.add(m);
                return formatListToHTML(list, new String[]{"ID", "MatCarrera ID", "Periodo ID", "PlanPago ID", "Fecha", "Estado"}, x -> new Object[]{x.getId(), x.getMatriculaCarreraId(), x.getPeriodoAcademicoId(), x.getPlanPagoId(), x.getFechaMatricula(), x.getEstado()});
            }
            case "MATRICGRUPOS":
            case "INSCRIPCIONES": {
                List<MatriculaGrupo> list = new ArrayList<>();
                MatriculaGrupo m = matriculaGrupoService.obtenerMatriculaGrupo(id);
                if (m != null) list.add(m);
                return formatListToHTML(list, new String[]{"ID", "MatPeriodo ID", "GrupoPeriodo ID", "Nota", "Estado"}, x -> new Object[]{x.getId(), x.getMatriculaPeriodoId(), x.getGrupoPeriodoId(), x.getNotaFinal(), x.getEstado()});
            }
            case "PLANESPAGO": {
                List<PlanPago> list = new ArrayList<>();
                PlanPago p = planPagoService.obtenerPlanPago(id);
                if (p != null) list.add(p);
                return formatListToHTML(list, new String[]{"ID", "Oferta ID", "Nombre", "Tipo", "Matrícula", "Cuota", "Cuotas"}, x -> new Object[]{x.getId(), x.getOfertaAcademicaId(), x.getNombre(), x.getTipo(), x.getMontoMatricula(), x.getMontoCuota(), x.getCantidadCuotas()});
            }
            case "CUOTAS": {
                List<Cuota> list = new ArrayList<>();
                Cuota c = cuotaService.obtenerCuota(id);
                if (c != null) list.add(c);
                return formatListToHTML(list, new String[]{"ID", "MatPeriodo ID", "Descripción", "Monto", "Vencimiento", "Estado"}, x -> new Object[]{x.getId(), x.getMatriculaPeriodoId(), x.getDescripcion(), x.getMonto(), x.getFechaVencimiento(), x.getEstado()});
            }
            case "PAGOS":
                return pagoService.consultarEstadoPago(id);
            case "TAREAS": {
                List<Tarea> list = new ArrayList<>();
                Tarea t = tareaService.obtenerTarea(id);
                if (t != null) list.add(t);
                return formatListToHTML(list, new String[]{"ID", "GrupoPeriodo ID", "Título", "Vencimiento", "Puntaje"}, x -> new Object[]{x.getId(), x.getGrupoPeriodoId(), x.getTitulo(), x.getFechaVencimiento(), x.getPuntajeMaximo()});
            }
            case "ENTREGAS": {
                List<Entrega> list = new ArrayList<>();
                Entrega e = entregaService.obtenerEntrega(id);
                if (e != null) list.add(e);
                return formatListToHTML(list, new String[]{"ID", "Tarea ID", "Usuario ID", "Archivo", "Fecha", "Nota"}, x -> new Object[]{x.getId(), x.getTareaId(), x.getUsuarioId(), x.getRutaArchivo(), x.getFechaEntrega(), x.getNota()});
            }
            default:
                return "<p style='color: #e74c3c;'>Entidad no soportada para GET: " + entidad + "</p>";
        }
    }

    private String ejecutarDelete(String entidad, String rawParam) {
        if (rawParam == null || rawParam.trim().isEmpty()) {
            return "<p style='color: #e74c3c;'>Error: ID requerido para eliminar.</p>";
        }
        int id = Integer.parseInt(rawParam.trim());
        String entUpper = entidad.toUpperCase();

        switch (entUpper) {
            case "AULAS": return aulaService.eliminarAula(id);
            case "OFERTAS": return ofertaService.eliminarOferta(id);
            case "PERIODOS": return periodoService.eliminarPeriodo(id);
            case "MATERIAS": return materiaService.eliminarMateria(id);
            case "MALLA": return mallaService.eliminarMalla(id);
            case "PREREQUISITOS": return prerequisitoService.eliminarPrerequisito(id);
            case "GRUPOS": return grupoService.eliminarGrupo(id);
            case "GRUPOPERIODOS": return grupoPeriodoService.eliminarGrupoPeriodo(id);
            case "HORARIOS": return horarioService.eliminarHorario(id);
            case "USUARIOS":
            case "ESTUDIANTES": return usuarioService.eliminarUsuario(id);
            case "MATRICARRERAS": return matriculaCarreraService.eliminarMatriculaCarrera(id);
            case "MATRICPERIODOS": return matriculaPeriodoService.eliminarMatriculaPeriodo(id);
            case "MATRICGRUPOS":
            case "INSCRIPCIONES": return matriculaGrupoService.eliminarInscripcion(id);
            case "PLANESPAGO": return planPagoService.eliminarPlanPago(id);
            case "CUOTAS": return cuotaService.eliminarCuota(id);
            case "PAGOS": return pagoService.eliminarPago(id);
            case "TAREAS": return tareaService.eliminarTarea(id);
            case "ENTREGAS": return entregaService.eliminarEntrega(id);
            default:
                return "<p style='color: #e74c3c;'>Entidad no soportada para eliminar: " + entidad + "</p>";
        }
    }

    private String ejecutarCreate(String entidad, String rawParam) {
        if (rawParam == null || rawParam.trim().isEmpty()) {
            return "<p style='color: #e74c3c;'>Error: Parámetros vacíos para creación.</p>";
        }
        String[] p = rawParam.split(",");
        String entUpper = entidad.toUpperCase();

        switch (entUpper) {
            case "AULAS":
                return aulaService.crearAula(p[0].trim(), p[1].trim(), Integer.parseInt(p[2].trim()));
            case "OFERTAS":
                return ofertaService.crearOferta(p[0].trim(), p[1].trim(), p.length > 2 ? p[2].trim() : null);
            case "PERIODOS":
                return periodoService.crearPeriodo(
                    Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
                    java.time.LocalDate.parse(p[3].trim()), java.time.LocalDate.parse(p[4].trim()));
            case "MATERIAS":
                return materiaService.crearMateria(p[0].trim(), p[1].trim(), p.length > 2 ? p[2].trim() : null);
            case "MALLA":
                return mallaService.crearMalla(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
            case "PREREQUISITOS":
                return prerequisitoService.crearPrerequisito(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()));
            case "GRUPOS":
                return grupoService.crearGrupo(p[0].trim(), Integer.parseInt(p[1].trim()));
            case "GRUPOPERIODOS":
                Integer docenteId = p.length > 3 && !p[3].trim().isEmpty() ? Integer.parseInt(p[3].trim()) : null;
                return grupoPeriodoService.crearGrupoPeriodo(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()),
                    docenteId, p.length > 2 ? Integer.parseInt(p[2].trim()) : 35);
            case "HORARIOS":
                return horarioService.crearHorario(
                    Integer.parseInt(p[0].trim()), p[1].trim(),
                    java.time.LocalTime.parse(p[2].trim()), java.time.LocalTime.parse(p[3].trim()),
                    Integer.parseInt(p[4].trim()));
            case "USUARIOS":
                return usuarioService.crearUsuario(p[0].trim(), p[1].trim(), p[2].trim());
            case "ESTUDIANTES":
                return usuarioService.crearEstudiante(p[0].trim(), p[1].trim(), p[2].trim(),
                    p.length > 3 ? p[3].trim() : "EST-" + System.currentTimeMillis());
            case "MATRICARRERAS":
                return matriculaCarreraService.crearMatriculaCarrera(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()));
            case "MATRICPERIODOS":
                return matriculaPeriodoService.crearMatriculaPeriodo(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
            case "MATRICGRUPOS":
            case "INSCRIPCIONES":
                return matriculaGrupoService.inscribirAlumno(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()));
            case "PLANESPAGO":
                return planPagoService.crearPlanPago(
                    Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
                    new java.math.BigDecimal(p[3].trim()), new java.math.BigDecimal(p[4].trim()),
                    Integer.parseInt(p[5].trim()));
            case "CUOTAS":
                return cuotaService.crearCuota(
                    Integer.parseInt(p[0].trim()), p[1].trim(),
                    new java.math.BigDecimal(p[2].trim()), java.time.LocalDate.parse(p[3].trim()));
            case "PAGOS":
                return pagoService.crearPago(Integer.parseInt(p[0].trim()));
            case "TAREAS":
                return tareaService.crearTarea(
                    Integer.parseInt(p[0].trim()), p[1].trim(), p.length > 2 ? p[2].trim() : null,
                    java.time.LocalDateTime.parse(p[3].trim()), new java.math.BigDecimal(p[4].trim()));
            case "ENTREGAS":
                return entregaService.crearEntrega(
                    Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), p[2].trim());
            default:
                return "<p style='color: #e74c3c;'>Entidad no soportada para creación: " + entidad + "</p>";
        }
    }

    private String ejecutarUpdate(String entidad, String rawParam) {
        if (rawParam == null || rawParam.trim().isEmpty()) {
            return "<p style='color: #e74c3c;'>Error: Parámetros vacíos para actualización.</p>";
        }
        String[] p = rawParam.split(",");
        int id = Integer.parseInt(p[0].trim());
        String entUpper = entidad.toUpperCase();

        switch (entUpper) {
            case "AULAS":
                return aulaService.actualizarAula(id, p[1].trim(), p[2].trim(), Integer.parseInt(p[3].trim()));
            case "OFERTAS":
                return ofertaService.actualizarOferta(id, p[1].trim(), p[2].trim(), p.length > 3 ? p[3].trim() : null);
            case "PERIODOS":
                return periodoService.actualizarPeriodo(id,
                    Integer.parseInt(p[1].trim()), p[2].trim(), p[3].trim(),
                    java.time.LocalDate.parse(p[4].trim()), java.time.LocalDate.parse(p[5].trim()),
                    p.length > 6 ? p[6].trim() : "inscripcion");
            case "MATERIAS":
                return materiaService.actualizarMateria(id, p[1].trim(), p[2].trim(), p.length > 3 ? p[3].trim() : null);
            case "MALLA":
                return mallaService.actualizarMalla(id,
                    Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()));
            case "GRUPOS":
                return grupoService.actualizarGrupo(id, p[1].trim(), Integer.parseInt(p[2].trim()));
            case "GRUPOPERIODOS":
                Integer docenteId = p.length > 4 && !p[4].trim().isEmpty() ? Integer.parseInt(p[4].trim()) : null;
                return grupoPeriodoService.actualizarGrupoPeriodo(id,
                    Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()),
                    docenteId, Integer.parseInt(p[3].trim()));
            case "HORARIOS":
                return horarioService.actualizarHorario(id,
                    Integer.parseInt(p[1].trim()), p[2].trim(),
                    java.time.LocalTime.parse(p[3].trim()), java.time.LocalTime.parse(p[4].trim()),
                    Integer.parseInt(p[5].trim()));
            case "USUARIOS":
            case "ESTUDIANTES":
                return usuarioService.actualizarUsuario(id, p[1].trim(), p[2].trim(), p[3].trim());
            case "MATRICARRERAS":
                return matriculaCarreraService.actualizarMatriculaCarrera(id,
                    Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()), p[3].trim());
            case "MATRICPERIODOS":
                return matriculaPeriodoService.actualizarMatriculaPeriodo(id,
                    Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()),
                    Integer.parseInt(p[3].trim()), p[4].trim());
            case "MATRICGRUPOS":
            case "INSCRIPCIONES":
                if (p.length > 2) {
                    return matriculaGrupoService.calificarAlumno(id, new java.math.BigDecimal(p[1].trim()));
                }
                return matriculaGrupoService.inscribirAlumno(
                    Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
            case "PLANESPAGO":
                return planPagoService.actualizarPlanPago(id,
                    Integer.parseInt(p[1].trim()), p[2].trim(), p[3].trim(),
                    new java.math.BigDecimal(p[4].trim()), new java.math.BigDecimal(p[5].trim()),
                    Integer.parseInt(p[6].trim()));
            case "CUOTAS":
                return cuotaService.actualizarCuota(id,
                    Integer.parseInt(p[1].trim()), p[2].trim(),
                    new java.math.BigDecimal(p[3].trim()), java.time.LocalDate.parse(p[4].trim()),
                    p.length > 5 ? p[5].trim() : "pendiente");
            case "PAGOS":
                return pagoService.actualizarPago(id,
                    Integer.parseInt(p[1].trim()), new java.math.BigDecimal(p[2].trim()),
                    p[3].trim(), p[4].trim(), p[5].trim());
            case "TAREAS":
                return tareaService.actualizarTarea(id,
                    Integer.parseInt(p[1].trim()), p[2].trim(), p.length > 3 ? p[3].trim() : null,
                    java.time.LocalDateTime.parse(p[4].trim()), new java.math.BigDecimal(p[5].trim()));
            case "ENTREGAS":
                return entregaService.calificarEntrega(id,
                    new java.math.BigDecimal(p[1].trim()), p.length > 2 ? p[2].trim() : null);
            default:
                return "<p style='color: #e74c3c;'>Entidad no soportada para actualización: " + entidad + "</p>";
        }
    }

    private <T> String formatListToHTML(List<T> list, String[] headers, Function<T, Object[]> rowMapper) {
        if (list == null || list.isEmpty()) {
            return "<p style='color: #7f8c8d;'>No hay registros disponibles.</p>";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' style='border-collapse: collapse; width: 100%; margin: 10px 0;'>");
        sb.append("<thead><tr style='background-color: #3498db; color: white;'>");
        for (String h : headers) {
            sb.append("<th style='padding: 8px; text-align: left;'>").append(h).append("</th>");
        }
        sb.append("</tr></thead><tbody>");
        for (T item : list) {
            sb.append("<tr>");
            Object[] values = rowMapper.apply(item);
            for (Object val : values) {
                sb.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
                  .append(val != null ? val.toString().trim() : "NULL")
                  .append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</tbody></table>");
        return sb.toString();
    }

    private String wrapInHTML(String content) {
        if (content != null && content.trim().toLowerCase().startsWith("<!doctype html>")) {
            return content;
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n");
        html.append("<meta charset='UTF-8'>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n");
        html.append(".container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }\n");
        html.append("h1, h2, h3 { color: #2c3e50; }\n");
        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<div class='container'>\n");

        if (content != null && (content.contains("<table") || content.contains("<p"))) {
            html.append(content);
        } else {
            html.append("<pre style='background: #ecf0f1; padding: 15px; border-radius: 5px; font-family: monospace;'>")
                    .append(content)
                    .append("</pre>");
        }

        html.append("</div>\n</body>\n</html>");
        return html.toString();
    }
}
