package boundary;

import control.ControladoraCidade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmDesativarCidade extends FrmAlterarStatus implements ActionListener {

    Vector linha;
    Vector responsavel;
    int Id;
    Date data = new Date();

    public FrmDesativarCidade(ControladoraCidade controladora, Vector responsavel, Vector linha) {
        super();
        this.responsavel = responsavel;
        this.setTitle("SCA-Ativar Usuário");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.controladora = controladora;
        this.btOk.addActionListener(this);
        this.linha = linha;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btOk) {
            desativarCidade();
            this.dispose();
        }
    }

    private void desativarCidade() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para desativar este Material !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            if (controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar") == true) {
                JOptionPane.showMessageDialog(null, "Material Desativado com sucesso!!", "Status Alterado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
