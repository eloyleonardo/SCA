package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import util.ActionFechar;

public class FrmInserirCidade extends FrmFormularioCidade implements ActionListener {

    private Vector linhasUf = new Vector();
    private String ufInserida;

    public FrmInserirCidade(ControladoraClasse controladora) {
        super();
        this.setTitle("[SCA] - Inserir Cidade");
        this.controladora = controladora;
        btOk.addActionListener(this);
        btCancelar.addActionListener(this);
        linhasUf = carregarUfs();
        this.adicionarMap();
        this.cbUF.setSelectedIndex(-1);
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private Vector carregarUfs() {
        Vector linhas;
        linhas = controladoraUf.obterLinhas("", "Ativo");
        for (int i = 0; i < linhas.size(); i++) {
            cbUF.addItem(((Vector) linhas.get(i)).get(1));
        }
        return linhas;
    }

    private void inserirCidade() {
        for (int i = 0; i < linhasUf.size(); i++) {
            if (cbUF.getSelectedItem().toString().equals(((Vector) linhasUf.get(i)).get(1).toString())) {
                ufInserida = ((Vector) linhasUf.get(i)).get(0).toString();
                break;
            }
        }
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome da Cidade a ser inserida !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (this.cbUF.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Escolha a qual Estado essa Cidade!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Vector linha = new Vector();
            linha.add(0);
            linha.add(tfNome.getText());
            linha.add(ufInserida);
            if (controladora.inserir(linha)) {
                JOptionPane.showMessageDialog(null, "Cidade " + tfNome.getText() + " Inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            inserirCidade();
        } else if (e.getSource() == btCancelar) {
            this.tfNome.setText("");
            this.cbUF.setSelectedIndex(-1);
            this.dispose();
        }
    }
}
