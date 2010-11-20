package boundary;

import control.ControladoraCargo;
import control.ControladoraSetor;
import control.ControladoraUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmDesativarUsuario extends FrmAlterarStatus implements ActionListener {

    private Vector linha;
    private Vector responsavel;
    private Vector linhasCargo = new Vector();
    private Vector linhasSetor = new Vector();
    private String cargoInserido;
    private String setorInserido;
    private ControladoraCargo controladoraCargo;
    private ControladoraSetor controladoraSetor;

    public FrmDesativarUsuario(Vector linha, Vector responsavel, ControladoraUsuario controladora,String servidor) {
        super();
        this.linha = linha;
        controladoraSetor = new ControladoraSetor(servidor);
        controladoraCargo = new ControladoraCargo(servidor);
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.setTitle("SCA - Desativar Usuário");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        btOk.addActionListener(this);
        linhasCargo = controladoraCargo.obterLinhas("", "Ativo");
        linhasSetor = controladoraSetor.obterLinhas("", "Ativo");
    }

    private void DesativarUsuario() {
        int i;
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para Ativar este usuário !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            for (i = 0; i < linhasCargo.size(); i++) {
                if (linha.get(2).toString().equals(((Vector) linhasCargo.get(i)).get(1).toString())) {
                    cargoInserido = ((Vector) linhasCargo.get(i)).get(0).toString();
                    break;
                }
            }
            for (i = 0; i < linhasSetor.size(); i++) {
                if (linha.get(3).toString().equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                    setorInserido = ((Vector) linhasSetor.get(i)).get(0).toString();
                    break;
                }
            }
            if (linha.get(7) == null) {
                linha.set(7, "");
            }
            linha.set(2, cargoInserido);
            linha.set(3, setorInserido);
            if (controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar") == true) {
                JOptionPane.showMessageDialog(null, "Usuário Desativado com sucesso!!", "Status Alterado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            DesativarUsuario();
            this.dispose();
        }
    }
}
