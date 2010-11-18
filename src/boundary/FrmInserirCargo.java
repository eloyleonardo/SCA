package boundary;

import control.ControladoraCargo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmInserirCargo extends FrmFormularioCargo implements ActionListener {

    public FrmInserirCargo(ControladoraCargo controladora) {
        super();
        this.setTitle("SCA-Inserir Cargo");
        this.controladora = controladora;
        btOk.addActionListener(this);

    }

    private void inserirCargo() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome do cargo a ser inserido !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Vector linha = new Vector(1);
            linha.add(0);
            linha.add(tfNome.getText());
            if (rdSim.isSelected()) {
                linha.add(true);
            } else {
                linha.add(false);
            }
            if (controladora.inserir(linha)) {
                JOptionPane.showMessageDialog(null, "Cargo " + tfNome.getText() + " Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            inserirCargo();
        }
    }
}
