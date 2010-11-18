package boundary;

import control.ControladoraUsuario;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class FrmAlterarUsuario extends FrmFormularioUsuario {

    private Vector linhaSelecionada;
    protected int Id;
    int i;
    Vector linhasCargo = new Vector();
    Vector linhasSetor = new Vector();
    String cargoInserido;
    String setorInserido;
    String permissao = new String();

    public FrmAlterarUsuario(ControladoraUsuario controladora, Vector linha) {
        this.linhaSelecionada = linha;
        this.setTitle("Alterar Dados do Usuário");
        linhasCargo = controladoraCargo.obterLinhas("", "Ativo");
        linhasSetor = controladoraSetor.obterLinhas("", "Ativo");
        for (i = 0; i < linhasCargo.size(); i++) {
            cbCargo.addItem(((Vector) linhasCargo.get(i)).get(1));
            if (this.linhaSelecionada.get(2).toString().equals(((Vector) linhasCargo.get(i)).get(1).toString())) {
                cbCargo.setSelectedIndex(cbCargo.getItemCount() - 1);
            }
        }
        for (i = 0; i < linhasSetor.size(); i++) {
            cbSetor.addItem(((Vector) linhasSetor.get(i)).get(1));
            if (this.linhaSelecionada.get(3).toString().equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                cbSetor.setSelectedIndex(cbSetor.getItemCount() - 1);
            }
        }

        if (this.linhaSelecionada.get(1) != null) {
            tfNome.setText(this.linhaSelecionada.get(1).toString());
            Id = (Integer) (this.linhaSelecionada.get(0));
        }

        if (this.linhaSelecionada.get(4) != null) {
            tfLogin.setText(this.linhaSelecionada.get(4).toString());
        }
        if (this.linhaSelecionada.get(5) != null) {
            pfSenha.setText(this.linhaSelecionada.get(5).toString());
        }
        if (this.linhaSelecionada.get(6) != null) {
            tfDocumento.setText(this.linhaSelecionada.get(6).toString());
        }
        if (this.linhaSelecionada.get(7) != null) {
            permissao = this.linhaSelecionada.get(7).toString();
            for (i = 0; i < permissao.length(); i++) {
                if (permissao.charAt(i) == 'A') {
                    chbCadastrar.setSelected(true);
                }
                if (permissao.charAt(i) == 'B') {
                    chbCadUsuarios.setSelected(true);
                }
                if (permissao.charAt(i) == 'C') {
                    chbEntrada.setSelected(true);
                }
                if (permissao.charAt(i) == 'D') {
                    chbSaida.setSelected(true);
                }
                if (permissao.charAt(i) == 'E') {
                    chbRelatorios.setSelected(true);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        try{
        if (e.getSource() == this.btOk) {
            alterarUsuario();

        } else if ((e.getSource() == this.btCancelar)) {
            tfNome.setText("");
            cbCargo.setSelectedIndex(-1);
            cbSetor.setSelectedIndex(-1);
            pfSenha.setText("");
            tfDocumento.setText("");
            chbEntrada.setSelected(false);
            chbCadastrar.setSelected(false);
            chbEntrada.setSelected(false);
            chbSaida.setSelected(false);
            chbRelatorios.setSelected(false);
            this.setVisible(false);
        }
    }

    private void alterarUsuario() {
        boolean saidaOk = true;
        boolean chefia = false;
        boolean cargoChefe = false;
        boolean tornarFuncionario = false;
        int n = 0;
        if (tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O Nome do Usuário é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (tfLogin.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o Login do usuário!!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (pfSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite a Senha do usuário!!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (tfDocumento.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o Documento do usuário!!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (i = 0; i < linhasCargo.size(); i++) {
                if (cbCargo.getSelectedItem().toString().equals(((Vector) linhasCargo.get(i)).get(1).toString())) {
                    cargoInserido = ((Vector) linhasCargo.get(i)).get(0).toString();
                    if (((Vector) linhasCargo.get(i)).get(2).toString().equals("Sim")) {
                        chefia = true;
                        break;
                    }
                }
            }
            for (i = 0; i < linhasCargo.size(); i++) {
                if (this.linhaSelecionada.get(2).toString().equals(((Vector) linhasCargo.get(i)).get(1).toString())) {
                    if (((Vector) linhasCargo.get(i)).get(2).toString().equals("Sim")) {
                        cargoChefe = true;
                        break;
                    }
                }
            }
            for (i = 0; i < linhasSetor.size(); i++) {
                if (cbSetor.getSelectedItem().toString().equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                    setorInserido = ((Vector) linhasSetor.get(i)).get(0).toString();
                }
            }
            permissao = "";
            if (chbCadastrar.isSelected() == true) {
                permissao = permissao + "A";
            }
            if (chbCadUsuarios.isSelected() == true) {
                permissao = permissao + "B";
            }
            if (chbEntrada.isSelected() == true) {
                permissao = permissao + "C";
            }
            if (chbSaida.isSelected() == true) {
                permissao = permissao + "D";
            }
            if (chbRelatorios.isSelected() == true) {
                permissao = permissao + "E";
            }
            if (chefia == true) {
                for (i = 0; i < linhasSetor.size(); i++) {
                    if (cbSetor.getSelectedItem().toString().equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                        if (Integer.parseInt(((Vector) linhasSetor.get(i)).get(2).toString()) != -1) {
                            JOptionPane.showMessageDialog(null, "Esse setor já possui um responsável!!", "Erro", JOptionPane.ERROR_MESSAGE);
                            saidaOk = false;
                            n = i;
                            break;
                        }
                    }
                }
            }
            if (saidaOk == true) {
                if ((cargoChefe == true && chefia == false) || (cargoChefe == true && !((Vector) linhasSetor.get(n)).get(1).toString().equals(this.linhaSelecionada.get(3).toString()))) {
                    tornarFuncionario = true;
                }
                Vector linhaNova = new Vector();
                linhaNova.addElement(Id);
                linhaNova.addElement(this.tfNome.getText());
                linhaNova.addElement(this.cargoInserido);
                linhaNova.addElement(this.setorInserido);
                linhaNova.addElement(this.tfLogin.getText());
                linhaNova.addElement(this.pfSenha.getText());
                linhaNova.addElement(this.tfDocumento.getText());
                linhaNova.addElement(permissao);
                this.controladora.alterar(linhaNova, tornarFuncionario, chefia);
                this.setVisible(false);
            }
        }
    }
}
