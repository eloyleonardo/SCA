package control;

import dao.MaterialDao;
import dao.MaterialJDBCDao;
import domain.Material;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControladoraMaterial {

    private Vector<Material> materiaisAtivos;
    private Vector<Material> materiaisInativos;
    private Vector<Material> material;
    private MaterialDao materialDao;
    private int marcador;

    public int getMarcador() {
        return marcador;
    }

    public void setMarcador(int marcador) {
        this.marcador = marcador;
    }

    public ControladoraMaterial() {
        this.materialDao = new MaterialJDBCDao();
    }

    public void alterarStatus(String motivo, Usuario responsavel, String acao, int cod) {
        Usuario r = responsavel;
        try {
            materialDao.alterarStatusMaterial(cod, motivo, acao, r);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Vector criarLinhaMaterial(Material material) {
        Vector linha = new Vector();
        linha.addElement(material.getCodigo());
        linha.addElement(material.getDescricao());
        linha.addElement(material.getQntMinima());
        linha.addElement(material.getNd().getCodigo());
        linha.addElement(material.getSubitem().getCodigo());
        linha.addElement(material.getUnidade().getNome());
        linha.addElement(material.getEstoqueAtual());
        return linha;
    }

    private Vector criarLinhaMaterialCodigo(Material material) {
        Vector linha = new Vector();
        linha.addElement(material.getCodigo());
        linha.addElement(material.getDescricao());
        linha.addElement(material.getEstoqueAtual());
        return linha;
    }

    private Vector criarLinhaMaterialBaixoEstoque(Material material) {
        Vector linha = new Vector();
        linha.addElement(material.getCodigo());
        linha.addElement(material.getDescricao());
        linha.addElement(material.getQntMinima());
        linha.addElement(material.getEstoqueAtual());
        return linha;
    }

    private Vector<Material> obterMateriaisAtivos(String nome) throws SQLException {
        materiaisAtivos = materialDao.obterMateriaisAtivos(nome);
        return materiaisAtivos;
    }

    private Vector<Material> obterMateriaisInativos(String nome) throws SQLException {
        materiaisInativos = materialDao.obterMateriaisInativos(nome);
        return materiaisInativos;
    }

    public Vector obterMaterialAtivoCodigo(String codigo) throws SQLException {
        this.material = this.materialDao.obterMaterialAtivoCodigo(codigo);
        Vector materialAtual = criarLinhaMaterialCodigo((Material) this.material.get(0));
        return materialAtual;
    }

    public Vector obterLinhasMateriaisAtivos(String nome) {
        Vector<Material> m = new Vector<Material>();
        try {
            m = obterMateriaisAtivos(nome);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        Vector linhas = new Vector();
        for (int i = 0; i < m.size(); i++) {
            Material mat = m.get(i);
            linhas.addElement(this.criarLinhaMaterial(mat));
        }
        return linhas;

    }

    private Material atualizarMaterial(Vector linha) {
        Material materialAtual = new Material();
        materialAtual.setCodigo(Integer.parseInt(linha.get(0).toString()));
        materialAtual.setDescricao(linha.get(1).toString());
        materialAtual.setQntMinima(Double.parseDouble(linha.get(2).toString()));
        materialAtual.setEstoqueAtual(Double.parseDouble(linha.get(3).toString()));
        return materialAtual;
    }

    public Vector obterMaterialAbaixoEstoque() throws SQLException {
        Vector materiaisFinal = new Vector();
        Vector<Material> materiais = this.materialDao.obterMateriaisAbaixoEstoque();
        for (int i = 0; i < materiais.size(); i++) {
            materiaisFinal.add(criarLinhaMaterialBaixoEstoque((Material) materiais.get(i)));
        }
        return materiaisFinal;
    }

    public void darCienciaMaterial(Vector material) throws SQLException {
        this.materialDao.darCiencia(atualizarMaterial(material));
    }

    public Vector obterLinhasMateriaisInativos(String nome) {
        Vector<Material> m = new Vector<Material>();
        try {
            m = obterMateriaisInativos(nome);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        Vector linhas = new Vector();
        for (int i = 0; i < m.size(); i++) {
            Material mat = m.get(i);
            linhas.addElement(this.criarLinhaMaterial(mat));
        }
        return linhas;
    }

    private void atualizarMaterial(Material material, Vector linha) {
        material.setDescricao(linha.get(0).toString());
        material.getUnidade().setCodigo(Integer.parseInt(linha.get(1).toString()));
        material.setQntMinima(Double.parseDouble(linha.get(2).toString()));
        material.getNd().setCodigo(Integer.parseInt(linha.get(3).toString()));
        material.getSubitem().setCodigo(Integer.parseInt(linha.get(4).toString()));
    }

    public void inserirNovoMaterial(Vector linha) {
        Material mat = new Material();
        this.atualizarMaterial(mat, linha);
        try {
            materialDao.inserirMaterial(mat);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void alterarMaterial(Vector linha, int Id) {
        Material mat = new Material();
        this.atualizarMaterial(mat, linha);
        try {
            materialDao.alterarMaterial(mat, Id);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Vector obterValores(int codigo) {
        for (int i = 0; i < materiaisAtivos.size(); i++) {
            if (materiaisAtivos.get(i).getCodigo() == codigo) {
                Vector valores = new Vector();
                return valores;
            }
        }
        return new Vector();
    }
}
