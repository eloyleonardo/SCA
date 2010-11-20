package dao;

import domain.Material;
import domain.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public interface MaterialDao {

    public Vector carregarLotesMaterial(int codMaterial) throws SQLException;

    public boolean excluirMaterial(Connection conexao) throws SQLException;

    public void inserirMaterial(Material material) throws SQLException;

    public void alterarMaterial(Material material) throws SQLException;

    public Vector<Material> obterMateriais(String nome, String status) throws SQLException;

    public void alterarStatusMaterial(Material m, String motivo, String acao, Vector r) throws SQLException;

    public void alterarEstoqueMaterial(int id, Double soma) throws SQLException;

    public void MaterialAbaixo(int codMaterial) throws SQLException;

    public void alterarQuantidadeMaterial(Material material, Connection conexao) throws SQLException;

    public void alterarStatusMaterial(int id, String motivo, String acao, Usuario usuario) throws SQLException;

    void darCiencia(Material material) throws SQLException;

    public Vector<Material> obterMateriaisAbaixoEstoque() throws SQLException;

    public Vector<Material> obterMateriaisInativos(String nome) throws SQLException;

    public Vector<Material> obterMateriaisAtivos(String nome) throws SQLException;

    public Vector<Material> obterMaterialAtivoCodigo(String codigo) throws SQLException;

    public void alterarMaterial(Material material, int Id) throws SQLException;
}
