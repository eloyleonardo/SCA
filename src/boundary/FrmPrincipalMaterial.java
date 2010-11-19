package boundary;

import control.ControladoraMaterial;


import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmPrincipalMaterial extends javax.swing.JFrame {

    ControladoraMaterial controladora;
    Vector responsavel;

    public FrmPrincipalMaterial(Vector usuario) {
        controladora = new ControladoraMaterial();
        initComponents();
        preencherTabela();
        this.responsavel = usuario;
        this.btAtivarMat.setEnabled(false);
        adicionarMap();
        this.setLocationRelativeTo(null);
    }

    private FrmPrincipalMaterial() {
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void preencherTabela() {
        Vector linhas;
        linhas = controladora.obterLinhas(this.tfPesquisar.getText(), cbAtivoInativo.getSelectedItem().toString());
        DefaultTableModel modelo = (DefaultTableModel) this.tbMateriais.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbMateriais.getModel();
        int numLinhas = tbMateriais.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector();
        int i;
        for (i = 0; i < tbMateriais.getColumnCount(); i++) {
            linha.add(this.tbMateriais.getModel().getValueAt(this.tbMateriais.getSelectedRow(), i));
        }
        return linha;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbAtivoInativo = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMateriais = new javax.swing.JTable();
        bt_inserirMat = new javax.swing.JButton();
        btAlterarMat = new javax.swing.JButton();
        btAtivarMat = new javax.swing.JButton();
        btDesativarMat = new javax.swing.JButton();
        btFecharMat = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Materiais");
        setResizable(false);

        jLabel1.setText("Descrição");

        tfPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPesquisarActionPerformed(evt);
            }
        });

        jLabel2.setText("Listar");

        cbAtivoInativo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo" }));
        cbAtivoInativo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbAtivoInativoItemStateChanged(evt);
            }
        });

        tbMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "QTD Minima", "ND", "Subitem", "Unidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbMateriais);
        tbMateriais.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbMateriais.getColumnModel().getColumn(3).setPreferredWidth(50);

        bt_inserirMat.setText("Inserir");
        bt_inserirMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_inserirMatMouseClicked(evt);
            }
        });

        btAlterarMat.setText("Alterar");
        btAlterarMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAlterarMatMouseClicked(evt);
            }
        });

        btAtivarMat.setText("Ativar");
        btAtivarMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAtivarMatMouseClicked(evt);
            }
        });

        btDesativarMat.setText("Desativar");
        btDesativarMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btDesativarMatMouseClicked(evt);
            }
        });

        btFecharMat.setText("Fechar");
        btFecharMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btFecharMatMouseClicked(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbAtivoInativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bt_inserirMat, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(btAlterarMat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(btAtivarMat, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(btDesativarMat, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(btFecharMat, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbAtivoInativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bt_inserirMat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterarMat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btAtivarMat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDesativarMat)
                        .addGap(55, 55, 55)
                        .addComponent(btFecharMat))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPesquisarActionPerformed
}//GEN-LAST:event_tfPesquisarActionPerformed

    private void bt_inserirMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_inserirMatMouseClicked
        JDialog janela = new FrmInserirMaterial(controladora);
        janela.setModal(true);
        janela.setVisible(true);
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_bt_inserirMatMouseClicked

    private void btFecharMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btFecharMatMouseClicked
        this.dispose();
    }//GEN-LAST:event_btFecharMatMouseClicked

    private void btAlterarMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterarMatMouseClicked
        int linhaSelecionada = this.tbMateriais.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um Material !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmAlterarMaterial(controladora, criarLinhaSelecao(), cbAtivoInativo.getSelectedItem().toString());
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
    }//GEN-LAST:event_btAlterarMatMouseClicked

    private void btPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPesquisarMouseClicked
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_btPesquisarMouseClicked

    private void btAtivarMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAtivarMatMouseClicked
        int quant = this.tbMateriais.getColumnCount();
        int linha_selecionada = this.tbMateriais.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmAtivarMaterial(controladora, responsavel, linha);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
    }//GEN-LAST:event_btAtivarMatMouseClicked

    private void cbAtivoInativoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAtivoInativoItemStateChanged
        if (cbAtivoInativo.getSelectedIndex() == 0) {
            btDesativarMat.setEnabled(true);
            btAtivarMat.setEnabled(false);
        } else {
            btAtivarMat.setEnabled(true);
            btDesativarMat.setEnabled(false);
        }
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_cbAtivoInativoItemStateChanged

    private void btDesativarMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btDesativarMatMouseClicked
        int quant = this.tbMateriais.getColumnCount();
        int linha_selecionada = this.tbMateriais.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmDesativarMaterial(controladora, responsavel, linha);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
    }//GEN-LAST:event_btDesativarMatMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btAlterarMat;
    protected javax.swing.JButton btAtivarMat;
    protected javax.swing.JButton btDesativarMat;
    protected javax.swing.JButton btFecharMat;
    protected javax.swing.JButton btPesquisar;
    protected javax.swing.JButton bt_inserirMat;
    protected javax.swing.JComboBox cbAtivoInativo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMateriais;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
