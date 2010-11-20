package boundary;

import control.ControladoraMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAlterarMaterial extends FrmFormularioMaterial implements ActionListener {

    private Vector linhaSelecionada;
    private Vector linhasUnidade = new Vector();
    private Vector linhasND = new Vector();
    private Vector linhasSubitem = new Vector();
    private String ndInserida;
    private String subitemInserido;
    private String unidadeInserida;
    private String status;
    private int ID;

    public FrmAlterarMaterial(ControladoraMaterial controladora, Vector linhaSelecionada, String status) {
        this.controladora = controladora;
        this.linhaSelecionada = linhaSelecionada;
        this.status = status;
        int i;
        linhasND = controladoraNd.obterLinhasND();
        linhasSubitem = controladoraSubitem.obterLinhasSubitem();
        linhasUnidade = controladoraUnidade.obterLinhas("", "Ativo");
        for (i = 0; i < linhasND.size(); i++) {
            cbND.addItem(((Vector) linhasND.get(i)).get(0));
            if (this.linhaSelecionada.get(3).toString().equals(((Vector) linhasND.get(i)).get(0).toString())) {
                cbND.setSelectedIndex(cbND.getItemCount() - 1);
            }
        }
        for (i = 0; i < linhasSubitem.size(); i++) {
            cbSubitem.addItem(((Vector) linhasSubitem.get(i)).get(0));
            if (this.linhaSelecionada.get(4).toString().equals(((Vector) linhasSubitem.get(i)).get(0).toString())) {
                cbSubitem.setSelectedIndex(cbSubitem.getItemCount() - 1);
            }
        }
        for (i = 0; i < linhasUnidade.size(); i++) {
            cbUnidade.addItem(((Vector) linhasUnidade.get(i)).get(1));
            if (this.linhaSelecionada.get(5).toString().equals(((Vector) linhasUnidade.get(i)).get(1).toString())) {
                cbUnidade.setSelectedIndex(cbUnidade.getItemCount() - 1);
            }
        }

        this.ID = (Integer.parseInt(linhaSelecionada.get(0).toString()));
        this.tfNome.setText(linhaSelecionada.get(1).toString());
        this.tfQtdMinimo.setText(linhaSelecionada.get(2).toString());
        btOk.addActionListener(this);
    }

    private void alterarMaterial() {
        int i;
        if (this.tfNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o nome do material a ser inserido !", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (tfQtdMinimo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite a quantidade minima\n" +
                    "não será alterado !", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            for (i = 0; i < linhasND.size(); i++) {
                if (cbND.getSelectedItem().toString().equals(((Vector) linhasND.get(i)).get(0).toString())) {
                    ndInserida = ((Vector) linhasND.get(i)).get(0).toString();
                }
            }
            for (i = 0; i < linhasSubitem.size(); i++) {
                if (cbSubitem.getSelectedItem().toString().equals(((Vector) linhasSubitem.get(i)).get(0).toString())) {
                    subitemInserido = ((Vector) linhasSubitem.get(i)).get(0).toString();
                }
            }
            for (i = 0; i < linhasUnidade.size(); i++) {
                if (cbUnidade.getSelectedItem().toString().equals(((Vector) linhasUnidade.get(i)).get(1).toString())) {
                    unidadeInserida = ((Vector) linhasUnidade.get(i)).get(0).toString();
                }
            }
            Vector linhaInserida = new Vector();
            linhaInserida.addElement(ID);
            linhaInserida.addElement(this.tfNome.getText());
            linhaInserida.addElement(this.tfQtdMinimo.getText());
            linhaInserida.addElement(ndInserida);
            linhaInserida.addElement(subitemInserido);
            linhaInserida.addElement(unidadeInserida);
            controladora.alterar(linhaInserida, "");
            this.dispose();

        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            alterarMaterial();
        }
    }
}
