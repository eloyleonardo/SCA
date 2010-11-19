package boundary;

import control.ControladoraCargo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmDesativarCargo extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmDesativarCargo(ControladoraCargo controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Desativar Cargo");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void desativarCargo() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para desativar este cargo !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar");
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            desativarCargo();
            this.dispose();
        }
    }
}
