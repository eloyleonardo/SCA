package domain;

public class TipoEndereco {

    private int codigo;
    private String nome;

    public TipoEndereco() {
    }

    public int getCodigo() {
        return codigo;
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

    @Override
    public String toString() {
        return nome;
    }
}

