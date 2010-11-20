package domain;

public class Fornecedor {

    private String id;
    private String nome;
    private String nomeFantasia;
    private String endereco;
    private String complemento;
    private String bairro;
    private Cidade cidade;
    private String fax;
    private String email;
    private String telefone1;
    private String telefone2;
    private TipoEndereco TipoEndereco;
    private String cep;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Fornecedor() {
    }

    public TipoEndereco getTipoEndereco() {
        return TipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco val) {
        this.TipoEndereco = val;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String val) {
        this.bairro = val;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String val) {
        this.complemento = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String val) {
        this.email = val;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String val) {
        this.endereco = val;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String val) {
        this.fax = val;
    }

    public String getId() {
        return id;
    }

    public void setId(String val) {
        this.id = val;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String val) {
        this.nome = val;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String val) {
        this.nomeFantasia = val;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String toString() {
        return this.nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fornecedor other = (Fornecedor) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
