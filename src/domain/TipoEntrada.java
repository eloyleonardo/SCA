package domain;

public class TipoEntrada {

    private int codigo;
    private String nome;
    private TipoDocumento tipoDoc;

    public TipoEntrada() {
    }

    public int getCodigo() {
        return codigo;
    }

    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String val) {
        this.nome = val;
    }
}

