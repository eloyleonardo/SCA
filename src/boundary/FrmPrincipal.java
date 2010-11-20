package boundary;

import control.ControladoraRelatorios;
import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import util.ObterDadosEvent;
import util.ObterDadosListener;

public class FrmPrincipal extends javax.swing.JFrame implements WindowListener, ObterDadosListener {

    private Vector usuario;
    private int janelasAbertas;
    private ControladoraRelatorios controladoraRelatorios;
    private Date dataI,  dataF;
    private int codSetor;
    private String classe;

    public FrmPrincipal(Vector usuario) {
        initComponents();
        this.usuario = usuario;
        this.setarPermissoes();
        janelasAbertas = 0;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        controladoraRelatorios = new ControladoraRelatorios();
        FrmEstoqueAbaixoMinimo j = new FrmEstoqueAbaixoMinimo();
        if (j.getMateriais().size() == 0) {
            j.dispose();
        } else {
            j.setVisible(true);
            j.requestFocus();
        }
    }

    private void exibirBoletim() {
        selecionarData();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (dataF != null) {
            this.controladoraRelatorios.getBoletim(this.dataI, this.dataF);
        }
        this.dataF = null;
        this.dataI = null;
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void exibirLog() {
        selecionarClasseLog();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (this.classe != null) {
            this.controladoraRelatorios.getLog(this.classe);
        }
        this.classe = null;
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void exibirRCM() {
        selecionarDataESetor();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (dataF != null) {
            this.controladoraRelatorios.getRCM(this.dataI, this.dataF, this.codSetor);
        }
        this.setCursor(Cursor.getDefaultCursor());
        this.dataF = null;
        this.dataI = null;
        this.codSetor = 0;
    }

    private void exibirRME() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.controladoraRelatorios.getRME();
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void registrarSaida() {
        JFrame janela = new FrmSaidaPrincipal(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }

    private void selecionarClasseLog() {
        FrmSelecionarClasseLog cl = new FrmSelecionarClasseLog();
        cl.addListener(this);
        cl.setVisible(true);
    }

    private void selecionarData() {
        FrmSelecionarData j = new FrmSelecionarData();
        j.addListener(this);
        j.setVisible(true);

    }

    private void selecionarDataESetor() {
        FrmSelecionarDataESetor j = new FrmSelecionarDataESetor();
        j.addListener(this);
        j.setVisible(true);
    }

    private void exibirRMM() {
        selecionarData();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (dataF != null) {
            this.controladoraRelatorios.getRMM(this.dataI, this.dataF);
        }
        this.dataF = null;
        this.dataI = null;
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void fechar() {
        if (janelasAbertas != 0) {
            int r = JOptionPane.showConfirmDialog(null, "Existem janelas abertas,\n deseja mesmo sair ?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == 0) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btCadastrarFornecedor = new javax.swing.JButton();
        btSaida = new javax.swing.JButton();
        btEntrada = new javax.swing.JButton();
        btBoletim = new javax.swing.JButton();
        btRmm = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmCadastros = new javax.swing.JMenu();
        jmiCargo = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jmiUf = new javax.swing.JMenuItem();
        jmiUnidade = new javax.swing.JMenuItem();
        jmiUsuario = new javax.swing.JMenuItem();
        jmRelatorios = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jmEntrada = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jmSaida = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jmSolicitacoes = new javax.swing.JMenu();
        jmiAprovarSolicitacao = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jmSair = new javax.swing.JMenu();
        jmiFechar = new javax.swing.JMenuItem();
        jmiSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA - Módulo Principal Almoxarifado");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/SCA-Logo_03.png"))); // NOI18N
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);

        btCadastrarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/male-user.png"))); // NOI18N
        btCadastrarFornecedor.setToolTipText("Cadastro de Fornecedor");
        btCadastrarFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCadastrarFornecedorMouseClicked(evt);
            }
        });

        btSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/container.png"))); // NOI18N
        btSaida.setToolTipText("Saída de Materiais");
        btSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaidaActionPerformed(evt);
            }
        });

        btEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/van.png"))); // NOI18N
        btEntrada.setToolTipText("Entrada de Materiais");
        btEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntradaActionPerformed(evt);
            }
        });

        btBoletim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar-chart.png"))); // NOI18N
        btBoletim.setToolTipText("Boletim");
        btBoletim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBoletimActionPerformed(evt);
            }
        });

        btRmm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pie-chart.png"))); // NOI18N
        btRmm.setToolTipText("RMM");
        btRmm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRmmActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit.png"))); // NOI18N
        jButton6.setToolTipText("Sair do Sistema");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        jButton7.setToolTipText("Fechar o Sistema");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btCadastrarFornecedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btBoletim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRmm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(933, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btCadastrarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btBoletim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btRmm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jmCadastros.setText("Cadastos/Alterações");

        jmiCargo.setText("Cargo");
        jmiCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCargoActionPerformed(evt);
            }
        });
        jmCadastros.add(jmiCargo);

        jMenuItem6.setText("Cidade");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jmCadastros.add(jMenuItem6);

        jMenuItem3.setText("Fornecedor");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jmCadastros.add(jMenuItem3);

        jMenuItem11.setText("Material");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jmCadastros.add(jMenuItem11);

        jMenuItem4.setText("Setor");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jmCadastros.add(jMenuItem4);

        jmiUf.setText("Uf");
        jmiUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUfActionPerformed(evt);
            }
        });
        jmCadastros.add(jmiUf);

        jmiUnidade.setText("Unidade");
        jmiUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUnidadeActionPerformed(evt);
            }
        });
        jmCadastros.add(jmiUnidade);

        jmiUsuario.setText("Usuario");
        jmiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUsuarioActionPerformed(evt);
            }
        });
        jmCadastros.add(jmiUsuario);

        jMenuBar1.add(jmCadastros);

        jmRelatorios.setText("Relatórios");

        jMenuItem9.setText("RCM");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jmRelatorios.add(jMenuItem9);

        jMenuItem12.setText("RME");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jmRelatorios.add(jMenuItem12);

        jMenuItem8.setText("Boletim");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jmRelatorios.add(jMenuItem8);

        jMenuItem7.setText("RMM");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jmRelatorios.add(jMenuItem7);

        jMenuItem23.setText("Logs do Sistema");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jmRelatorios.add(jMenuItem23);

        jMenuBar1.add(jmRelatorios);

        jmEntrada.setText("Entrada");

        jMenuItem16.setText("Realizar");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jmEntrada.add(jMenuItem16);

        jMenuItem17.setText("Visualizar");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jmEntrada.add(jMenuItem17);

        jMenuBar1.add(jmEntrada);

        jmSaida.setText("Saída");

        jMenuItem18.setText("Realizar");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jmSaida.add(jMenuItem18);

        jMenuItem19.setText("Visualizar");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jmSaida.add(jMenuItem19);

        jMenuBar1.add(jmSaida);

        jMenu1.setText("Estoque");

        jMenuItem1.setText("Relatorio de Materiais em Estoque");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Visualizar Materiais abaixo do estoque");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jmSolicitacoes.setText("Solicitações");

        jmiAprovarSolicitacao.setText("Aprovar");
        jmiAprovarSolicitacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAprovarSolicitacaoActionPerformed(evt);
            }
        });
        jmSolicitacoes.add(jmiAprovarSolicitacao);

        jMenuItem15.setText("Realizar");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jmSolicitacoes.add(jMenuItem15);

        jMenuBar1.add(jmSolicitacoes);
        jmSair.setText("Sair");

        jmiFechar.setText("Fechar");
        jmiFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFecharActionPerformed(evt);
            }
        });
        jmSair.add(jmiFechar);

        jmiSair.setText("Sair");
        jmiSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSairActionPerformed(evt);
            }
        });
        jmSair.add(jmiSair);

        jMenuBar1.add(jmSair);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        this.sair();
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        this.fechar();
    }//GEN-LAST:event_jButton7MouseClicked

    private void jmiFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFecharActionPerformed
        this.fechar();
    }//GEN-LAST:event_jmiFecharActionPerformed

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        this.sair();
    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCargoActionPerformed
        JFrame janela = new FrmPrincipalCargo(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jmiCargoActionPerformed

    private void jmiUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUnidadeActionPerformed
        JFrame janela = new FrmPrincipalUnidade(this.usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jmiUnidadeActionPerformed

    private void jmiUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUfActionPerformed
        JFrame janela = new FrmPrincipalUf(this.usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jmiUfActionPerformed

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked
        this.cadastrarFornecedor();
    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void btCadastrarFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCadastrarFornecedorMouseClicked
        this.cadastrarFornecedor();
    }//GEN-LAST:event_btCadastrarFornecedorMouseClicked

    private void jmiAprovarSolicitacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAprovarSolicitacaoActionPerformed
        JFrame janela = new FrmAprovarSolicitacao(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jmiAprovarSolicitacaoActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JFrame janela = new FrmPrincipalSetor(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        this.exibirBoletim();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.exibirRMM();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        this.registrarEntrada();
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        this.registrarSaida();
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        JFrame janela = new FrmSolicitarMaterial(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void btEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntradaActionPerformed
        this.registrarEntrada();
    }//GEN-LAST:event_btEntradaActionPerformed

    private void btSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaidaActionPerformed
        this.registrarSaida();
    }//GEN-LAST:event_btSaidaActionPerformed

    private void btBoletimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBoletimActionPerformed
        this.exibirBoletim();
    }//GEN-LAST:event_btBoletimActionPerformed

    private void btRmmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRmmActionPerformed
        this.exibirRMM();
    }//GEN-LAST:event_btRmmActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JFrame janela = new FrmPrincipalCidade(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        this.exibirRCM();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        this.exibirRME();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        this.exibirLog();
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.cadastrarFornecedor();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jmiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUsuarioActionPerformed
        JFrame j = new FrmPrincipalUsuario(usuario);
        j.addWindowListener(this);
        j.setVisible(true);
    }//GEN-LAST:event_jmiUsuarioActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        this.visualizarEntradas();
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        JFrame janela = new FrmPrincipalMaterial(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        JFrame j = new FrmVisuzalizarSaida();
        j.addWindowListener(this);
        j.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.exibirRME();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame j = new FrmEstoqueAbaixoMinimo();
        j.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBoletim;
    private javax.swing.JButton btCadastrarFornecedor;
    private javax.swing.JButton btEntrada;
    private javax.swing.JButton btRmm;
    private javax.swing.JButton btSaida;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu jmCadastros;
    private javax.swing.JMenu jmEntrada;
    private javax.swing.JMenu jmRelatorios;
    private javax.swing.JMenu jmSaida;
    private javax.swing.JMenu jmSair;
    private javax.swing.JMenu jmSolicitacoes;
    private javax.swing.JMenuItem jmiAprovarSolicitacao;
    private javax.swing.JMenuItem jmiCargo;
    private javax.swing.JMenuItem jmiFechar;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JMenuItem jmiUf;
    private javax.swing.JMenuItem jmiUnidade;
    private javax.swing.JMenuItem jmiUsuario;
    // End of variables declaration//GEN-END:variables

    private void registrarEntrada() {
        JFrame janela = new FrmEntradaMaterial(usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }

    private void sair() {
        if (janelasAbertas == 0) {
            this.dispose();
            JFrame janela = new FrmLogin();
            janela.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Feche as outras janelas abertas primeiro !", "Feche as Janelas", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setarPermissoes() {
        this.tirarTodasPermissoes();
        if (usuario.get(2) != null) {
            char[] permissoes = usuario.get(2).toString().toCharArray();
            for (int i = 0; i < permissoes.length; i++) {
                if (permissoes[i] == 'a') {
                    this.jmCadastros.setEnabled(true);
                    this.btCadastrarFornecedor.setEnabled(true);
                    this.jmiUsuario.setEnabled(false);
                } else if (permissoes[i] == 'b') {
                    this.jmiUsuario.setEnabled(true);
                } else if (permissoes[i] == 'c') {
                    this.jmEntrada.setEnabled(true);
                    this.btEntrada.setEnabled(true);
                } else if (permissoes[i] == 'd') {
                    this.jmSaida.setEnabled(true);
                    this.btSaida.setEnabled(true);
                    this.jmiAprovarSolicitacao.setEnabled(true);
                } else if (permissoes[i] == 'e') {
                    this.jmRelatorios.setEnabled(true);
                    this.btSaida.setEnabled(true);
                    this.btBoletim.setEnabled(true);
                    this.btRmm.setEnabled(true);
                }
            }
        } else if (Boolean.parseBoolean(usuario.get(9).toString())) {
            this.jmiAprovarSolicitacao.setEnabled(true);
        }
    }

    private void tirarTodasPermissoes() {
        this.jmCadastros.setEnabled(false);
        this.jmRelatorios.setEnabled(false);
        this.jmEntrada.setEnabled(false);
        this.jmSaida.setEnabled(false);
        this.jmiAprovarSolicitacao.setEnabled(false);
        this.btCadastrarFornecedor.setEnabled(false);
        this.btEntrada.setEnabled(false);
        this.btSaida.setEnabled(false);
        this.btBoletim.setEnabled(false);
        this.btRmm.setEnabled(false);
    }

    public void windowOpened(WindowEvent e) {
        janelasAbertas++;
    }

    public void windowClosed(WindowEvent e) {
        janelasAbertas--;
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    private void cadastrarFornecedor() {
        JFrame janela = new FrmPrincipalFornecedor(this.usuario);
        janela.addWindowListener(this);
        janela.setVisible(true);
    }

    public void dadosSelecionados(ObterDadosEvent e) {
        this.codSetor = e.getCodSetor();
        this.dataI = e.getDataI();
        this.dataF = e.getDataF();
        this.classe = e.getNome();
    }

    private void visualizarEntradas() {
        JFrame j = new FrmVisuzalizarEntrada();
        j.addWindowListener(this);
        j.setVisible(true);
    }

    @Override
    public void dispose() {
        this.fechar();
    }
}
