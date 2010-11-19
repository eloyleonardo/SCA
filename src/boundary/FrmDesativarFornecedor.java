package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmDesativarFornecedor extends FrmAlterarStatus implements ActionListener {

    Vector linha = new Vector();
    Vector responsavel;

    public FrmDesativarFornecedor(ControladoraClasse controladora, Vector linha, Vector responsavel) {
        super();
        this.setTitle("SCA-Desativar Fornecedor");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.responsavel = responsavel;
        this.controladora = controladora;
        this.linha = linha;
        btOk.addActionListener(this);
    }

    private void desativarFornecedor() {
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para desativar este Fornecedor !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar");
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            desativarFornecedor();
            this.dispose();
        }
    }
}
