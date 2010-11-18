package boundary;

import control.ControladoraUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import util.ActionFechar;

public class FrmInserirUsuario extends FrmFormularioUsuario implements ActionListener {

    int i;
    Vector linhasCargo = new Vector();
    Vector linhasSetor = new Vector();
    String cargoInserido;
    String setorInserido;

    public FrmInserirUsuario(ControladoraUsuario controladora) {
        this.setTitle("Inserir Novo Usuário");
        this.adicionarMap();
        linhasCargo = controladoraCargo.obterLinhas("", "Ativo");
        linhasSetor = controladoraSetor.obterLinhas("", "Ativo");
        for (i = 0; i < linhasCargo.size(); i++) {
            cbCargo.addItem(((Vector) linhasCargo.get(i)).get(1));
        }
        for (i = 0; i < linhasSetor.size(); i++) {
            cbSetor.addItem(((Vector) linhasSetor.get(i)).get(1));
        }
        this.cbCargo.setSelectedIndex(-1);
        this.cbSetor.setSelectedIndex(-1);
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String permissao = new String();
        boolean chefia = false;
        if (e.getSource() == btOk) {
            if (tfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "O Nome do Usuário é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (cbCargo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "O Cargo do usuário deve ser especificado!!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (cbSetor.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "O Setor do usuario deve ser especificado!!!", "Erro", JOptionPane.ERROR_MESSAGE);
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
                        }
                        break;
                    }
                }
                for (i = 0; i < linhasSetor.size(); i++) {
                    if (cbSetor.getSelectedItem().toString().equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                        setorInserido = ((Vector) linhasSetor.get(i)).get(0).toString();
                        break;
                    }
                }
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
                if (chefia == true && Integer.parseInt(((Vector) linhasSetor.get(i)).get(2).toString()) != -1) {
                    JOptionPane.showMessageDialog(null, "Esse setor já possui um responsável!!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Vector linhaNova = new Vector();
                    linhaNova.addElement(0);
                    linhaNova.addElement(this.tfNome.getText());
                    linhaNova.addElement(this.cargoInserido);
                    linhaNova.addElement(this.setorInserido);
                    linhaNova.addElement(this.tfLogin.getText());
                    linhaNova.addElement(this.pfSenha.getText());
                    linhaNova.addElement(this.tfDocumento.getText());
                    linhaNova.addElement(permissao);
                    controladora.inserir(linhaNova, chefia);
                    this.setVisible(false);
                }
            }
        }
    }
}
