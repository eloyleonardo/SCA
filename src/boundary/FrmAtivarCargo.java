package boundary;

import control.ControladoraCargo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAtivarCargo extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmAtivarCargo(ControladoraCargo controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Ativar Cargo");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void ativarCargo() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para ativar este cargo !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Ativar");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            ativarCargo();
            this.dispose();
        }
    }
}
