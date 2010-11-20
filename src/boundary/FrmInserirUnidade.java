package boundary;

import control.ControladoraUnidade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import util.ActionFechar;

public class FrmInserirUnidade extends FrmFormularioSimples implements ActionListener {

    public FrmInserirUnidade(ControladoraUnidade controladora) {
        super();
        this.setTitle("SCA-Inserir Unidade");
        this.controladora = controladora;
        this.adicionarMap();
        btOk.addActionListener(this);

    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void inserirUnidade() {
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Unidade a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Vector linha = new Vector(1);
            linha.add(0);
            linha.add(tfNome.getText());
            if (controladora.inserir(linha)) {
                JOptionPane.showMessageDialog(null, "Unidade " + tfNome.getText() + " Inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            inserirUnidade();
        }
    }
}
