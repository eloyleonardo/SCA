package boundary;

import control.ControladoraClasse;
import control.ControladoraMaterial;
import control.ControladoraUnidade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;


class FrmDesativarMaterial extends FrmAlterarStatus implements ActionListener {

    Vector linha;
    Vector responsavel;
    Date data = new Date();
    Vector linhasUnidade = new Vector();

    public FrmDesativarMaterial(ControladoraMaterial controladora, Vector responsavel, Vector linha,String servidor) {
        super();
        ControladoraClasse controladoraUnidade = new ControladoraUnidade(servidor);
        this.controladora = controladora;
        linhasUnidade = controladoraUnidade.obterLinhas("", "Ativo");
        this.linha = linha;
        this.responsavel = responsavel;
        this.setTitle("SCA-Ativar Material");
        this.lbUsuario.setText("Usuario: " + responsavel.get(1));
        this.controladora = controladora;
        btOk.addActionListener(this);
    }

    public void FrmDesativarMaterial() {
    }

    private void desativarMaterial() {
        for (int i = 0; i < linhasUnidade.size(); i++) {
            if (linha.get(5).toString().equals(((Vector) linhasUnidade.get(i)).get(1).toString())) {
                linha.set(5, ((Vector) linhasUnidade.get(i)).get(0).toString());
            }
        }
        if (this.taMotivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um motivo para desativar este Material !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            if (controladora.alterarStatus(taMotivo.getText(), responsavel, linha, "Desativar")) {
                JOptionPane.showMessageDialog(null, "Status Alterado com Sucesso !", "Status Alterado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btOk) {
            desativarMaterial();
            this.dispose();
        }
    }
}
