package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAtivarUf extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmAtivarUf(ControladoraClasse controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Ativar Uf");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void ativarUf() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para ativar esta Uf !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Ativar");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            ativarUf();
            this.dispose();
        }
    }
}
