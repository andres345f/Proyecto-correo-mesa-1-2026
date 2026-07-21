package com.example.Adaptadores.Entrada;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientePOP {
    static final String HOST = "mail.tecnoweb.org.bo";
    static final int PORT = 110;
    static final String USER = "grupo03sc";
    static final String PASS = "grup003grup003*";

    private Socket socket;
    private BufferedReader entrada;
    private DataOutputStream salida;

    public void conectar() throws IOException {
        try {
            socket = new Socket(HOST, PORT);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new DataOutputStream(socket.getOutputStream());
            System.out.println("S : " + entrada.readLine());

            enviarComando(salida, entrada, "USER " + USER + "\r\n");
            enviarComando(salida, entrada, "PASS " + PASS + "\r\n");
        } catch (java.net.SocketException e) {
            System.err.println("S : Error al conectar al servidor POP (SocketException): " + e.getMessage());
            throw new IOException("No se pudo conectar al servidor POP (Socket reset/refused)", e);
        } catch (IOException e) {
            System.err.println("S : Error de E/S al conectar al servidor POP: " + e.getMessage());
            throw e;
        }
    }

    private static String enviarComando(DataOutputStream salida, BufferedReader entrada, String comando)
            throws IOException {
        try {
            System.out.print("C : " + comando);
            salida.writeBytes(comando);
            String response;
            if (comando.startsWith("RETR") || comando.startsWith("LIST")) {
                response = leerRespuestaMultilinea(entrada);
                return response;
            }
            return entrada.readLine();
        } catch (java.net.SocketException e) {
            System.err.println("\nS : Error de conexión POP - Socket cerrado o reset por el servidor.");
            throw new IOException("Socket cerrado o reset por el servidor: " + e.getMessage(), e);
        } catch (IOException e) {
            System.err.println("\nS : Error de E/S en la comunicación POP: " + e.getMessage());
            throw e;
        }
    }

    static protected String leerRespuestaMultilinea(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null)
                throw new IOException("S : Server unawares closed the connection.");
            if (line.equals("."))
                break;
            if (line.startsWith("."))
                line = line.substring(1);
            lines.append("\n").append(line);
        }
        return lines.toString();
    }

    public int obtenerTotalDeCorreos() throws IOException {
        String response = enviarComando(salida, entrada, "STAT\r\n");
        response = response.substring(4, response.length());
        int i = 1;
        while (response.charAt(i) != ' ') {
            i++;
        }
        response = response.substring(0, i);
        return Integer.parseInt(response);
    }

    public void desconectar() {
        try {
            enviarComando(salida, entrada, "QUIT\r\n");
            entrada.close();
            salida.close();
            socket.close();
            System.out.println("S : Conexión finalizada POP.");
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión POP: " + e.getMessage());
        }
    }

    public String obtenerCorreo(int posicion) {
        try {
            return enviarComando(salida, entrada, "RETR " + posicion + "\r\n");
        } catch (IOException e) {
            System.out.println("Error al obtener el correo: " + e.getMessage());
            return null;
        }
    }

    public String obtenerCorreoYEliminar(int posicion) {
        try {
            String correo = enviarComando(salida, entrada, "RETR " + posicion + "\r\n");
            enviarComando(salida, entrada, "DELE " + posicion + "\r\n");
            return correo;
        } catch (IOException e) {
            System.out.println("Error al obtener el correo y marcar para eliminación: " + e.getMessage());
            return null;
        }
    }
}
