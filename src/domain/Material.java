package domain;

import java.util.ArrayList;

public class Material {

    private int codigo;
    private String descricao;
    private double qntMinima;
    private double estoqueAtual;
    private Nd nd;
    private SubItem subitem;
    private ArrayList<Lote> lote;
    private Unidade unidade;

    public Material() {
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade val) {
        this.unidade = val;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String val) {
        this.descricao = val;
    }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(double val) {
        this.estoqueAtual = val;
    }

    public ArrayList<Lote> getLote() {
        return lote;
    }

    public void setLote(ArrayList<Lote> val) {
        this.lote = val;
    }

    public Nd getNd() {
        return nd;
    }

    public void setNd(Nd val) {
        this.nd = val;
    }

    public double getQntMinima() {
        return qntMinima;
    }

    public void setQntMinima(double val) {
        this.qntMinima = val;
    }

    public SubItem getSubitem() {
        return subitem;
    }

    public void setSubitem(SubItem val) {
        this.subitem = val;
    }
}

