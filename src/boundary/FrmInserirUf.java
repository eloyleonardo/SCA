package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmInserirUf extends FrmFormularioUF implements ActionListener {

    public FrmInserirUf(ControladoraClasse controladora) {
        super();
        this.setTitle("SCA-Inserir Uf");
        this.controladora = controladora;
        btOk.addActionListener(this);

    }

    private void inserirUf() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Uf a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (this.tfSigla.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite a Sigla da Uf a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Vector linha = new Vector(1);
            linha.add(this.tfSigla.getText());
            linha.add(tfNome.getText());
            if (controladora.inserir(linha)) {
                JOptionPane.showMessageDialog(null, "Uf " + tfNome.getText() + " Inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            inserirUf();
        }
    }
}
