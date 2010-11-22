package boundary;

import control.ControladoraMaterial;
import control.ControladoraRelatorios;
import control.ControladoraSaida;
import control.ControladoraSolicitacao;
import control.ControladoraTipoSaida;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmSaidaMaterialOutros extends javax.swing.JFrame {

    private ControladoraSolicitacao controladoraSolicitacao;
    private ControladoraTipoSaida controladoraTipoSaida;
    private ControladoraMaterial controladoraMaterial;
    private ControladoraSaida controladoraSaida;
    private Vector usuario;
    private String servidor;
    private Vector linhasTipoSaida = new Vector();

    public FrmSaidaMaterialOutros(Vector usuario, String servidor) {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/SCA-Logo_4.png")).getImage());
        this.usuario = usuario;
        controladoraSaida = new ControladoraSaida(servidor);
        controladoraMaterial = new ControladoraMaterial(servidor);
        controladoraTipoSaida = new ControladoraTipoSaida(servidor);
        controladoraSolicitacao = new ControladoraSolicitacao(servidor);
        this.lbResponsavel.setText("Responsável: " + usuario.get(1));
        this.lbData.setText("Data: " + new Date());
        this.adicionarMap();
        linhasTipoSaida = controladoraTipoSaida.obterLinhasTipoSaida();
        for (int i = 1; i < linhasTipoSaida.size(); i++) {
            cbTipoSaida.addItem(((Vector) linhasTipoSaida.get(i)).get(1));
        }
        preencherTabela();
        this.setLocationRelativeTo(null);
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void preencherTabela() {
        Vector linhas;
        linhas = controladoraMaterial.obterLinhas(this.tfPesquisar.getText(), "Ativo");
        DefaultTableModel modelo = (DefaultTableModel) this.jtMateriais.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            Vector linha = new Vector();
            String s;
            if (((Vector) linhas.get(i)).get(4).toString().length() == 1) {
                s = "0" + ((Vector) linhas.get(i)).get(4).toString();
            } else {
                s = ((Vector) linhas.get(i)).get(4).toString();
            }
            String siafi = ((Vector) linhas.get(i)).get(3).toString() + s;
            linha.add(((Vector) linhas.get(i)).get(0));
            linha.add(((Vector) linhas.get(i)).get(1));
            linha.add(siafi);
            linha.add(((Vector) linhas.get(i)).get(5));
            linha.add(((Vector) linhas.get(i)).get(6));
            linha.add(((Vector) linhas.get(i)).get(2));
            modelo.insertRow(modelo.getRowCount(), linha);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        cbTipoSaida = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtMateriais = new javax.swing.JTable();
        btAdicionar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSaida = new javax.swing.JTable();
        btRemover = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        lbResponsavel = new javax.swing.JLabel();
        lbData = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taObs = new javax.swing.JTextArea();
        btCancelar = new javax.swing.JButton();
        btOK = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Saida de Material por Outros Motivos");
        setResizable(false);

        cbTipoSaida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Selecione--" }));

        jtMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "SIAFI", "Unidade", "Qnt Estoque", "QntMinimaEstoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtMateriais);
        jtMateriais.getColumnModel().getColumn(0).setMinWidth(50);
        jtMateriais.getColumnModel().getColumn(0).setMaxWidth(50);
        jtMateriais.getColumnModel().getColumn(2).setMinWidth(80);
        jtMateriais.getColumnModel().getColumn(2).setPreferredWidth(80);
        jtMateriais.getColumnModel().getColumn(2).setMaxWidth(80);
        jtMateriais.getColumnModel().getColumn(3).setMinWidth(50);
        jtMateriais.getColumnModel().getColumn(3).setMaxWidth(50);
        jtMateriais.getColumnModel().getColumn(4).setMinWidth(70);
        jtMateriais.getColumnModel().getColumn(4).setMaxWidth(70);
        jtMateriais.getColumnModel().getColumn(5).setMinWidth(70);
        jtMateriais.getColumnModel().getColumn(5).setPreferredWidth(70);
        jtMateriais.getColumnModel().getColumn(5).setMaxWidth(70);

        btAdicionar.setText("Adicionar Material ▼");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        jtSaida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "SIAFI", "Unidade", "Qnt Estoque", "Qnt de Saída", "QntMinima"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtSaida);
        jtSaida.getColumnModel().getColumn(0).setMinWidth(50);
        jtSaida.getColumnModel().getColumn(0).setMaxWidth(50);
        jtSaida.getColumnModel().getColumn(2).setMinWidth(80);
        jtSaida.getColumnModel().getColumn(2).setPreferredWidth(80);
        jtSaida.getColumnModel().getColumn(2).setMaxWidth(80);
        jtSaida.getColumnModel().getColumn(3).setMinWidth(50);
        jtSaida.getColumnModel().getColumn(3).setMaxWidth(50);
        jtSaida.getColumnModel().getColumn(4).setMinWidth(75);
        jtSaida.getColumnModel().getColumn(4).setMaxWidth(75);
        jtSaida.getColumnModel().getColumn(5).setMinWidth(75);
        jtSaida.getColumnModel().getColumn(5).setMaxWidth(75);
        jtSaida.getColumnModel().getColumn(6).setMinWidth(70);
        jtSaida.getColumnModel().getColumn(6).setPreferredWidth(70);
        jtSaida.getColumnModel().getColumn(6).setMaxWidth(70);

        btRemover.setText("Remover Material");
        btRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverActionPerformed(evt);
            }
        });

        jLabel1.setText("Tipo de Saida:");

        jLabel2.setText("Pesquisar:");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        lbResponsavel.setText("Responsável:");

        lbData.setText("Data:");

        taObs.setColumns(20);
        taObs.setRows(5);
        jScrollPane4.setViewportView(taObs);

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btOK.setText("Confirmar");
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });

        jLabel5.setText("Observação:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(btAdicionar)
                .addContainerGap(283, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(478, 478, 478))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addComponent(btRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbData, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btPesquisar)
                        .addGap(66, 66, 66))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btOK)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbData)
                    .addComponent(lbResponsavel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAdicionar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRemover)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOK)
                    .addComponent(btCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) this.jtSaida.getModel();
        Vector linha = this.criarLinhaSelecao();
        Vector insercao = new Vector();
        insercao.add(linha.get(0));
        insercao.add(linha.get(1));
        insercao.add(linha.get(2));
        insercao.add(linha.get(3));
        insercao.add(linha.get(4));
        insercao.add(0);
        insercao.add(linha.get(5));

        insercao.add(0);
        modelo.insertRow(modelo.getRowCount(), (Vector) insercao);
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void btRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) this.jtSaida.getModel();
        modelo.removeRow(this.jtSaida.getSelectedRow());

    }//GEN-LAST:event_btRemoverActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        ControladoraRelatorios controladoraRelatorios = new ControladoraRelatorios(servidor);
        if (cbTipoSaida.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha o tipo de saida!!", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        } else {
            DefaultTableModel modelo = (DefaultTableModel) this.jtSaida.getModel();
            Vector linhaSaida = new Vector();
            boolean saidaOk = true;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Vector linha = new Vector();
                linha.add(this.jtSaida.getModel().getValueAt(i, 0));
                linha.add(this.jtSaida.getModel().getValueAt(i, 5));
                linha.add(this.jtSaida.getModel().getValueAt(i, 4));
                linha.add(this.jtSaida.getModel().getValueAt(i, 6));
                if (Double.parseDouble(this.jtSaida.getModel().getValueAt(i, 5).toString()) > Double.parseDouble(this.jtSaida.getModel().getValueAt(i, 4).toString())) {
                    saidaOk = false;
                } else {
                    linhaSaida.addElement(linha);
                }
            }
            if (saidaOk == true) {
                int codTipoSaida = obterCodTipoSaida();
                String obs = this.taObs.getText();
                controladoraSaida.inserirNovaDSM(Integer.parseInt(usuario.get(0).toString()), codTipoSaida,1);
                int codSaida = controladoraSaida.obterUltimaSaidaInserida();
                linhaSaida.add(codTipoSaida);
                linhaSaida.add(codSaida);
                linhaSaida.add(obs);
                controladoraSaida.registrarSaidaMaterialOutrosMotivos(linhaSaida);
                JOptionPane.showMessageDialog(null, "Saída Registrada!!", "Saída Registrada!!", JOptionPane.INFORMATION_MESSAGE);
                controladoraRelatorios.getDsm(codSaida);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "O número da quantidade deve ser menor que o número no estoque!!", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btOKActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCancelarActionPerformed

    private Vector criarLinhaSelecao() {
        Vector linha = new Vector();
        int i;
        for (i = 0; i < jtMateriais.getColumnCount(); i++) {
            linha.add(this.jtMateriais.getModel().getValueAt(this.jtMateriais.getSelectedRow(), i));
        }
        return linha;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btOK;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btRemover;
    private javax.swing.JComboBox cbTipoSaida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable jtMateriais;
    private javax.swing.JTable jtSaida;
    private javax.swing.JLabel lbData;
    private javax.swing.JLabel lbResponsavel;
    private javax.swing.JTextArea taObs;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.jtMateriais.getModel();
        int numLinhas = jtMateriais.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private int obterCodTipoSaida() {
        int codTipoSaida = 0;
        for (int i = 0; i < linhasTipoSaida.size(); i++) {
            if (cbTipoSaida.getSelectedItem().toString().equals(((Vector) linhasTipoSaida.get(i)).get(1).toString())) {
                codTipoSaida = Integer.parseInt(((Vector) linhasTipoSaida.get(i)).get(0).toString());
                return codTipoSaida;
            }
        }
        return 0;
    }
}
