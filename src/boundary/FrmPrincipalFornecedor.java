package boundary;

import control.ControladoraFornecedor;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmPrincipalFornecedor extends javax.swing.JFrame implements WindowListener {

    private ControladoraFornecedor controladora;
    private Vector responsavel;
    private String servidor;

    public FrmPrincipalFornecedor(Vector responsavel, String servidor) {
        initComponents();
        this.adicionarMap();
        this.responsavel = responsavel;
        this.setLocationRelativeTo(null);
        this.controladora = new ControladoraFornecedor(servidor);
        this.btAtivar.setEnabled(false);
        this.servidor = servidor;
        preencherTabela();
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector(2);
        for (int i = 0; i < 14; i++) {
            linha.add(this.tbFornecedores.getModel().getValueAt(this.tbFornecedores.getSelectedRow(), i));
        }
        return linha;
    }

    private void preencherTabela() {
        Vector linhas;
        linhas = controladora.obterLinhas("" + this.tfPesquisar.getText(), cbSatus.getSelectedItem().toString());
        DefaultTableModel modelo = (DefaultTableModel) this.tbFornecedores.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void preencherTabela(Vector linhas) {
        DefaultTableModel modelo = (DefaultTableModel) this.tbFornecedores.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
        }
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbFornecedores.getModel();
        int numLinhas = tbFornecedores.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFornecedores = new javax.swing.JTable();
        btInserir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btAtivar = new javax.swing.JButton();
        btDesativar = new javax.swing.JButton();
        cbSatus = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Listar Fornecedores");
        setResizable(false);

        jLabel1.setText("Pesquisar:");

        tbFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CPF/CNPJ", "Nome", "Nome Fantasia", "Cep", "Tipo de Endereco", "Endereco", "Uf", "Cidade", "Bairro", "Complemento", "Telefone", "Telefone", "Email", "Fax"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbFornecedores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbFornecedores);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSatus, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btInserir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAtivar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDesativar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(btFechar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cbSatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btFecharMouseClicked
        this.dispose();
}//GEN-LAST:event_btFecharMouseClicked

    private void btPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPesquisarMouseClicked
        this.limparTabela();
        this.preencherTabela(this.controladora.pesquisar(this.tfPesquisar.getText(), this.cbSatus.getSelectedItem().toString()));
    }//GEN-LAST:event_btPesquisarMouseClicked

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

    private void btInserirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btInserirMouseClicked
        JDialog janela = new FrmInserirFornecedor(this.controladora, servidor);
        janela.setVisible(true);
        this.limparTabela();
        this.preencherTabela();
    }//GEN-LAST:event_btInserirMouseClicked

    private void btAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterarMouseClicked
        int linhaSelecionada = this.tbFornecedores.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um Fornecedor !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JDialog janela = new FrmAlterarFornecedor(controladora, criarLinhaSelecao(), cbSatus.getSelectedItem().toString(), servidor);
            janela.setModal(true);
            janela.setVisible(true);
            limparTabela();
            preencherTabela();
        }
    }//GEN-LAST:event_btAlterarMouseClicked

    private void btAtivarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAtivarMouseClicked
        JDialog janela = new FrmAtivarFornecedor(controladora, criarLinhaSelecao(), responsavel);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_btAtivarMouseClicked

    private void btDesativarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btDesativarMouseClicked
        JDialog janela = new FrmDesativarFornecedor(controladora, criarLinhaSelecao(), responsavel);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_btDesativarMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAtivar;
    private javax.swing.JButton btDesativar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btInserir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbSatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbFornecedores;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
        limparTabela();
        preencherTabela();
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
