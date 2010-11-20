package boundary;

import control.ControladoraCargo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import util.ActionFechar;

public class FrmAlterarCargo extends FrmFormularioCargo implements ActionListener {

    Vector linha;
    String status;

    public FrmAlterarCargo(ControladoraCargo controladora, Vector linha, String status) {
        super();
        this.setTitle("SCA-Alterar Cargo");
        this.controladora = controladora;
        this.linha = linha;
        this.status = status;
        btOk.addActionListener(this);
        this.adicionarMap();
        this.tfNome.setText(linha.get(1).toString());
        if (linha.get(2).toString().equals("Sim")) {
            rdSim.setSelected(true);
        } else {
            rdNao.setSelected(true);
        }
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void alterarCargo() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome do cargo a ser inserido !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if ((!rdSim.isSelected()) && (!rdNao.isSelected())) {
            JOptionPane.showMessageDialog(null, "Informe se este é ou não um cargo de chefia !", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            linha.remove(1);
            linha.remove(1);
            linha.add(this.tfNome.getText());
            if (rdSim.isSelected()) {
                linha.add(true);
            } else {
                linha.add(false);
            }
            if (controladora.alterar(linha, status)) {
                JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            alterarCargo();
        }
    }
}
