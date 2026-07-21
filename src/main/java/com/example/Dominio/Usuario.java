package com.example.Dominio;

public class Usuario {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isPropietario;
    private boolean isDirector;
    private boolean isSecretaria;
    private boolean isProfesor;
    private boolean isEstudiante;
    private String codigoEstudiante;
    private boolean isActivo;

    public Usuario() {}

    public Usuario(int id, String name, String email, String password) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        this.isActivo = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.name = name.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        this.email = email.trim();
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        this.password = password;
    }

    public boolean isPropietario() { return isPropietario; }
    public void setPropietario(boolean propietario) { isPropietario = propietario; }

    public boolean isDirector() { return isDirector; }
    public void setDirector(boolean director) { isDirector = director; }

    public boolean isSecretaria() { return isSecretaria; }
    public void setSecretaria(boolean secretaria) { isSecretaria = secretaria; }

    public boolean isProfesor() { return isProfesor; }
    public void setProfesor(boolean profesor) { isProfesor = profesor; }

    public boolean isEstudiante() { return isEstudiante; }
    public void setEstudiante(boolean estudiante) { isEstudiante = estudiante; }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }

    public boolean isActivo() { return isActivo; }
    public void setActivo(boolean activo) { isActivo = activo; }
}
