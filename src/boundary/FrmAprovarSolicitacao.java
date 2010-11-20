package boundary;

import control.ControladoraSolicitacao;
import java.awt.Cursor;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrmAprovarSolicitacao extends javax.swing.JFrame {

    ControladoraSolicitacao controladora;
    Vector usuario;

    public FrmAprovarSolicitacao(Vector usuario,String servidor) {
        initComponents();
        controladora = new ControladoraSolicitacao(servidor);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.usuario = usuario;
        btAprovar.setEnabled(false);
        btRejeitar.setEnabled(false);
        preencherTabelaSolicitacoes();
        centralizarConteudoTabela();
    }

    private void centralizarConteudoTabela() {
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        tbSolicitacoes.getColumnModel().getColumn(1).setCellRenderer(centralizado);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbSolicitacoes = new javax.swing.JTable();
        btAprovar = new javax.swing.JButton();
        btFechar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbItensSolicitacao = new javax.swing.JTable();
        btRejeitar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Aprovar Solicitação");
        setResizable(false);

        tbSolicitacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Solicitante", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSolicitacoes.getTableHeader().setReorderingAllowed(false);
        tbSolicitacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSolicitacoesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSolicitacoes);
        tbSolicitacoes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbSolicitacoes.getColumnModel().getColumn(0).setMinWidth(50);
        tbSolicitacoes.getColumnModel().getColumn(0).setMaxWidth(50);
        tbSolicitacoes.getColumnModel().getColumn(1).setResizable(false);
        tbSolicitacoes.getColumnModel().getColumn(2).setMinWidth(60);
        tbSolicitacoes.getColumnModel().getColumn(2).setMaxWidth(60);

        btAprovar.setText("Aprovar");
        btAprovar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAprovarMouseClicked(evt);
            }
        });

        btFechar.setText("Fechar");
        btFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btFecharMouseClicked(evt);
            }
        });

        tbItensSolicitacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "SIAFI", "Descrição", "Unidade", "Qtd Requisitada", "Qtd Aprovada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbItensSolicitacao.setColumnSelectionAllowed(true);
        tbItensSolicitacao.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbItensSolicitacao);
        tbItensSolicitacao.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tbItensSolicitacao.getColumnModel().getColumn(0).setMinWidth(50);
        tbItensSolicitacao.getColumnModel().getColumn(0).setMaxWidth(50);
        tbItensSolicitacao.getColumnModel().getColumn(1).setMinWidth(65);
        tbItensSolicitacao.getColumnModel().getColumn(1).setMaxWidth(65);
        tbItensSolicitacao.getColumnModel().getColumn(3).setMinWidth(60);
        tbItensSolicitacao.getColumnModel().getColumn(3).setMaxWidth(60);
        tbItensSolicitacao.getColumnModel().getColumn(4).setMinWidth(90);
        tbItensSolicitacao.getColumnModel().getColumn(4).setMaxWidth(90);
        tbItensSolicitacao.getColumnModel().getColumn(5).setMinWidth(80);
        tbItensSolicitacao.getColumnModel().getColumn(5).setMaxWidth(80);

        btRejeitar.setText("Rejeitar");
        btRejeitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btRejeitarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(btRejeitar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(btAprovar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btAprovar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRejeitar)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btFechar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btFecharMouseClicked
        this.dispose();
    }//GEN-LAST:event_btFecharMouseClicked

    private void tbSolicitacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSolicitacoesMouseClicked
        int linhaSelecionada = this.tbSolicitacoes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            this.btAprovar.setEnabled(true);
            this.btRejeitar.setEnabled(true);
            if (linhaSelecionada != this.controladora.getMarcador()) {
                this.limparTabelaItensSolicitacao();
                this.preencherTabelaItensSolicitacao(linhaSelecionada);
            }
        }
    }//GEN-LAST:event_tbSolicitacoesMouseClicked

    private void btAprovarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAprovarMouseClicked
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DefaultTableModel dt = (DefaultTableModel) this.tbSolicitacoes.getModel();
        Vector linha = new Vector();
        for (int i = 0; i < 3; i++) {
            linha.add(dt.getValueAt(this.tbSolicitacoes.getSelectedRow(), i));
        }
        DefaultTableModel dtIs = (DefaultTableModel) this.tbItensSolicitacao.getModel();
        Vector qnt = new Vector();
        for (int i = 0; i < dtIs.getRowCount(); i++) {
            qnt.add(dtIs.getValueAt(i, 5));
        }
        if (controladora.aprovarSolicitacao(linha, qnt)) {
            JOptionPane.showMessageDialog(null, "Solicitação Aprovada com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dt.removeRow(this.tbSolicitacoes.getSelectedRow());
            this.tbSolicitacoes.updateUI();
            this.btAprovar.setEnabled(false);
            this.btRejeitar.setEnabled(false);
            this.limparTabelaItensSolicitacao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao aprovar Solicitacao !", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btAprovarMouseClicked

    private void btRejeitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRejeitarMouseClicked
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DefaultTableModel dt = (DefaultTableModel) this.tbSolicitacoes.getModel();
        Vector linha = new Vector();
        for (int i = 0; i < 3; i++) {
            linha.add(dt.getValueAt(this.tbSolicitacoes.getSelectedRow(), i));
        }
        if (this.controladora.rejeitarSolicitacao(linha)) {
            JOptionPane.showMessageDialog(null, "Solicitação Rejeitada com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dt.removeRow(this.tbSolicitacoes.getSelectedRow());
            this.tbSolicitacoes.updateUI();
            this.btAprovar.setEnabled(false);
            this.btRejeitar.setEnabled(false);
            this.limparTabelaItensSolicitacao();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao aprovar Solicitacao !", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btRejeitarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAprovar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btRejeitar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbItensSolicitacao;
    private javax.swing.JTable tbSolicitacoes;
    // End of variables declaration//GEN-END:variables

    private void preencherTabelaSolicitacoes() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            Vector linhas;
            linhas = controladora.obter(this.usuario);
            DefaultTableModel modelo = (DefaultTableModel) this.tbSolicitacoes.getModel();
            int numLinhas = linhas.size();
            for (int i = 0; i < numLinhas; i++) {
                modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));
            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void preencherTabelaItensSolicitacao(int index) {
        try {
            Vector linhas;
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            linhas = controladora.obterItensSolicitacao(index);
            DefaultTableModel modelo = (DefaultTableModel) this.tbItensSolicitacao.getModel();
            for (int i = 0; i < linhas.size(); i++) {
                modelo.insertRow(modelo.getRowCount(), (Vector) linhas.get(i));

            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }

    }

    private void limparTabelaItensSolicitacao() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbItensSolicitacao.getModel();
        int numLinhas = tbItensSolicitacao.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }
}
