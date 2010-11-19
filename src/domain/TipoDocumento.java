package domain;

public class TipoDocumento {

    private String siglaTipoDoc;
    private String nomeTipoDoc;

    public TipoDocumento() {
    }

    public String getNomeTipoDoc() {
        return nomeTipoDoc;
    }

    public void setNomeTipoDoc(String nomeTipoDoc) {
        this.nomeTipoDoc = nomeTipoDoc;
    }

    public String getSiglaTipoDoc() {
        return siglaTipoDoc;
    }

    public void setSiglaTipoDoc(String siglaTipoDoc) {
        this.siglaTipoDoc = siglaTipoDoc;
    }
}
