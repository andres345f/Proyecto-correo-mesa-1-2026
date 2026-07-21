package com.example.Adaptadores.Salida;

import com.example.Puertos.IEmailResponderPort;

public class SmtpEmailResponderAdapter implements IEmailResponderPort {

    private final ClienteSMTP clienteSMTP;
    private final ClienteSMTPGoogle clienteSMTPGoogle;
    private final boolean useGoogle;

    public SmtpEmailResponderAdapter(boolean useGoogle) {
        this.clienteSMTP = new ClienteSMTP();
        ClienteSMTPGoogle tempGoogle = null;
        boolean googleSuccess = false;

        if (useGoogle) {
            try {
                tempGoogle = ClienteSMTPGoogle.fromConfig();
                System.out.println("S : SMTP Google habilitado para respuestas.");
                googleSuccess = true;
            } catch (Exception e) {
                System.out.println("S : SMTP Google no configurado, se forzará SMTP por defecto. Detalle: " + e.getMessage());
            }
        }

        this.clienteSMTPGoogle = tempGoogle;
        this.useGoogle = googleSuccess;
    }

    @Override
    public void enviarRespuesta(String emailDestino, String resultado) {
        if (useGoogle && clienteSMTPGoogle != null) {
            System.out.println("Enviando correo vía Google SMTP a " + emailDestino);
            clienteSMTPGoogle.enviarCorreo(emailDestino, "Resultado de la Consulta", resultado);
        } else {
            System.out.println("Enviando correo vía Tecnoweb SMTP a " + emailDestino);
            clienteSMTP.enviarCorreo(emailDestino, "Resultado de la Consulta", resultado);
        }
    }
}