package boundary;

import control.ControladoraUsuario;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmPrincipalUsuario extends javax.swing.JFrame {

    protected ControladoraUsuario controladora;
    private Vector usuario;
    private String servidor;

    public FrmPrincipalUsuario(Vector usuario, String servidor) {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/SCA-Logo_4.png")).getImage());
        this.servidor = servidor;
        this.adicionarMap();
        this.usuario = usuario;
        this.setTitle("Menu de usuários");
        this.controladora = new ControladoraUsuario(servidor);
        this.preencherTabelaUsuarios();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void preencherTabelaUsuarios() {
        Vector linhas;

        if (this.cbStatus.getSelectedIndex() == 0) {
            this.btAtivar.setEnabled(false);
            this.btDesativar.setEnabled(true);
            linhas = controladora.obterLinhas(this.tfNome.getText(), "Ativo");
        } else {
            this.btAtivar.setEnabled(true);
            this.btDesativar.setEnabled(false);
            linhas = controladora.obterLinhas(this.tfNome.getText(), "Desativo");
        }
        DefaultTableModel modelo = (DefaultTableModel) this.jTable1.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }

    }

    private void setarJanelaFormUsuario(FrmFormularioUsuario janela) throws SQLException {
        janela.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        janela.setVisible(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btInserir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btAtivar = new javax.swing.JButton();
        btDesativar = new javax.swing.JButton();
        cbStatus = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nome:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Cargo", "Setor", "Login", "Senha", "Documento", "Permissão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setMinWidth(50);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(50);

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

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo" }));
        cbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStatusItemStateChanged(evt);
            }
        });

        jLabel2.setText("Listar:");

        btFechar.setText("Fechar");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
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
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btInserir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btAtivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btFechar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btDesativar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btPesquisar))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addComponent(btFechar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInserirActionPerformed
        JDialog janela = new FrmInserirUsuario(controladora, this.servidor);
        janela.setModal(true);
        janela.setVisible(true);
        this.limparTabelaUsuarios();
        this.preencherTabelaUsuarios();
    }//GEN-LAST:event_btInserirActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        limparTabelaUsuarios();
        preencherTabelaUsuarios();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void cbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStatusItemStateChanged
        limparTabelaUsuarios();
        preencherTabelaUsuarios();
    }//GEN-LAST:event_cbStatusItemStateChanged

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        int cod;
        int quant = this.jTable1.getColumnCount();
        int linha_selecionada = this.jTable1.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            for (int i = 0; i < quant; i++) {
                linha.add(i, this.jTable1.getModel().getValueAt(linha_selecionada, i));
            }
            JDialog janela = new FrmAlterarUsuario(this.controladora, linha,this.servidor);
            janela.setModal(true);
            janela.setVisible(true);
            this.limparTabelaUsuarios();
            this.preencherTabelaUsuarios();
        }
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btAtivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtivarActionPerformed
        int quant = this.jTable1.getColumnCount();
        int linha_selecionada = this.jTable1.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmAtivarUsuario(linha, usuario, controladora,this.servidor);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabelaUsuarios();
            preencherTabelaUsuarios();
        }
    }//GEN-LAST:event_btAtivarActionPerformed

    private void btDesativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesativarActionPerformed
        int quant = this.jTable1.getColumnCount();
        int linha_selecionada = this.jTable1.getSelectedRow();
        if (linha_selecionada < 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionada uma linha", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.controladora.setMarcador(linha_selecionada);
            Vector linha = new Vector(quant);
            linha = criarLinhaSelecao();
            JDialog janela = new FrmDesativarUsuario(linha, usuario, controladora,this.servidor);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabelaUsuarios();
            preencherTabelaUsuarios();
        }
    }//GEN-LAST:event_btDesativarActionPerformed

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    protected void limparTabelaUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) this.jTable1.getModel();
        int numLinhas = modelo.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector();
        for (int i = 0; i < 8; i++) {
            linha.add(this.jTable1.getModel().getValueAt(this.jTable1.getSelectedRow(), i));
        }
        return linha;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAtivar;
    private javax.swing.JButton btDesativar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btInserir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JComboBox cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
