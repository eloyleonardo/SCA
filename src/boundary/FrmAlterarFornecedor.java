package boundary;

import control.ControladoraFornecedor;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAlterarFornecedor extends FrmFormularioFornecedor implements ActionListener {

    String status;

    public FrmAlterarFornecedor(ControladoraFornecedor controladora, Vector linha, String status) {
        super();
        this.controladora = controladora;
        this.setTitle("SCA-Alterar Fornecedor");
        this.btOk.addActionListener(this);
        this.setarAtributos(linha);
        this.tfId.setEnabled(false);
        this.cbId.setEnabled(false);
        this.status = status;
    }

    private void operacao() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (this.controladora.alterar(criarLinha(), status)) {
            JOptionPane.showMessageDialog(null, "Fornecedor Cadastrado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void actionPerformed(ActionEvent e) {
        if (this.validarCampos()) {
            this.operacao();
            this.dispose();
        }
    }
}
