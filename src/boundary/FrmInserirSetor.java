package boundary;

import control.ControladoraSetor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmInserirSetor extends FrmFormularioSimples implements ActionListener {

    public FrmInserirSetor(ControladoraSetor controladora) {
        super();
        this.setTitle("SCA-Inserir Setor");
        this.controladora = controladora;
        btOk.addActionListener(this);

    }

    private void inserirSetor() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Setor a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Vector linha = new Vector(1);
            linha.add(0);
            linha.add(tfNome.getText());
            if (controladora.inserir(linha)) {
                JOptionPane.showMessageDialog(null, "Setor " + tfNome.getText() + " Inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            inserirSetor();
        }
    }
}
