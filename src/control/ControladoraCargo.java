package control;

import dao.CargoDao;
import dao.CargoJDBCDao;
import domain.Cargo;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import util.PesquisarVector;

public class ControladoraCargo implements ControladoraClasse {

    private Vector<Cargo> cargosAtivos;
    private Vector<Cargo> cargosInativos;
    private CargoDao cargoDao;

    public ControladoraCargo() {
        this.cargoDao = new CargoJDBCDao();
        cargosAtivos = new Vector<Cargo>();
        cargosInativos = new Vector<Cargo>();
    }

    private Vector criarLinhaCargo(Cargo cargo) {
        Vector linha = new Vector();
        linha.addElement(cargo.getCodigo());
        linha.addElement(cargo.getNome());
        if (cargo.isChefia()) {
            linha.addElement("Sim");
        } else {
            linha.addElement("Não");
        }
        return linha;
    }

    private Vector<Cargo> obterCargos(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            cargosAtivos = cargoDao.obterCargos(nome, status);
            return cargosAtivos;
        } else {
            cargosInativos = cargoDao.obterCargos(nome, status);
            return cargosInativos;
        }
    }

    public Vector obterLinhas(String nome, String status) {
        Vector<Cargo> c;
        if (status.equals("Ativo")) {
            if (cargosAtivos.isEmpty()) {
                c = new Vector<Cargo>();
                try {

                    c = obterCargos(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = cargosAtivos;
            }
        } else {
            if (cargosInativos.isEmpty()) {
                c = new Vector<Cargo>();
                try {
                    c = obterCargos(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = cargosInativos;
            }
        }
        Vector linhas = new Vector();
        // Montando as linhas
        for (int i = 0; i < c.size(); i++) {
            Cargo cargo = c.get(i);
            linhas.addElement(this.criarLinhaCargo(cargo));
        }
        return linhas;

    }

    private void atualizarCargo(Cargo cargo, Vector linha) {
        cargo.setCodigo(Integer.parseInt(linha.get(0).toString()));
        cargo.setNome(linha.get(1).toString());
        if (linha.get(2).toString().equals("true")) {
            cargo.setChefia(true);
        } else {
            cargo.setChefia(false);
        }

    }

    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
    }

    public boolean inserir(Vector linha) {
        Cargo cargo = new Cargo();
        this.atualizarCargo(cargo, linha);
        try {
            cargo.setCodigo(cargoDao.inserirCargo(cargo));
            cargosAtivos.add(cargo);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar(Vector linha, String status) {
        Cargo cargo = new Cargo();
        this.atualizarCargo(cargo, linha);
        cargo.setStatus(status.substring(0, 1).toLowerCase());
        try {

            if (status.equals("Ativo")) {
                cargoDao.alterarCargo(cargo);
                for (int i = 0; i < cargosAtivos.size(); i++) {
                    if (cargosAtivos.get(i).getCodigo() == cargo.getCodigo()) {
                        cargosAtivos.get(i).setNome(cargo.getNome());
                        cargosAtivos.get(i).setChefia(cargo.isChefia());
                        return true;
                    }
                }
            } else {
                cargoDao.alterarCargo(cargo);
                for (int i = 0; i < cargosInativos.size(); i++) {
                    if (cargosInativos.get(i).getCodigo() == cargo.getCodigo()) {
                        cargosInativos.get(i).setNome(cargo.getNome());
                        cargosInativos.get(i).setChefia(cargo.isChefia());
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            return false;
        }
    }

    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao) {
        Cargo cargo = new Cargo();
        Usuario r = new Usuario();
        atualizarCargo(cargo, linha);
        atualizarUsuario(r, responsavel);
        try {
            cargoDao.alterarStatusCargo(cargo.getCodigo(), motivo, r, acao);
            if (acao.equals("Desativar")) {
                cargo.setStatus("i");
                excluirCargoVector(cargosAtivos, cargo);
                if (cargosInativos.isEmpty()) {
                    this.obterCargos("", "Inativo");
                } else {
                    cargosInativos.add(cargo);
                }
                return true;
            } else {
                cargo.setStatus("a");
                cargosAtivos.add(cargo);
                excluirCargoVector(cargosInativos, cargo);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            return false;
        }
    }

    private void excluirCargoVector(Vector<Cargo> v, Cargo c) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).equals(c)) {
                v.remove(i);
                break;
            }
        }
    }

    public Vector pesquisar(String nome, String status) {
        Vector resultadoCargos, resultadoLinhas = new Vector();
        PesquisarVector pesq;
        if (status.equals("Ativo")) {
            pesq = new PesquisarVector(cargosAtivos);
        } else {
            pesq = new PesquisarVector(cargosInativos);
        }
        resultadoCargos = pesq.pesquisar(nome);
        for (int i = 0; i < resultadoCargos.size(); i++) {
            resultadoLinhas.addElement(criarLinhaCargo((Cargo) resultadoCargos.get(i)));
        }
        return resultadoLinhas;
    }
}
