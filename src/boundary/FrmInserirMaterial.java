package boundary;

import control.ControladoraMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

class FrmInserirMaterial extends FrmFormularioMaterial implements ActionListener {

    Vector linhasNd = new Vector();
    Vector linhasSubitem = new Vector();
    Vector linhasUnidade = new Vector();
    String NdInserida;
    String SubitemInserido;
    String UnidadeInserida;
    int i;

    public FrmInserirMaterial(ControladoraMaterial controladora) {
        super();
        this.setTitle("Inserir Novo Material");
        this.controladora = controladora;
        linhasNd = controladoraNd.obterLinhasND();
        linhasSubitem = controladoraSubitem.obterLinhasSubitem();
        linhasUnidade = controladoraUnidade.obterLinhas("", "Ativo");
        for (i = 0; i < linhasNd.size(); i++) {
            cbND.addItem(((Vector) linhasNd.get(i)).get(0));
        }
        for (i = 0; i < linhasSubitem.size(); i++) {
            cbSubitem.addItem(((Vector) linhasSubitem.get(i)).get(0));
        }
        for (i = 0; i < linhasUnidade.size(); i++) {
            cbUnidade.addItem(((Vector) linhasUnidade.get(i)).get(1));
        }
        this.cbND.setSelectedIndex(-1);
        this.cbUnidade.setSelectedIndex(-1);
        this.cbSubitem.setSelectedIndex(-1);
        btOk.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btOk) {
            if (tfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "O Nome do Material é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (cbND.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Você deve especificar a ND!!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (cbSubitem.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "O subitem deve ser especificado!!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (cbUnidade.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "A Subitem do material é obrigatória!!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (tfQtdMinimo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Você deve especificar a quantidade mínima!!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                for (i = 0; i < linhasNd.size(); i++) {
                    if (cbND.getSelectedItem().toString().equals(((Vector) linhasNd.get(i)).get(0).toString())) {
                        NdInserida = ((Vector) linhasNd.get(i)).get(0).toString();
                    }
                }
                for (i = 0; i < linhasSubitem.size(); i++) {
                    if (cbSubitem.getSelectedItem().toString().equals(((Vector) linhasSubitem.get(i)).get(0).toString())) {
                        SubitemInserido = ((Vector) linhasSubitem.get(i)).get(0).toString();
                    }
                }
                for (i = 0; i < linhasUnidade.size(); i++) {
                    if (cbUnidade.getSelectedItem().toString().equals(((Vector) linhasUnidade.get(i)).get(1).toString())) {
                        UnidadeInserida = ((Vector) linhasUnidade.get(i)).get(0).toString();
                    }
                }
                Vector linha = new Vector();
                linha.addElement(0);
                linha.addElement(this.tfNome.getText());
                linha.addElement(this.tfQtdMinimo.getText());
                linha.addElement(this.NdInserida);
                linha.addElement(this.SubitemInserido);
                linha.addElement(this.UnidadeInserida);
                controladora.inserir(linha);
                this.dispose();
            }
        } else {
            this.dispose();
        }
    }
}
