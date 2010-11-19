package domain;

import domain.Lote;
import java.util.ArrayList;
import java.util.Date;

public class Entrada {

    private int codigo;
    private String numNota;
    private Date dataNota;
    private String numNE;
    private Date data;
    private double valorNota;
    private TipoEntrada tipoEntrada;
    private ArrayList<Material> Material;
    private Fornecedor Fornecedor;
    private Usuario reponsavel;
    private Documento documento;

    public Entrada() {
    }

    public Fornecedor getFornecedor() {
        return Fornecedor;
    }

    public void setFornecedor(Fornecedor val) {
        this.Fornecedor = val;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public ArrayList<Material> getMaterial() {
        return Material;
    }

    public void setMaterial(ArrayList<Material> val) {
        this.Material = val;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date val) {
        this.data = val;
    }

    public Date getDataNota() {
        return dataNota;
    }

    public void setDataNota(Date val) {
        this.dataNota = val;
    }

    public String getNumNE() {
        return numNE;
    }

    public void setNumNE(String val) {
        this.numNE = val;
    }

    public String getNumNota() {
        return numNota;
    }

    public void setNumNota(String val) {
        this.numNota = val;
    }

    public Usuario getReponsavel() {
        return reponsavel;
    }

    public void setReponsavel(Usuario val) {
        this.reponsavel = val;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada val) {
        this.tipoEntrada = val;
    }

    public double getValorNota() {
        return valorNota;
    }

    public void setValorNota(double val) {
        this.valorNota = val;
    }
}

