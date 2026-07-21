package com.example.Adaptadores.Entrada;

import com.example.Dominio.ComandoDTO;
import com.example.Puertos.IProcesarComandoUseCase;
import java.io.IOException;

public class Pop3EmailDaemon {
    private final EmailCommandParser parser;
    private final IProcesarComandoUseCase useCase;
    private final ClientePOP clientePOP;
    private boolean activo;

    public Pop3EmailDaemon(IProcesarComandoUseCase useCase) {
        this.useCase = useCase;
        this.parser = new EmailCommandParser();
        this.clientePOP = new ClientePOP();
        this.activo = false;
    }

    public void leerCorreosEntrantes() {
        try {
            System.out.println("S : Conectando al servidor POP3 para revisar correos...");
            clientePOP.conectar();
            int totalCorreos = clientePOP.obtenerTotalDeCorreos();
            System.out.println("S : Total de correos encontrados: " + totalCorreos);

            for (int i = 1; i <= totalCorreos; i++) {
                // Obtiene y elimina de forma atómica para no volver a procesarlo
                String rawEmail = clientePOP.obtenerCorreoYEliminar(i);
                if (rawEmail != null) {
                    ComandoDTO cmd = parser.parsearCuerpo(rawEmail);
                    if (cmd != null) {
                        System.out.println("S : Procesando comando: " + cmd.accion + " " + cmd.entidadObjetivo);
                        useCase.ejecutarOperacion(cmd);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("S : Error en comunicación POP3 durante lectura: " + e.getMessage());
        } finally {
            clientePOP.desconectar();
        }
    }

    public void startDaemon() {
        this.activo = true;
        System.out.println("S : Iniciando daemon POP3...");
        
        while (activo) {
            try {
                leerCorreosEntrantes();
            } catch (Exception e) {
                System.err.println("S : Excepción no controlada en el loop del daemon: " + e.getMessage());
            }

            try {
                // Espera 10 segundos antes de la siguiente revisión
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("S : Daemon interrumpido. Apagando...");
                this.activo = false;
            }
        }
    }

    public void stopDaemon() {
        this.activo = false;
        System.out.println("S : Daemon POP3 detenido.");
    }
}