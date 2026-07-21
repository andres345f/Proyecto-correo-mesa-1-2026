package com.example.Dominio;

public class MateriaPrerequisito {
    private int id;
    private int mallaCurricularId;
    private int prerequisitoMallaId;

    public MateriaPrerequisito() {}

    public MateriaPrerequisito(int id, int mallaCurricularId, int prerequisitoMallaId) {
        this.id = id;
        this.mallaCurricularId = mallaCurricularId;
        this.prerequisitoMallaId = prerequisitoMallaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMallaCurricularId() { return mallaCurricularId; }
    public void setMallaCurricularId(int mallaCurricularId) { this.mallaCurricularId = mallaCurricularId; }

    public int getPrerequisitoMallaId() { return prerequisitoMallaId; }
    public void setPrerequisitoMallaId(int prerequisitoMallaId) { this.prerequisitoMallaId = prerequisitoMallaId; }
}
