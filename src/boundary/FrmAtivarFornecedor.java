package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAtivarFornecedor extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmAtivarFornecedor(ControladoraClasse controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Ativar Fornecedor");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void ativarFornecedor() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para ativar este Fornecedor !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Ativar");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            ativarFornecedor();
            this.dispose();
        }
    }
}
