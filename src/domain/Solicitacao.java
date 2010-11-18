package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Solicitacao {

    private int codigo;
    private ArrayList quantidade;
    private Date dataRequisicao;
    private Date dataAprovacao;
    private Vector quantidadeAprovada;
    private String estado;
    private Usuario responsavel;
    private ArrayList<Material> materiais;

    public Solicitacao() {
        this.materiais = new ArrayList<Material>();
        this.quantidade = new ArrayList();
        this.responsavel = new Usuario();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public Date getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(Date val) {
        this.dataAprovacao = val;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date val) {
        this.dataRequisicao = val;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String val) {
        this.estado = val;
    }

    public ArrayList<Material> getMateriais() {
        return materiais;
    }

    public void setMaterial(ArrayList<Material> val) {
        this.materiais = val;
    }

    public void setMaterial(Material val) {
        this.materiais.add(val);
    }

    public double getQuantidade(int indice) {
        return Double.valueOf(quantidade.get(indice).toString());
    }

    public ArrayList getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(double val, int indice) {
        this.quantidade.set(indice, val);
    }

    public void setQuantidade(ArrayList val) {
        this.quantidade = val;
    }

    public double getQuantidadeAprovada(int indice) {
        return Double.valueOf(quantidadeAprovada.get(indice).toString());
    }

    public void setQuantidadeAprovada(double val, int indice) {
        this.quantidadeAprovada.set(indice, val);
    }

    public void setQuantidadeAprovada(Vector quantidadeAprovada) {
        this.quantidadeAprovada = quantidadeAprovada;
    }

    public Vector getQuantidadeAprovada() {
        return quantidadeAprovada;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario val) {
        this.responsavel = val;
    }
}

