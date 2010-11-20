
package boundary;

import control.ControladoraEntradaMaterial;
import control.ControladoraRelatorios;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmVisuzalizarEntrada extends javax.swing.JFrame {
    private ControladoraEntradaMaterial controladoraEntrada;
    private ControladoraRelatorios controladoraRelatorio;

    public FrmVisuzalizarEntrada() {
        initComponents();
        this.controladoraEntrada = new ControladoraEntradaMaterial();
        this.controladoraRelatorio = new ControladoraRelatorios();
        this.setLocationRelativeTo(null);
        adicionarMap();
    }

    private void preencherTabelaMaterial() {
        Vector linhas;
        try {
            linhas = this.controladoraEntrada.obterDemEntre(this.jcDataInicio.getDate(), this.jcDataFinal.getDate());
            DefaultTableModel modelo = (DefaultTableModel) this.tbEntrada.getModel();
            int numLinhas = linhas.size();
            for (int i = 0; i < numLinhas; i++) {
                modelo.insertRow(modelo.getRowCount(), (Vector)linhas.get(i));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um Erro,  Contate o Suporte!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void preencherTabelaLote() {
        Vector linhas;
        try {
            int linhaAtual = this.tbEntrada.getSelectedRow();
            int linha = Integer.parseInt(this.tbEntrada.getModel().getValueAt(linhaAtual, 0).toString());
            linhas = this.controladoraEntrada.obterLotesPor(linha);
            DefaultTableModel modelo = (DefaultTableModel) this.tbLote.getModel();
            int numLinhas = linhas.size();
            for (int i = 0; i < numLinhas; i++) {
                modelo.insertRow(modelo.getRowCount(), (Vector)linhas.get(i));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um Erro,  Contate o Suporte!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparTabelaLote() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbLote.getModel();
        int numLinhas = this.tbLote.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }
    private void limparTabelaMaterial() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbEntrada.getModel();
        int numLinhas = this.tbEntrada.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEntrada = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbLote = new javax.swing.JTable();
        btImprimir = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jcDataInicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcDataFinal = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();

        jButton3.setText("Confirmar");

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Imprimir");

        jButton4.setText("Detalhar");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Vizualizar Entrada");

        tbEntrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Número Documento", "Número NE", "Fornecedor", "Tipo de Entrada", "Data Entrada", "Valor Entrada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEntradaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbEntrada);
        tbEntrada.getColumnModel().getColumn(0).setMinWidth(50);
        tbEntrada.getColumnModel().getColumn(0).setMaxWidth(50);
        tbEntrada.getColumnModel().getColumn(1).setMinWidth(125);
        tbEntrada.getColumnModel().getColumn(1).setMaxWidth(125);
        tbEntrada.getColumnModel().getColumn(2).setMinWidth(75);
        tbEntrada.getColumnModel().getColumn(2).setMaxWidth(5);
        tbEntrada.getColumnModel().getColumn(3).setMinWidth(100);
        tbEntrada.getColumnModel().getColumn(3).setMaxWidth(100);
        tbEntrada.getColumnModel().getColumn(4).setMinWidth(100);
        tbEntrada.getColumnModel().getColumn(4).setMaxWidth(100);
        tbEntrada.getColumnModel().getColumn(5).setMinWidth(100);
        tbEntrada.getColumnModel().getColumn(5).setMaxWidth(100);
        tbEntrada.getColumnModel().getColumn(6).setMinWidth(100);
        tbEntrada.getColumnModel().getColumn(6).setMaxWidth(100);

        tbLote.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Material", "Descrição", "Quantidade", "Validade", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbLote);
        tbLote.getColumnModel().getColumn(0).setMinWidth(90);
        tbLote.getColumnModel().getColumn(0).setMaxWidth(90);
        tbLote.getColumnModel().getColumn(2).setMinWidth(75);
        tbLote.getColumnModel().getColumn(2).setMaxWidth(75);
        tbLote.getColumnModel().getColumn(3).setMinWidth(80);
        tbLote.getColumnModel().getColumn(3).setMaxWidth(80);
        tbLote.getColumnModel().getColumn(4).setMinWidth(80);
        tbLote.getColumnModel().getColumn(4).setMaxWidth(80);

        btImprimir.setText("Imprimir");
        btImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btImprimirMouseClicked(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCancelarMouseClicked(evt);
            }
        });

        jLabel1.setText("Data Inicial:");

        jLabel2.setText("Data Final:");

        jButton6.setText("Pesquisar");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btCancelar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btImprimir))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btPesquisar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPesquisar, 0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jcDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcDataFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelar)
                    .addComponent(btImprimir))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void btPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPesquisarMouseClicked
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = formato.format(this.jcDataInicio.getDate());
        String dataFim = formato.format(this.jcDataFinal.getDate());

        if((this.jcDataInicio.getDate() == null) || (this.jcDataFinal.getDate() == null)){
            JOptionPane.showMessageDialog(null, "Preencha as Data Inicial e Final!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }else if((this.jcDataInicio.getDate().after(this.jcDataFinal.getDate()))){
            JOptionPane.showMessageDialog(null, "A data Inicial deve ser anterior a data Final!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }else if(dataInicio.equals(dataFim)){
            JOptionPane.showMessageDialog(null, "A data Inicial não pode ser igual a data Final!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        else{
            limparTabelaMaterial();
            preencherTabelaMaterial();
        }
        
    }//GEN-LAST:event_btPesquisarMouseClicked

    private void tbEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEntradaMouseClicked
        limparTabelaLote();
        preencherTabelaLote();
    }//GEN-LAST:event_tbEntradaMouseClicked

    private void btCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_btCancelarMouseClicked

    private void btImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btImprimirMouseClicked
        int numLinha = this.tbEntrada.getSelectedRow();
        if(numLinha != -1){
            this.controladoraRelatorio.getDEM(Integer.parseInt(this.tbEntrada.getModel().getValueAt(numLinha, 0).toString()));
        }
        else {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um DEM!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btImprimirMouseClicked

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrmVisuzalizarEntrada().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private com.toedter.calendar.JDateChooser jcDataFinal;
    private com.toedter.calendar.JDateChooser jcDataInicio;
    private javax.swing.JTable tbEntrada;
    private javax.swing.JTable tbLote;
    // End of variables declaration//GEN-END:variables

}
