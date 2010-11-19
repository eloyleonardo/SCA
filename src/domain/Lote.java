package domain;

import java.util.Date;

public class Lote {

    private int codigo;
    private Material material;
    private Entrada entrada;
    private double quantidadeTotal;
    private double valorUnidade;
    private double estoque;
    private Date validade;

    public Lote() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material codigoMaterial) {
        this.material = codigoMaterial;
    }

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    public double getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(double quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public double getValorUnidade() {
        return valorUnidade;
    }

    public void setValorUnidade(double valorUnidade) {
        this.valorUnidade = valorUnidade;
    }
}
