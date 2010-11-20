package boundary;

import control.ControladoraClasse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FrmAlterarCidade extends FrmFormularioCidade implements ActionListener {

    private Vector linhaSelecionada;
    protected int Id;
    Vector linhasUf = new Vector();
    String UfAlterada;
    String status;

    public FrmAlterarCidade(ControladoraClasse controladora, Vector linha, String status,String servidor) {
        super(servidor);
        this.linhaSelecionada = linha;
        this.setTitle("[SCA] - Alterar Cidade");
        this.status = status;
        btOk.addActionListener(this);
        btCancelar.addActionListener(this);
        if (this.linhaSelecionada.get(1) != null) {
            tfNome.setText(this.linhaSelecionada.get(1).toString());
            Id = (Integer) (this.linhaSelecionada.get(0));
        }
        carregarUfs();
    }

    private void carregarUfs() {
        linhasUf = controladoraUf.obterLinhas("", "Ativo");
        for (int i = 0; i < linhasUf.size(); i++) {
            cbUF.addItem(((Vector) linhasUf.get(i)).get(1));
            if (this.linhaSelecionada.get(2).toString().equals(((Vector) linhasUf.get(i)).get(1).toString())) {
                cbUF.setSelectedIndex(cbUF.getItemCount() - 1);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btOk) {
            if (tfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "O Nome do Usuário é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                for (int i = 0; i < linhasUf.size(); i++) {
                    if (cbUF.getSelectedItem().toString().equals(((Vector) linhasUf.get(i)).get(1).toString())) {
                        UfAlterada = ((Vector) linhasUf.get(i)).get(0).toString();
                    }
                }
                Vector linhaNova = new Vector();
                linhaNova.addElement(Id);
                linhaNova.addElement(tfNome.getText());
                linhaNova.addElement(UfAlterada);
                if (this.controladora.alterar(linhaNova, status)) {
                    JOptionPane.showMessageDialog(null, "Cidade atualizada com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                this.dispose();
            }
        } else if (e.getSource() == btCancelar) {
            this.tfNome.setText("");
            this.cbUF.setSelectedIndex(-1);
            this.dispose();
        }
    }
}
