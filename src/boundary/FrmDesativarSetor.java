package boundary;

import control.ControladoraSetor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmDesativarSetor extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmDesativarSetor(ControladoraSetor controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Desativar Setor");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void desativarSetor() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para desativar esta Setor !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar");
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            desativarSetor();
            this.dispose();
        }
    }
}
