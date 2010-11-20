package util;

import java.util.Date;
import java.util.EventObject;

public class ObterDadosEvent extends EventObject {

    private Date dataI;
    private Date dataF;
    private int codSetor;
    private String nome;

    public String getNome() {
        return nome;
    }

    public ObterDadosEvent(Object source, String nome) {
        super(source);
        this.nome = nome;
    }

    public ObterDadosEvent(Object source, Date dataI, Date dataF) {
        super(source);
        this.dataI = dataI;
        this.dataF = dataF;
        this.codSetor = -1;
    }

    public ObterDadosEvent(Object source, Date dataI, Date dataF, int codSetor) {
        super(source);
        this.dataI = dataI;
        this.dataF = dataF;
        this.codSetor = codSetor;
    }

    public Date getDataF() {
        return dataF;
    }

    public Date getDataI() {
        return dataI;
    }

    public int getCodSetor() {
        return codSetor;
    }
}