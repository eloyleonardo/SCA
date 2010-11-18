package boundary;

import control.ControladoraCargo;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmPrincipalCargo extends javax.swing.JFrame {

    ControladoraCargo controladora = new ControladoraCargo();
    Vector responsavel;

    public FrmPrincipalCargo(Vector responsavel) {
        initComponents();
        this.setLocationRelativeTo(null);
        preencherTabela();
        this.responsavel = responsavel;
        this.btAtivar.setEnabled(false);
        adicionarMap();

    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void preencherTabela() {
        Vector linhas;
        linhas = controladora.obterLinhas("" + this.tfPesquisar.getText(), cbSatus.getSelectedItem().toString());
        DefaultTableModel modelo = (DefaultTableModel) this.tbCargos.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void preencherTabela(Vector linhas) {
        DefaultTableModel modelo = (DefaultTableModel) this.tbCargos.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbCargos.getModel();
        int numLinhas = tbCargos.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector(3);
        for (int i = 0; i < 3; i++) {
            linha.add(this.tbCargos.getModel().getValueAt(this.tbCargos.getSelectedRow(), i));
        }
        return linha;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCargos = new javax.swing.JTable();
        btInserir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btAtivar = new javax.swing.JButton();
        btDesativar = new javax.swing.JButton();
        cbSatus = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Listar Cargos");
        setResizable(false);

        jLabel1.setText("Nome:");

        tbCargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Chefia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCargos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbCargos);
        tbCargos.getColumnModel().getColumn(0).setMinWidth(50);
        tbCargos.getColumnModel().getColumn(0).setMaxWidth(50);
        tbCargos.getColumnModel().getColumn(1).setResizable(false);
        tbCargos.getColumnModel().getColumn(2).setMinWidth(50);
        tbCargos.getColumnModel().getColumn(2).setMaxWidth(50);

        btInserir.setText("Inserir");
        btInserir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btInserirMouseClicked(evt);
            }
        });

        btAlterar.setText("Alterar");
        btAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAlterarMouseClicked(evt);
            }
        });

        btAtivar.setText("Ativar");
        btAtivar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAtivarMouseClicked(evt);
            }
        });

        btDesativar.setText("Desativar");
        btDesativar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btDesativarMouseClicked(evt);
            }
        });

        cbSatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo" }));
        cbSatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSatusItemStateChanged(evt);
            }
        });

        jLabel2.setText("Listar:");

        btFechar.setText("Fechar");
        btFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btFecharMouseClicked(evt);
            }
        });

        btPesquisar.setText("Pesquisar");
        btPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btPesquisarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSatus, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btDesativar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btAtivar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(cbSatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btPesquisar))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btInserir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAtivar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDesativar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btFechar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btInserirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btInserirMouseClicked
        JDialog janela = new FrmInserirCargo(controladora);
        janela.setModal(true);
        janela.setVisible(true);
        if (cbSatus.getSelectedItem().equals("Ativo")) {
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btInserirMouseClicked

    private void btFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btFecharMouseClicked
        this.dispose();
}//GEN-LAST:event_btFecharMouseClicked

    private void btAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterarMouseClicked
        int linhaSelecionada = this.tbCargos.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um cargo !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmAlterarCargo(controladora, criarLinhaSelecao(), cbSatus.getSelectedItem().toString());
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btAlterarMouseClicked

    private void btPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPesquisarMouseClicked
        limparTabela();
        preencherTabela(controladora.pesquisar(tfPesquisar.getText(), cbSatus.getSelectedItem().toString()));
}//GEN-LAST:event_btPesquisarMouseClicked

    private void btAtivarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAtivarMouseClicked
        int linhaSelecionada = this.tbCargos.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um cargo !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmAtivarCargo(controladora, criarLinhaSelecao(), responsavel);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btAtivarMouseClicked

    private void cbSatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSatusItemStateChanged
        if (this.cbSatus.getSelectedItem().equals("Ativo")) {
            this.btAtivar.setEnabled(false);
            this.btDesativar.setEnabled(true);
        } else {
            this.btDesativar.setEnabled(false);
            this.btAtivar.setEnabled(true);
        }
        tfPesquisar.setText("");
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_cbSatusItemStateChanged

    private void btDesativarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btDesativarMouseClicked
        int linhaSelecionada = this.tbCargos.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um cargo !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmDesativarCargo(controladora, criarLinhaSelecao(), responsavel);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
    }//GEN-LAST:event_btDesativarMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAtivar;
    private javax.swing.JButton btDesativar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btInserir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JComboBox cbSatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCargos;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
