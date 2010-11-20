package boundary;

import control.ControladoraSetor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAlterarSetor extends FrmFormularioSimples implements ActionListener {

    Vector linha;
    String status;

    public FrmAlterarSetor(ControladoraSetor controladora, Vector linha, String status) {
        super();
        this.setTitle("SCA-Alterar Setor");
        this.controladora = controladora;
        this.linha = linha;
        this.status = status;
        btOk.addActionListener(this);
        this.tfNome.setText(linha.get(1).toString());
    }

    private void alterarSetor() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Setor a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (linha.get(1).equals(tfNome.getText())) {
            JOptionPane.showMessageDialog(null, "O nome da Setor é igual ao anterior,\n" +
                    "não será alterado !", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            linha.remove(1);
            linha.add(this.tfNome.getText());
            if (controladora.alterar(linha, status)) {
                JOptionPane.showMessageDialog(null, "Setor atualizado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            alterarSetor();
        }
    }
}
