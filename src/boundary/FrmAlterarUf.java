package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAlterarUf extends FrmFormularioUF implements ActionListener {

    Vector linha;
    String status;

    public FrmAlterarUf(ControladoraClasse controladora, Vector linha, String status) {
        super();
        this.setTitle("SCA-Alterar Uf");
        this.controladora = controladora;
        this.linha = linha;
        this.status = status;
        btOk.addActionListener(this);
        this.tfSigla.setText(linha.get(0).toString());
        this.tfNome.setText(linha.get(1).toString());
    }

    private void alterarUf() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Uf a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (this.tfSigla.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite a Sigla da Uf a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (linha.get(1).equals(tfNome.getText())) {
            JOptionPane.showMessageDialog(null, "O nome da Uf é igual ao anterior,\n" +
                    "não será alterado !", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else if (linha.get(0).equals(tfSigla.getText())) {
            JOptionPane.showMessageDialog(null, "A sigla da Uf é igual ao anterior,\n" +
                    "não será alterado !", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            linha.remove(0);
            linha.add(this.tfSigla.getText());
            linha.remove(1);
            linha.add(this.tfNome.getText());
            if (this.controladora.alterar(linha, status)) {
                JOptionPane.showMessageDialog(null, "Uf atualizado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            this.alterarUf();
        }
    }
}
