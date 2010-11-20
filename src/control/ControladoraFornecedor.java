package control;

import dao.CidadeDao;
import dao.CidadeJDBCDao;
import dao.FornecedorDao;
import dao.FornecedorJDBCDao;
import dao.TipoEnderecoDao;
import dao.TipoEnderecoJDBCDao;
import dao.UfDao;
import dao.UfJDBCDao;
import domain.Cidade;
import domain.Fornecedor;
import domain.TipoEndereco;
import domain.Uf;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import util.PesquisarVector;

public class ControladoraFornecedor implements ControladoraClasse {

    private Vector<Fornecedor> fornecedoresAtivos;
    private Vector<Fornecedor> fornecedoresInativos;
    private UfDao ufDao;
    private TipoEnderecoDao teDao;
    private Vector<Uf> ufs;
    private Vector<TipoEndereco> te;
    private FornecedorDao fornecedorDao;
    private CidadeDao controladoraCidade;
    private Vector cidades;

    /**
     * Construtor
     */
    public ControladoraFornecedor(String servidor) {
        this.teDao = new TipoEnderecoJDBCDao(servidor);
        this.ufDao = new UfJDBCDao(servidor);
        this.controladoraCidade = new CidadeJDBCDao(servidor);
        this.fornecedoresAtivos = new Vector<Fornecedor>();
        this.fornecedoresInativos = new Vector<Fornecedor>();
        this.fornecedorDao = new FornecedorJDBCDao(servidor);
        this.cidades = new Vector();
        this.obterUfs();
        this.obterTipoEndereco();
    }

    /**
     *Esse método retorna um vector de strings com todas as ufs locais
     * @return um Vector de uf com as Ufs obtidas
     */
    public Vector obterLinasUfs() {
        Vector estados = new Vector(this.ufs.size());
        for (int i = 0; i < this.ufs.size(); i++) {
            Vector v = new Vector(2);
            v.add(this.ufs.get(i).getSigla());
            v.add(this.ufs.get(i).getDescricao());
            estados.add(v);
        }
        return estados;
    }

    /**
     * Esse método pesquisa no Vector local por uma Uf especifica, retornando-a
     * @param uf - String de Uf a ser pesquisada
     * @return um Objeto de Uf
     */
    private Uf criarUf(String uf) {
        for (int i = 0; i < ufs.size(); i++) {
            if ((ufs.get(i).getDescricao().equals(uf)) || (ufs.get(i).getSigla().equals(uf))) {
                return this.ufs.get(i);
            }
        }
        return null;
    }

    /**
     * Este método retorna um vector apartir de um Vector de cidades
     * @param cidades - Cidades a serem convertidades em Vector
     * @return Vector de String com as cidades recebidas como parametro
     */
    private Vector criarLinhaCidade(Vector<Cidade> cidades) {
        Vector cid = new Vector();
        String uf = null;
        for (int i = 0; i < cidades.size(); i++) {
            Vector v = new Vector();
            uf = cidades.get(i).getUf().getDescricao();
            v.addElement(cidades.get(i).getCodigo());
            v.addElement(cidades.get(i).getNome());
            v.addElement(cidades.get(i).getUf().getSigla());
            v.addElement(cidades.get(i).getUf().getDescricao());
            cid.addElement(v);
        }
        if (!(uf == null)) {
            Vector v = new Vector(2);
            v.add(uf);
            v.add(cid);
            this.cidades.add(v);
        }
        return cid;
    }

    /**
     * Este método retorna um Vector de String apartir de uma Uf
     * @param uf - Uf da qual deseja-se as cidades
     * @return Vector com as cidades de uma Uf
     */
    public Vector obterCidades(String uf) {
        for (int i = 0; i < this.cidades.size(); i++) {
            Vector cidade = (Vector) this.cidades.get(i);
            if (cidade.get(0).equals(uf)) {
                return (Vector) cidade.get(1);
            }
        }
        try {
            return criarLinhaCidade(controladoraCidade.obterCidades(criarUf(uf)));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Converte o Vector de tipo de Enderecos para um Vector de String
     * @return Vector de Sting com os Tipos de Endereco
     */
    public Vector obterLinhasTe() {
        Vector tes = new Vector(this.te.size());
        for (int i = 0; i < this.te.size(); i++) {
            Vector v = new Vector(2);
            v.add(this.te.get(i).getCodigo());
            v.add(this.te.get(i).getNome());
            tes.add(v);
        }
        return tes;
    }

    /**
     * Obtem as Ufs da Dao e as guarda em um Vector
     */
    private void obterUfs() {
        try {
            this.ufs = this.ufDao.obterUfs("", "Ativo");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtem os Tipos de Endereco da Dao e as guarda em um Vector
     */
    private void obterTipoEndereco() {
        try {
            this.te = this.teDao.obterTiposEndereco();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtem da Dao os Fornecedores de acordo com o Status
     * @param nome Nome do fornecedor pode ser usado em implementações de Busca (atualmente em memória)
     * @param status Status desejado
     * @return Retorna o Vector de Fornecedor
     * @throws java.sql.SQLException
     */
    private Vector<Fornecedor> obterForncedor(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            fornecedoresAtivos = fornecedorDao.obterFornecedores(nome, status);
            return fornecedoresAtivos;
        } else {
            fornecedoresInativos = fornecedorDao.obterFornecedores(nome, status);
            return fornecedoresInativos;
        }
    }

    /**
     * Converte um fornecedor em Vector de String
     * @param fornecedor - Fornecedor a ser convertido
     * @return - Retorna o Vector de String contendo o fornecedor recebido como parametro
     */
    private Vector criarLinhaFornecedor(Fornecedor fornecedor) {
        Vector linha = new Vector();
        linha.addElement(fornecedor.getId());
        linha.addElement(fornecedor.getNome());
        linha.addElement(fornecedor.getNomeFantasia());
        linha.addElement(fornecedor.getCep());
        linha.addElement(fornecedor.getTipoEndereco().toString());
        linha.addElement(fornecedor.getEndereco());
        linha.addElement(fornecedor.getCidade().getUf().getSigla());
        linha.addElement(fornecedor.getCidade().getNome());
        linha.addElement(fornecedor.getBairro());
        linha.addElement(fornecedor.getComplemento());
        linha.addElement(fornecedor.getTelefone1());
        linha.addElement(fornecedor.getTelefone2());
        linha.addElement(fornecedor.getEmail());
        linha.addElement(fornecedor.getFax());
        return linha;
    }

    /**
     * Recebe uma String e retorna um Tipo de Endereco
     * @param te - Nome do Tipo de Endereco Desejado
     * @return - Retorna o Tipo de Endereco de acordo com o nome recebido
     */
    private TipoEndereco toTipoEndereco(String te) {
        for (int i = 0; i < this.te.size(); i++) {
            if (this.te.get(i).getNome().equals(te)) {
                return this.te.get(i);
            }
        }
        return null;
    }

    /**
     * Converte uma linha para Cidade
     * @param linha linha a ser convertida
     * @return Cidade apos a conversao
     */
    private Cidade vectorToCidade(Vector linha) {
        Cidade cidade = new Cidade();
        cidade.setUf(criarUf(linha.get(1).toString()));
        obterCidades(cidade.getUf().getSigla());
        for (int i = 0; i < this.cidades.size(); i++) {
            Vector v = (Vector) this.cidades.get(i);
            if (v.get(0).equals(cidade.getUf().getDescricao())) {
                Vector cid = (Vector) v.get(1);
                for (int j = 0; j < cid.size(); j++) {
                    Vector c = (Vector) cid.get(j);
                    if (c.get(1).equals(linha.get(0).toString())) {
                        cidade.setCodigo(Integer.valueOf(c.get(0).toString()));
                        cidade.setNome(c.get(1).toString());
                        return cidade;
                    }
                }
            }
        }
        return cidade;
    }

    /**
     * Recebe uma linha e um fornecedor e atualiza o fornecedor de acordo com a linha
     * @param f Fornecedor a ser atualizado
     * @param linha linha a ser usada como base para a conversao
     */
    private void atualizarFornecedor(Fornecedor f, Vector linha) {
        f.setId(linha.get(0).toString());
        f.setNome(linha.get(1).toString());
        f.setNomeFantasia(linha.get(2).toString());
        f.setCep(linha.get(3).toString());
        f.setTipoEndereco(toTipoEndereco(linha.get(4).toString()));
        f.setEndereco(linha.get(5).toString());
        Vector cidade = new Vector(2);
        cidade.add(linha.get(7));
        cidade.add(linha.get(6));
        f.setCidade(vectorToCidade(cidade));
        f.setBairro(linha.get(8).toString());
        f.setComplemento(linha.get(9).toString());
        f.setTelefone1(linha.get(10).toString());
        f.setTelefone2(linha.get(11).toString());
        f.setEmail(linha.get(12).toString());
        f.setFax(linha.get(13).toString());
    }

    /**
     * Atualiza um usuario apatir de um Vector
     * @param usuario usuraio a ser atualizado
     * @param linha linha a ser usada como base para atualização
     */
    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
    }

    /**
     * Exclui o Fornecedor do Vector
     * @param v Vector a remover o fornecedor
     * @param f fornecedor a ser removido
     */
    private void excluirFornecedorVector(Vector<Fornecedor> v, Fornecedor f) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).equals(f)) {
                v.remove(i);
                break;
            }
        }
    }

    /**
     * Atelra o Status de um fornecedor
     * @param motivo Motivo pelo qual o fornecedor foi removido
     * @param responsavel Responsavel pela alteração de Status
     * @param linha Linha, indicando o fornecedor
     * @param acao Acao realizada (Desativar,Ativar)
     * @return True caso sucesso ou false no caso de erro
     */
    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao) {
        Fornecedor fornecedor = new Fornecedor();
        Usuario r = new Usuario();
        atualizarFornecedor(fornecedor, linha);
        atualizarUsuario(r, responsavel);
        try {
            fornecedorDao.alterarStatusFornecedor(fornecedor.getId(), motivo, r, acao);
            if (acao.equals("Desativar")) {
                fornecedor.setStatus("i");
                excluirFornecedorVector(fornecedoresAtivos, fornecedor);
                if (fornecedoresInativos.isEmpty()) {
                    this.obterForncedor("", "Inativo");
                } else {
                    fornecedoresInativos.add(fornecedor);
                }
                return true;
            } else {
                fornecedor.setStatus("a");
                excluirFornecedorVector(fornecedoresInativos, fornecedor);
                fornecedoresAtivos.add(fornecedor);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean verificarId(String id) {
        for (int i = 0; i < fornecedoresAtivos.size(); i++) {
            if (fornecedoresAtivos.get(i).getId().equals(id)) {
                JOptionPane.showMessageDialog(null, "Fornecedor ja cadastrado !", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        for (int i = 0; i < fornecedoresInativos.size(); i++) {
            if (fornecedoresInativos.get(i).getId().equals(id)) {
                JOptionPane.showMessageDialog(null, "Fornecedor ja cadastrado !", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Insere um fornecedor
     * @param linha Linha a ser Inserida
     * @return True em caso de Sucesso e false em caso de Erro
     */
    public boolean inserir(Vector linha) {
        Fornecedor fornecedor = new Fornecedor();
        this.atualizarFornecedor(fornecedor, linha);
        try {
            if (this.verificarId(linha.get(0).toString())) {
                fornecedorDao.inserirFornecedor(fornecedor);
                fornecedoresAtivos.add(fornecedor);
                return true;
            }
            return false;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Alterar um fornecedor
     * @param linha Linha com o fornecedor a ser alterado
     * @param status Status do fornecedor a ser alterado
     * @return true em caso de sucessom false em caso de erro
     */
    public boolean alterar(Vector linha, String status) {
        Fornecedor fornecedor = new Fornecedor();
        this.atualizarFornecedor(fornecedor, linha);
        fornecedor.setStatus(status.substring(0, 1).toLowerCase());
        try {
            if (status.equals("Ativo")) {
                fornecedorDao.alterarFornecedor(fornecedor);
                for (int i = 0; i < fornecedoresAtivos.size(); i++) {
                    if (this.fornecedoresAtivos.get(i).getId().equals(fornecedor.getId())) {
                        this.fornecedoresAtivos.get(i).setNome(fornecedor.getNome());
                        this.fornecedoresAtivos.get(i).setNomeFantasia(fornecedor.getNomeFantasia());
                        this.fornecedoresAtivos.get(i).setCep(fornecedor.getCep());
                        this.fornecedoresAtivos.get(i).setTipoEndereco(fornecedor.getTipoEndereco());
                        this.fornecedoresAtivos.get(i).setEndereco(fornecedor.getEndereco());
                        this.fornecedoresAtivos.get(i).setCidade(fornecedor.getCidade());
                        this.fornecedoresAtivos.get(i).setBairro(fornecedor.getBairro());
                        this.fornecedoresAtivos.get(i).setComplemento(fornecedor.getComplemento());
                        this.fornecedoresAtivos.get(i).setTelefone1(fornecedor.getTelefone1());
                        this.fornecedoresAtivos.get(i).setTelefone2(fornecedor.getTelefone2());
                        this.fornecedoresAtivos.get(i).setEmail(fornecedor.getEmail());
                        this.fornecedoresAtivos.get(i).setFax(fornecedor.getFax());
                        return true;
                    }
                }
            } else {
                fornecedorDao.alterarFornecedor(fornecedor);
                for (int i = 0; i < fornecedoresInativos.size(); i++) {
                    if (this.fornecedoresInativos.get(i).getId().equals(fornecedor.getId())) {
                        this.fornecedoresInativos.get(i).setNome(fornecedor.getNome());
                        this.fornecedoresInativos.get(i).setNomeFantasia(fornecedor.getNomeFantasia());
                        this.fornecedoresInativos.get(i).setCep(fornecedor.getCep());
                        this.fornecedoresInativos.get(i).setTipoEndereco(fornecedor.getTipoEndereco());
                        this.fornecedoresInativos.get(i).setEndereco(fornecedor.getEndereco());
                        this.fornecedoresInativos.get(i).setCidade(fornecedor.getCidade());
                        this.fornecedoresInativos.get(i).setBairro(fornecedor.getBairro());
                        this.fornecedoresInativos.get(i).setComplemento(fornecedor.getComplemento());
                        this.fornecedoresInativos.get(i).setTelefone1(fornecedor.getTelefone1());
                        this.fornecedoresInativos.get(i).setTelefone2(fornecedor.getTelefone2());
                        this.fornecedoresInativos.get(i).setEmail(fornecedor.getEmail());
                        this.fornecedoresInativos.get(i).setFax(fornecedor.getFax());
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

    /**
     * Pesquisa um fornecedor
     * @param Nome Nome a ser pesquisado
     * @param Status Status do fornecedor a ser pesquisado
     * @return Retorna um Vector com o Resultado
     */
    public Vector pesquisar(String nome, String status) {
        Vector resultadoFornecedor, resultadoLinhas = new Vector();
        PesquisarVector pesq;
        if (status.equals("Ativo")) {
            pesq = new PesquisarVector(fornecedoresAtivos);
        } else {
            pesq = new PesquisarVector(fornecedoresInativos);
        }
        resultadoFornecedor = pesq.pesquisar(nome);
        for (int i = 0; i < resultadoFornecedor.size(); i++) {
            resultadoLinhas.addElement(criarLinhaFornecedor((Fornecedor) resultadoFornecedor.get(i)));
        }
        return resultadoLinhas;
    }

    /**
     * Retorna um Vector com todos os fornecedores por Status
     * @param nome usado para a busca, pode ser implementado (atualmente em meória)
     * @param status Status dos Fornecedores a ser obtido
     * @return Vector com os fornecedores
     */
    public Vector obterLinhas(String nome, String status) {
        Vector<Fornecedor> f;
        if (status.equals("Ativo")) {
            if (this.fornecedoresAtivos.isEmpty()) {
                f = new Vector<Fornecedor>();
                try {

                    f = this.obterForncedor(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                f = this.fornecedoresAtivos;
            }
        } else {
            if (this.fornecedoresInativos.isEmpty()) {
                f = new Vector<Fornecedor>();
                try {
                    f = this.obterForncedor(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                f = this.fornecedoresInativos;
            }
        }
        Vector linhas = new Vector();
        for (int i = 0; i < f.size(); i++) {
            Fornecedor fornecedor = f.get(i);
            linhas.addElement(this.criarLinhaFornecedor(fornecedor));
        }
        return linhas;
    }
}
