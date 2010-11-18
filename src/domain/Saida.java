package domain;

import java.util.ArrayList;
import java.util.Date;

public class Saida {

    private int codigo;
    private ArrayList<Double> quantidadeAtendida;
    private Date dataSaida;
    private Solicitacao Requisicao;
    private Usuario responsavel;
    private ArrayList<Material> Material;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    private TipoSaida TipoSaida;
    private String observacao;

    public Saida() {
        this.Material = new ArrayList<Material>();
        this.Requisicao = new Solicitacao();
        this.TipoSaida = new TipoSaida();
        this.quantidadeAtendida = new ArrayList<Double>();
    }

    public ArrayList<Material> getMaterial() {
        return Material;
    }

    public void setMaterial(ArrayList<Material> val) {
        this.Material = val;
    }

    public Solicitacao getRequisicao() {
        return Requisicao;
    }

    public void setRequisicao(Solicitacao val) {
        this.Requisicao = val;
    }

    public TipoSaida getTipoSaida() {
        return TipoSaida;
    }

    public void setTipoSaida(TipoSaida val) {
        this.TipoSaida = val;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date val) {
        this.dataSaida = val;
    }

    public ArrayList<Double> getQuantidadeAtendida() {
        return quantidadeAtendida;
    }

    public void setQuantidadeAtendida(ArrayList<Double> quantidadeAtendida) {
        this.quantidadeAtendida = quantidadeAtendida;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario val) {
        this.responsavel = val;
    }
}

