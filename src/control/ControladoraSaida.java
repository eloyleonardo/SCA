package control;

import dao.SaidaDao;
import dao.SaidaJDBCDao;
import domain.Material;
import domain.Saida;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControladoraSaida {

    private SaidaDao saidaDao;

    public ControladoraSaida() {
        this.saidaDao = new SaidaJDBCDao();
    }

    public void inserirNovaDSM(int codUsuario, int codSaida, int codSolicitacao) {
        try {
            saidaDao.inserirSaida(codUsuario, codSaida, codSolicitacao);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarSaida(Saida saida, Vector linha) {
        Material material = new Material();
        material.setCodigo(Integer.parseInt(linha.get(0).toString()));
        material.setEstoqueAtual(Double.parseDouble(linha.get(1).toString()));
        material.setQntMinima(Double.parseDouble(linha.get(2).toString()));
        saida.getMaterial().add(material);
        saida.getRequisicao().setCodigo(Integer.parseInt(linha.get(3).toString()));
        saida.setCodigo(Integer.parseInt(linha.get(4).toString()));
        saida.getQuantidadeAtendida().add((Double.parseDouble(linha.get(5).toString())));
    }

    private void atualizarSaidaOutrosMotivos(Saida saida, Vector linha) {
        int i;
        for (i = 0; i < linha.size() - 3; i++) {
            Material material = new Material();
            material.setCodigo(Integer.parseInt(((Vector) linha.get(i)).get(0).toString()));
            saida.getQuantidadeAtendida().add(Double.parseDouble(((Vector) linha.get(i)).get(1).toString()));
            material.setEstoqueAtual(Double.parseDouble(((Vector) linha.get(i)).get(2).toString()));
            material.setQntMinima(Double.parseDouble(((Vector) linha.get(i)).get(3).toString()));
            saida.getMaterial().add(material);
        }
        saida.getTipoSaida().setCodigo(Integer.parseInt(linha.get(i).toString()));
        saida.setCodigo(Integer.parseInt(linha.get(i + 1).toString()));
        saida.setObservacao(linha.get(i + 2).toString());
    }

    public int obterUltimaSaidaInserida() {
        int codSaida = 0;
        try {
            codSaida = saidaDao.obterUltimaDsm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return codSaida;
    }

    public void registrarSaidaMaterial(Vector linha, int numLinhas) {
        Saida saida = new Saida();
        atualizarSaida(saida, linha);
        try {
            saidaDao.registrarSaida(saida, numLinhas);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registrarSaidaMaterialOutrosMotivos(Vector linha) {
        Saida saida = new Saida();
        atualizarSaidaOutrosMotivos(saida, linha);
        try {
            saidaDao.registrarSaidaOutrosMotivos(saida);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
