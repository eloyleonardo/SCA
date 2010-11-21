package boundary;

import control.ControladoraMaterial;
import control.ControladoraSetor;
import control.ControladoraSolicitacao;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmSolicitarMaterial extends javax.swing.JFrame {

    private ControladoraMaterial controladoraMaterial;
    private ControladoraSolicitacao controladoraSolicitacao;
    private Vector responsavel;
    private Date data = new Date();
    private String servidor;
    private ControladoraSetor controladoraSetor;

    public FrmSolicitarMaterial(Vector usuario, String servidor) {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/SCA-Logo_4.png")).getImage());
        controladoraSolicitacao = new ControladoraSolicitacao(servidor);
        controladoraSetor = new ControladoraSetor(servidor);
        this.responsavel = usuario;
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        this.adicionarMap();
        controladoraMaterial = new ControladoraMaterial(servidor);
        this.setVisible(true);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        preencherTabela();
        this.lbRequisitante.setText("Requisitante: " + this.responsavel.get(1).toString());
        this.lbData.setText("Data: " + formatarDate.format(data));
        this.lbSetor.setText("Setor: " + responsavel.get(11));
        this.setLocationRelativeTo(null);
        this.servidor = servidor;
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector();
        int i;
        for (i = 0; i < tbMateriais.getColumnCount(); i++) {
            linha.add(this.tbMateriais.getModel().getValueAt(this.tbMateriais.getSelectedRow(), i));
        }
        return linha;
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbMateriais.getModel();
        int numLinhas = tbMateriais.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMateriais = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        jbPesquisar = new javax.swing.JButton();
        jbAdicionar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtRequisicao = new javax.swing.JTable();
        jbRetirar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btConfirmar = new javax.swing.JButton();
        lbRequisitante = new javax.swing.JLabel();
        lbSetor = new javax.swing.JLabel();
        lbData = new javax.swing.JLabel();

        jButton3.setText("jButton3");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setText("Enviar Requisição");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Realizar Solicitação");
        setResizable(false);

        tbMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "SIAFI", "Unidade", "Qnt. Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMateriais.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbMateriais);
        tbMateriais.getColumnModel().getColumn(0).setMinWidth(50);
        tbMateriais.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbMateriais.getColumnModel().getColumn(0).setMaxWidth(50);
        tbMateriais.getColumnModel().getColumn(2).setMinWidth(100);
        tbMateriais.getColumnModel().getColumn(2).setMaxWidth(100);
        tbMateriais.getColumnModel().getColumn(3).setMinWidth(50);
        tbMateriais.getColumnModel().getColumn(3).setMaxWidth(50);
        tbMateriais.getColumnModel().getColumn(4).setMinWidth(80);
        tbMateriais.getColumnModel().getColumn(4).setMaxWidth(80);

        jLabel1.setText("Pesquisar Material:");

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jbAdicionar.setText("Adicionar a Solicitação ▼");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jtRequisicao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Código", "Descrição", "SIAFI", "Qnt. Solicitada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtRequisicao.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jtRequisicao);
        jtRequisicao.getColumnModel().getColumn(0).setMinWidth(50);
        jtRequisicao.getColumnModel().getColumn(0).setMaxWidth(50);
        jtRequisicao.getColumnModel().getColumn(1).setMinWidth(50);
        jtRequisicao.getColumnModel().getColumn(1).setMaxWidth(50);
        jtRequisicao.getColumnModel().getColumn(3).setMinWidth(100);
        jtRequisicao.getColumnModel().getColumn(3).setMaxWidth(100);
        jtRequisicao.getColumnModel().getColumn(4).setMinWidth(80);
        jtRequisicao.getColumnModel().getColumn(4).setMaxWidth(80);

        jbRetirar.setText("Retirar da Solicitação");
        jbRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRetirarActionPerformed(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btConfirmar.setText("Confirmar");
        btConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarActionPerformed(evt);
            }
        });

        lbRequisitante.setText("Requisitante:");

        lbSetor.setText("Setor:");

        lbData.setText("Data:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbRequisitante, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(lbData, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jbAdicionar))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btConfirmar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jbRetirar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(jbPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRequisitante)
                    .addComponent(lbData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRetirar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConfirmar)
                    .addComponent(btCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jbRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRetirarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) this.jtRequisicao.getModel();
        modelo.removeRow(this.jtRequisicao.getSelectedRow());
    }//GEN-LAST:event_jbRetirarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) this.jtRequisicao.getModel();
        Vector linha = this.criarLinhaSelecao();
        Vector insercao = new Vector();
        insercao.add(modelo.getRowCount() + 1);
        insercao.add(linha.get(0));
        insercao.add(linha.get(1));
        insercao.add(linha.get(2));
        modelo.insertRow(modelo.getRowCount(), (Vector) insercao);

    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void btConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarActionPerformed
        ControladoraSolicitacao controSol = new ControladoraSolicitacao(servidor);
        Vector linha = this.criarLinhaSolicitacao();
        Vector linhasSetor = controladoraSetor.obterLinhas("", "Ativo");
        String setorUsuario = "";
        for (int i = 0; i < linhasSetor.size(); i++) {
            if (responsavel.get(11).equals(((Vector) linhasSetor.get(i)).get(1).toString())) {
                setorUsuario = ((Vector) linhasSetor.get(i)).get(0).toString();
                break;
            }
        }
        if (linha != null) {
            try {
                linha.add(responsavel.get(0));
                linha.add(setorUsuario);
                controSol.inserirNovaSolicitacao(linha);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Requisição inserida com sucesso!!", "Requisição cadastrada", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Digite a quantidade de cada material", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btConfirmarActionPerformed

    private void preencherTabela() {
        Vector linhas;
        linhas = controladoraMaterial.obterLinhas(this.tfPesquisar.getText(), "Ativo");
        DefaultTableModel modelo = (DefaultTableModel) this.tbMateriais.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            Vector linha = new Vector();
            String Siafi = ((Vector) linhas.get(i)).get(3).toString() + ((Vector) linhas.get(i)).get(4).toString();
            linha.add(((Vector) linhas.get(i)).get(0));
            linha.add(((Vector) linhas.get(i)).get(1));
            linha.add(Siafi);
            linha.add(((Vector) linhas.get(i)).get(5));
            linha.add(((Vector) linhas.get(i)).get(6));
            linha.add(((Vector) linhas.get(i)).get(2));
            modelo.insertRow(modelo.getRowCount(), linha);
        }
    }

    private Vector criarLinhaSolicitacao() {
        boolean solOk = true;
        Vector solicitar = new Vector();
        int i;
        for (i = 0; i < jtRequisicao.getRowCount(); i++) {
            if (jtRequisicao.getModel().getValueAt(i, 4) != null) {
                Vector linha = new Vector();
                linha.add(this.jtRequisicao.getModel().getValueAt(i, 1).toString());
                linha.add(this.jtRequisicao.getModel().getValueAt(i, 4));
                solicitar.add(linha);
            } else {
                solOk = false;
            }
        }
        if (solOk == true) {
            return solicitar;
        } else {
            return null;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JButton jbRetirar;
    private javax.swing.JTable jtRequisicao;
    private javax.swing.JLabel lbData;
    private javax.swing.JLabel lbRequisitante;
    private javax.swing.JLabel lbSetor;
    private javax.swing.JTable tbMateriais;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
