package com.example.Adaptadores.Salida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClienteSMTP {

    private static final String SERVIDOR = "mail.tecnoweb.org.bo";
    private static final int PUERTO = 25;
    private static final String EMISOR = "grupo03sc@tecnoweb.org.bo";

    private static void enviarComando(OutputStreamWriter salida, BufferedReader entrada, String comando)
            throws IOException {
        salida.write(comando);
        salida.flush();
        String respuesta = leerRespuesta(entrada);

        int codigoRespuesta = Integer.parseInt(respuesta.substring(0, 3));
        if (codigoRespuesta >= 400) {
            throw new IOException(
                    "No se pudo enviar el correo, error durante el comando: " + comando + ".\nError" + respuesta);
        }
    }

    static protected String leerRespuesta(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            lines.append(line).append("\r\n");
            if (line.length() > 3 && line.charAt(3) == ' ')
                break;
        }
        if (line == null) {
            throw new IOException("S : Server unawares closed the connection.");
        }
        return lines.toString();
    }

    public void enviarCorreo(String usuarioReceptor, String subject, String mensaje) {
        try (Socket socket = new Socket(SERVIDOR, PUERTO);
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                OutputStreamWriter salida = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)) {

            System.out.println("S : " + entrada.readLine());

            enviarComando(salida, entrada, "HELO " + SERVIDOR + "\r\n");
            enviarComando(salida, entrada, "MAIL FROM:<" + EMISOR + ">\r\n");
            enviarComando(salida, entrada, "RCPT TO:<" + usuarioReceptor + ">\r\n");
            enviarComando(salida, entrada, "DATA\r\n");

            salida.write("Subject: " + subject + "\r\n");
            salida.write("MIME-Version: 1.0\r\n");

            System.out.println("S : [ClienteSMTP] enviarCorreo a=" + usuarioReceptor + " subject=" + subject);
            System.out.println("S : [ClienteSMTP] mensaje tiene 'data:image/png;base64,' = " + mensaje.contains("data:image/png;base64,"));

            boolean tieneBase64 = mensaje != null && mensaje.contains("data:image/png;base64,");
            if (tieneBase64) {
                int startIndex = mensaje.indexOf("data:image/png;base64,") + "data:image/png;base64,".length();
                System.out.println("S : [ClienteSMTP] startIndex=" + startIndex + " (contexto previo: '...base64," + mensaje.substring(startIndex, Math.min(startIndex + 30, mensaje.length())) + "...')");
                // Buscar el fin del base64: tomar la PRIMERA comilla (simple o doble) que aparezca
                int endSingle = mensaje.indexOf("'", startIndex);
                int endDouble = mensaje.indexOf("\"", startIndex);
                int endIndex;
                if (endSingle >= 0 && endDouble >= 0) {
                    endIndex = Math.min(endSingle, endDouble);
                } else if (endSingle >= 0) {
                    endIndex = endSingle;
                } else {
                    endIndex = endDouble;
                }
                System.out.println("S : [ClienteSMTP] endIndex=" + endIndex + " (endSingle=" + endSingle + ", endDouble=" + endDouble + ")");
                if (endIndex > startIndex) {
                    String base64Image = mensaje.substring(startIndex, endIndex);
                    int base64Len = base64Image.length();
                    System.out.println("S : [ClienteSMTP] base64 extraído, length=" + base64Len + " primeros 50=" + base64Image.substring(0, Math.min(50, base64Len)));
                    String htmlConCid = mensaje.replace("data:image/png;base64," + base64Image, "cid:qrCodeImage");
                    String boundary = "----=_Part_0_" + System.currentTimeMillis();

                    salida.write("Content-Type: multipart/related; boundary=\"" + boundary + "\"\r\n");
                    salida.write("\r\n");
                    salida.write("--" + boundary + "\r\n");
                    salida.write("Content-Type: text/html; charset=utf-8\r\n");
                    salida.write("Content-Transfer-Encoding: 8bit\r\n");
                    salida.write("\r\n");
                    salida.write(htmlConCid.replace("\n", "\r\n") + "\r\n");
                    salida.write("--" + boundary + "\r\n");
                    salida.write("Content-Type: image/png; name=\"qr.png\"\r\n");
                    salida.write("Content-Transfer-Encoding: base64\r\n");
                    salida.write("Content-ID: <qrCodeImage>\r\n");
                    salida.write("Content-Disposition: inline; filename=\"qr.png\"\r\n");
                    salida.write("\r\n");
                    System.out.println("S : [ClienteSMTP] Enviando multipart/related con QR inline");
                    salida.write(base64Image + "\r\n");
                    salida.write("--" + boundary + "--\r\n");
                } else {
                    System.out.println("S : [ClienteSMTP] WARN: no se encontró el fin del base64 (endIndex=" + endIndex + ")");
                    tieneBase64 = false;
                }
            }

            if (!tieneBase64) {
                String mensajeFormateado = mensaje.replace("\n", "\r\n");
                if (mensaje.trim().toLowerCase().startsWith("<!doctype html>")) {
                    salida.write("Content-Type: text/html; charset=utf-8\r\n");
                } else {
                    salida.write("Content-Type: text/plain; charset=utf-8\r\n");
                }
                salida.write("\r\n");
                salida.write(mensajeFormateado + "\r\n");
            }

            salida.write(".\r\n");
            salida.flush();
            leerRespuesta(entrada);

            enviarComando(salida, entrada, "QUIT\r\n");

        } catch (Exception e) {
            System.out.println("S : No se pudo conectar con el servidor, error: " + e.getMessage());
        }
    }
}
