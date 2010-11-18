package boundary;

import control.ControladoraUnidade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAtivarUnidade extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmAtivarUnidade(ControladoraUnidade controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Ativar Unidade");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void ativarUnidade() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para ativar esta Unidade !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Ativar");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            ativarUnidade();
            this.dispose();
        }
    }
}
