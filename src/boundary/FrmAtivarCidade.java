package boundary;

import control.ControladoraCidade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAtivarCidade extends FrmAlterarStatus implements ActionListener {

    Vector linha;
    Vector responsavel;
    int Id;
    Date data = new Date();

    public FrmAtivarCidade(ControladoraCidade controladora, Vector responsavel, Vector linha) {
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
            ativarCidade();
            this.dispose();
        }
    }

    private void ativarCidade() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para ativar este Material !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            if (controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Ativar")) {
                JOptionPane.showMessageDialog(null, "Material Ativado com sucesso!!", "Status Alterado", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
}
