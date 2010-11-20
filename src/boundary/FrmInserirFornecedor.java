package boundary;

import control.ControladoraFornecedor;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class FrmInserirFornecedor extends FrmFormularioFornecedor implements ActionListener {

    public FrmInserirFornecedor(ControladoraFornecedor controladora, String servidor) {
        super(servidor);
        this.controladora = controladora;
        this.setTitle("SCA-Inserir Fornecedor");
        this.btOk.addActionListener(this);
    }

    private void operacao() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (this.controladora.inserir(criarLinha())) {
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
