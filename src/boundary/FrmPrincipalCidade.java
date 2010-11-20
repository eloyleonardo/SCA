package boundary;

import control.ControladoraCidade;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmPrincipalCidade extends javax.swing.JFrame {

    private Vector responsavel;
    private ControladoraCidade controladora;
    private String servidor;

    public FrmPrincipalCidade(Vector usuario, String servidor) {
        initComponents();
        this.servidor = servidor;
        this.controladora = new ControladoraCidade(servidor);
        this.setLocationRelativeTo(null);
        preencherTabela();
        this.responsavel = usuario;
        this.btAtivar.setEnabled(false);
        adicionarMap();

    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btPesquisar = new javax.swing.JButton();
        tfPesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCidade = new javax.swing.JTable();
        btInserir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btAtivar = new javax.swing.JButton();
        btDesativar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("[SCA] - Cadastro de Cidades");
        setResizable(false);

        jLabel1.setText("Nome:");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        jLabel2.setText("Listar:");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo" }));
        cbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStatusItemStateChanged(evt);
            }
        });

        jtCidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome da Cidade", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtCidade);
        jtCidade.getColumnModel().getColumn(0).setMinWidth(60);
        jtCidade.getColumnModel().getColumn(0).setPreferredWidth(60);
        jtCidade.getColumnModel().getColumn(0).setMaxWidth(60);
        jtCidade.getColumnModel().getColumn(2).setMinWidth(90);
        jtCidade.getColumnModel().getColumn(2).setPreferredWidth(90);
        jtCidade.getColumnModel().getColumn(2).setMaxWidth(90);

        btInserir.setText("Inserir");
        btInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInserirActionPerformed(evt);
            }
        });

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btAtivar.setText("Ativar");
        btAtivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtivarActionPerformed(evt);
            }
        });

        btDesativar.setText("Desativar");
        btDesativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesativarActionPerformed(evt);
            }
        });

        jButton1.setText("Fechar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btPesquisar)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbStatus, 0, 116, Short.MAX_VALUE)
                    .addComponent(btInserir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(btAtivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(btDesativar, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed

        limparTabela();
        preencherTabela();
}//GEN-LAST:event_btPesquisarActionPerformed

    private void cbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStatusItemStateChanged
        if (this.cbStatus.getSelectedItem().equals("Ativo")) {
            this.btAtivar.setEnabled(false);
            this.btDesativar.setEnabled(true);
        } else {
            this.btDesativar.setEnabled(false);
            this.btAtivar.setEnabled(true);
        }
        tfPesquisar.setText("");
        limparTabela();
        preencherTabela();
}//GEN-LAST:event_cbStatusItemStateChanged

    private void btInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInserirActionPerformed
        JDialog janela = new FrmInserirCidade(controladora,servidor);
        janela.setModal(true);
        janela.setVisible(true);
        if (cbStatus.getSelectedItem().equals("Ativo")) {
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btInserirActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        int linhaSelecionada = this.jtCidade.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma Uf !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmAlterarCidade(controladora, criarLinhaSelecao(), cbStatus.getSelectedItem().toString(),servidor);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btAlterarActionPerformed

    private void btAtivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtivarActionPerformed
        int quant = this.jtCidade.getColumnCount();
        int linha_selecionada = this.jtCidade.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmAtivarCidade(controladora, responsavel, linha);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btAtivarActionPerformed

    private void btDesativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesativarActionPerformed
        int quant = this.jtCidade.getColumnCount();
        int linha_selecionada = this.jtCidade.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmDesativarCidade(controladora, responsavel, linha);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
}//GEN-LAST:event_btDesativarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAtivar;
    private javax.swing.JButton btDesativar;
    private javax.swing.JButton btInserir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JComboBox cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtCidade;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables

    private void preencherTabela() {
        Vector linhas;
        linhas = controladora.obterLinhas("" + this.tfPesquisar.getText(), cbStatus.getSelectedItem().toString());
        DefaultTableModel modelo = (DefaultTableModel) this.jtCidade.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.jtCidade.getModel();
        int numLinhas = jtCidade.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector();
        for (int i = 0; i < 3; i++) {
            linha.add(this.jtCidade.getModel().getValueAt(this.jtCidade.getSelectedRow(), i));
        }
        return linha;
    }
}
