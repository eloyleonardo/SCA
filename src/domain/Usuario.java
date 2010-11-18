package domain;

public class Usuario {

    private int codigo;
    private String nome;
    private String login;
    private String senha;
    private String documento;
    private Cargo cargo;
    private Setor setor;
    private String permissao;

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Usuario() {
        this.cargo = new Cargo();
        this.setor = new Setor();
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo val) {
        this.cargo = val;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int val) {
        this.codigo = val;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String val) {
        this.documento = val;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String val) {
        this.login = val;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String val) {
        this.nome = val;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String val) {
        this.senha = val;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor val) {
        this.setor = val;
    }
}

