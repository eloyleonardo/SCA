package boundary;

import control.ControladoraRelatorios;
import control.ControladoraSaida;
import control.ControladoraSetor;
import control.ControladoraSolicitacao;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmSaidaMaterial extends javax.swing.JFrame {

    private int codSaida = 0;
    private ControladoraSolicitacao controladora;
    private ControladoraSetor controladoraSetor;
    private ControladoraSaida controladoraSaida;
    private String servidor;
    private Vector linhasSetor;
    private int req;
    private Vector usuario;

    public FrmSaidaMaterial(Vector usuario, String servidor) throws SQLException {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/SCA-Logo_4.png")).getImage());
        controladoraSaida = new ControladoraSaida(servidor);
        controladoraSetor = new ControladoraSetor(servidor);
        controladora = new ControladoraSolicitacao(servidor);
        this.usuario = usuario;
        this.setTitle("[SCA] - Saída de materiais");
        this.setResizable(false);
        linhasSetor = controladoraSetor.obterLinhas("", "Ativo");
        int i;
        for (i = 0; i < linhasSetor.size(); i++) {
            cbSetor.addItem(((Vector) linhasSetor.get(i)).get(1));
        }
        cbSetor.setSelectedIndex(0);
        preencherTabela("");
        this.setLocationRelativeTo(null);
        this.btImprimir.setEnabled(false);
        this.adicionarMap();
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtSolicitacao = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSaida = new javax.swing.JTable();
        btExibir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbSetor = new javax.swing.JComboBox();
        btCancelar = new javax.swing.JButton();
        btConfirmar = new javax.swing.JButton();
        btImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA - Saída Material Por Solicitação");

        jtSolicitacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Solicitacao", "Setor requisitante", "Data Solicitacao", "Data Aprovacao"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtSolicitacao);

        jtSaida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº do item", "Código Material", "Descrição Material", "Quant. aprovada", "Qnt. Em Estoque", "Estoque Minimo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtSaida);

        btExibir.setText("Exibir Itens da Solicitação");
        btExibir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExibirActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar Requisições por Setor: ");

        cbSetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mostrar Todas as Solicitações" }));
        cbSetor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSetorItemStateChanged(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCancelarMouseClicked(evt);
            }
        });

        btConfirmar.setText("Confirmar Saída");
        btConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarActionPerformed(evt);
            }
        });

        btImprimir.setText("Imprimir DSM");
        btImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btImprimirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(btExibir))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(188, 188, 188)
                            .addComponent(btImprimir)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btCancelar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btExibir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConfirmar)
                    .addComponent(btCancelar)
                    .addComponent(btImprimir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btExibirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExibirActionPerformed
        try {
            preencherTabelaMateriais();
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btExibirActionPerformed

    private void btConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarActionPerformed
        Vector linha = new Vector();
        linha.add(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 1));
        linha.add(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 4));
        linha.add(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 5));
        if (Double.parseDouble(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 3).toString()) > Double.parseDouble(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 4).toString())) {
            JOptionPane.showMessageDialog(null, "O número da quantidade deve ser menor que o número no estoque!!", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (codSaida == 0) {
                controladoraSaida.inserirNovaDSM(Integer.parseInt(usuario.get(0).toString()), 1, Integer.parseInt(jtSolicitacao.getValueAt(jtSolicitacao.getSelectedRow(), 0).toString()));
                codSaida = controladoraSaida.obterUltimaSaidaInserida();
            }
            linha.add(req);
            linha.add(codSaida);
            linha.add(this.jtSaida.getModel().getValueAt(this.jtSaida.getSelectedRow(), 3));
            int numLinhas = jtSaida.getModel().getRowCount();
            controladoraSaida.registrarSaidaMaterial(linha, numLinhas);
            limparTabelaMateriais();
            limparTabelaSolicitacoes();
            this.btImprimir.setEnabled(true);
            this.cbSetor.setSelectedIndex(0);
            try {
                preencherTabela("");
            } catch (SQLException ex) {
            }
        }
    }//GEN-LAST:event_btConfirmarActionPerformed

    private void cbSetorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSetorItemStateChanged
        String nome = new String();
        int i;
        if (cbSetor.getSelectedIndex() == 0) {
            nome = "";
        } else {
            for (i = 0; i < linhasSetor.size(); i++) {
                nome = cbSetor.getSelectedItem().toString();
            }
        }
        try {
            preencherTabela(nome);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cbSetorItemStateChanged

    private void btCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_btCancelarMouseClicked

    private void btImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btImprimirMouseClicked
        ControladoraRelatorios controladoraRelatorio = new ControladoraRelatorios(servidor);
        controladoraRelatorio.getDsm(codSaida);

    }//GEN-LAST:event_btImprimirMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JButton btExibir;
    private javax.swing.JButton btImprimir;
    private javax.swing.JComboBox cbSetor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtSaida;
    private javax.swing.JTable jtSolicitacao;
    // End of variables declaration//GEN-END:variables

    private void preencherTabela(String nome) throws SQLException {
        limparTabelaSolicitacoes();
        Vector linhas;
        int i;
        linhas = controladora.obterSolicitacoesAprovadas(nome);
        DefaultTableModel modelo = (DefaultTableModel) this.jtSolicitacao.getModel();
        int numLinhas = linhas.size();
        for (i = 0; i < numLinhas; i++) {
            Vector linha = new Vector();
            Vector v;
            v = (Vector) linhas.get(i);
            linha.add((v.get(0)));
            linha.add((v.get(3)));
            linha.add((v.get(2)));
            linha.add((v.get(4)));
            modelo.insertRow(modelo.getRowCount(), linha);
        }
    }

    private void preencherTabelaMateriais() throws SQLException {
        limparTabelaMateriais();
        int i;
        Vector linhas;
        DefaultTableModel modelo2 = (DefaultTableModel) this.jtSaida.getModel();
        DefaultTableModel modelo = (DefaultTableModel) this.jtSolicitacao.getModel();
        int numReq = Integer.parseInt(this.jtSolicitacao.getModel().getValueAt(this.jtSolicitacao.getSelectedRow(), 0).toString());
        req = Integer.parseInt(this.jtSolicitacao.getModel().getValueAt(this.jtSolicitacao.getSelectedRow(), 0).toString());
        linhas = controladora.obterMateriaisPorSolicitacao(numReq);
        int numLinhas = linhas.size();
        for (i = 0; i < numLinhas; i++) {
            Vector linhaTabela = new Vector();
            linhaTabela.add(modelo2.getRowCount() + 1);
            linhaTabela.add(((Vector) linhas.get(i)).get(0));
            linhaTabela.add(((Vector) linhas.get(i)).get(1));
            linhaTabela.add(((Vector) linhas.get(i)).get(2));
            linhaTabela.add(((Vector) linhas.get(i)).get(3));
            linhaTabela.add(((Vector) linhas.get(i)).get(4));
            modelo2.insertRow(modelo2.getRowCount(), (Vector) linhaTabela);
        }
    }

    private void limparTabelaMateriais() {
        int i;
        DefaultTableModel modelo = (DefaultTableModel) this.jtSaida.getModel();
        int numLinhas = jtSaida.getRowCount();
        for (i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private void limparTabelaSolicitacoes() {
        int i;
        DefaultTableModel modelo = (DefaultTableModel) this.jtSolicitacao.getModel();
        int numLinhas = jtSolicitacao.getRowCount();
        for (i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }
}
