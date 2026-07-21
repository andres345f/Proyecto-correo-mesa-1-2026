package com.example;

import com.example.Adaptadores.Entrada.Pop3EmailDaemon;
import com.example.Adaptadores.Salida.*;
import com.example.Aplicacion.*;
import com.example.Puertos.*;
import org.bebidas.infraestructure.servicioemail.PagoFacilGateway;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  INICIANDO SISTEMA DE GESTIÓN ACADÉMICA (EMAIL) ");
        System.out.println("=================================================");

        // 0. Gateway de pagos
        PagoFacilGateway pagoFacilGateway = new PagoFacilGateway();

        // 1. Instanciar Adaptadores de Salida (Driven Adapters)
        IAulaRepository aulaRepo = new PostgresAulaRepository();
        IOfertaAcademicaRepository ofertaRepo = new PostgresOfertaAcademicaRepository();
        IPeriodoAcademicoRepository periodoRepo = new PostgresPeriodoAcademicoRepository();
        IMateriaRepository materiaRepo = new PostgresMateriaRepository();
        IMallaCurricularRepository mallaRepo = new PostgresMallaCurricularRepository();
        IMateriaPrerequisitoRepository prerequisitoRepo = new PostgresMateriaPrerequisitoRepository();
        IGrupoRepository grupoRepo = new PostgresGrupoRepository();
        IGrupoPeriodoRepository grupoPeriodoRepo = new PostgresGrupoPeriodoRepository();
        IHorarioRepository horarioRepo = new PostgresHorarioRepository();
        IUsuarioRepository usuarioRepo = new PostgresUsuarioRepository();
        IMatriculaCarreraRepository matriculaCarreraRepo = new PostgresMatriculaCarreraRepository();
        IMatriculaPeriodoRepository matriculaPeriodoRepo = new PostgresMatriculaPeriodoRepository();
        IMatriculaGrupoRepository matriculaGrupoRepo = new PostgresMatriculaGrupoRepository();
        IPlanPagoRepository planPagoRepo = new PostgresPlanPagoRepository();
        ICuotaRepository cuotaRepo = new PostgresCuotaRepository();
        IPagoRepository pagoRepo = new PostgresPagoRepository();
        ITareaRepository tareaRepo = new PostgresTareaRepository();
        IEntregaRepository entregaRepo = new PostgresEntregaRepository();

        System.out.println("Seleccione el servidor SMTP a utilizar para enviar respuestas:");
        System.out.println("1. Tecnoweb SMTP (Por defecto)");
        System.out.println("2. Google SMTP");
        System.out.print("Opción [1-2]: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String opcion = scanner.nextLine().trim();
        boolean usarGoogleSMTP = "2".equals(opcion);
        System.out.println("S : Iniciando con SMTP: " + (usarGoogleSMTP ? "Google" : "Tecnoweb"));

        IEmailResponderPort notificador = new SmtpEmailResponderAdapter(usarGoogleSMTP);

        // 2. Instanciar Casos de Uso/Servicios de Aplicación (Core)
        AulaService aulaService = new AulaService(aulaRepo);
        OfertaAcademicaService ofertaService = new OfertaAcademicaService(ofertaRepo);
        PeriodoAcademicoService periodoService = new PeriodoAcademicoService(periodoRepo);
        MateriaService materiaService = new MateriaService(materiaRepo);
        MallaCurricularService mallaService = new MallaCurricularService(mallaRepo);
        MateriaPrerequisitoService prerequisitoService = new MateriaPrerequisitoService(prerequisitoRepo);
        GrupoService grupoService = new GrupoService(grupoRepo);
        GrupoPeriodoService grupoPeriodoService = new GrupoPeriodoService(grupoPeriodoRepo);
        HorarioService horarioService = new HorarioService(horarioRepo);
        UsuarioService usuarioService = new UsuarioService(usuarioRepo);
        MatriculaCarreraService matriculaCarreraService = new MatriculaCarreraService(matriculaCarreraRepo);
        MatriculaPeriodoService matriculaPeriodoService = new MatriculaPeriodoService(matriculaPeriodoRepo, planPagoRepo, cuotaRepo);
        MatriculaGrupoService matriculaGrupoService = new MatriculaGrupoService(matriculaGrupoRepo);
        PlanPagoService planPagoService = new PlanPagoService(planPagoRepo);
        CuotaService cuotaService = new CuotaService(cuotaRepo);
        PagoService pagoService = new PagoService(pagoRepo, cuotaRepo, matriculaPeriodoRepo, matriculaCarreraRepo, usuarioRepo, pagoFacilGateway);
        TareaService tareaService = new TareaService(tareaRepo);
        EntregaService entregaService = new EntregaService(entregaRepo);

        IProcesarComandoUseCase useCase = new ComandoOrchestratorService(
            aulaService,
            ofertaService,
            periodoService,
            materiaService,
            mallaService,
            prerequisitoService,
            grupoService,
            grupoPeriodoService,
            horarioService,
            usuarioService,
            matriculaCarreraService,
            matriculaPeriodoService,
            matriculaGrupoService,
            planPagoService,
            cuotaService,
            pagoService,
            tareaService,
            entregaService,
            notificador
        );

        // 3. Instanciar e Iniciar Adaptadores de Entrada (Driving Adapters)
        Pop3EmailDaemon daemon = new Pop3EmailDaemon(useCase);

        // Registrar Hook para apagado limpio
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nS : Recibida señal de apagado. Deteniendo daemon...");
            daemon.stopDaemon();
        }));

        // Iniciar el daemon (bucle infinito con espera de 10s)
        daemon.startDaemon();
    }
}
