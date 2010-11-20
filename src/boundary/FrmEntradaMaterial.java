package boundary;

import control.ControladoraEntradaMaterial;
import control.ControladoraFornecedor;
import control.ControladoraMaterial;
import control.ControladoraRelatorios;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import util.ActionFechar;

public class FrmEntradaMaterial extends javax.swing.JFrame {

    private ControladoraEntradaMaterial controladoraEntrada;
    private ControladoraFornecedor controladoraFornecedor;
    private ControladoraMaterial controladoraMaterial;
    private ControladoraRelatorios controladoraRelatorio;
    private Vector tiposEntrada;
    private Vector fornecedores;
    private Vector usuario;
    private Vector entrada;
    private Vector lote;
    private Vector documento;
    private SimpleDateFormat data;
    private int codigo_Entrada;
    private int itemEntrada;

    public FrmEntradaMaterial(Vector usuario) {
        super();
        this.usuario = usuario;
        initComponents();
        Vector controle;
        this.setLocationRelativeTo(null);

        this.controladoraEntrada = new ControladoraEntradaMaterial();
        this.controladoraFornecedor = new ControladoraFornecedor();
        this.controladoraMaterial = new ControladoraMaterial();
        this.controladoraRelatorio = new ControladoraRelatorios();

        this.data = new SimpleDateFormat("dd/MM/yyyy");
        this.lbData.setText(this.data.format(new Date()));
        try {
            this.tiposEntrada = this.controladoraEntrada.ListarTodosTipoEntrada();
        } catch (SQLException ex) {
            Logger.getLogger(FrmEntradaMaterial.class.getName()).log(Level.SEVERE, null, ex);
        }

        int cont = this.tiposEntrada.size();
        for (int i = 0; i < cont; i++) {
            controle = (Vector) this.tiposEntrada.get(i);
            this.cbTipoDem.addItem(controle.get(1).toString());
        }

        this.fornecedores = this.controladoraFornecedor.obterLinhas("", "Ativo");
        cont = this.fornecedores.size();
        for (int i = 0; i < cont; i++) {
            controle = (Vector) this.fornecedores.get(i);
            this.cbFornecedor.addItem(controle.get(1).toString());
        }

        this.itemEntrada = 0;

        this.tbMaterial_01.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tbMaterial_02.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        adicionarMap();
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) this.tbMaterial_01.getModel();
        int numLinhas = this.tbMaterial_01.getRowCount();
        for (int i = 0; i < numLinhas; i++) {
            modelo.removeRow(0);
        }
    }

    private void removerLinha(int cont) {
        DefaultTableModel modelo = (DefaultTableModel) this.tbMaterial_02.getModel();
        modelo.removeRow(cont);
    }

    private Vector attLinha(Vector linha) {
        Vector linhaFinal = new Vector();
        String s;
        if (linha.get(4).toString().length() == 1) {
            s = "0" + linha.get(4).toString();
        } else {
            s = linha.get(4).toString();
        }
        String siafi = linha.get(3).toString() + s;
        linhaFinal.add(linha.get(0));
        linhaFinal.add(linha.get(1));
        linhaFinal.add(linha.get(5));
        linhaFinal.add(siafi);
        linhaFinal.add(linha.get(6));
        return linhaFinal;
    }

    private void preencherTabela() throws Exception {
        Vector linhas;
        linhas = this.controladoraMaterial.obterLinhas(this.tfPesquisar.getText(), "Ativo");

        DefaultTableModel modelo = (DefaultTableModel) this.tbMaterial_01.getModel();
        int numLinhas = linhas.size();
        for (int i = 0; i < numLinhas; i++) {
            modelo.insertRow(modelo.getRowCount(), attLinha((Vector) linhas.get(i)));
        }
    }

    private Vector montarLote() {
        Vector materialAtual = new Vector();
        for (int i = 0; i < this.tbMaterial_02.getModel().getRowCount(); i++) {
            materialAtual.add(this.criarLinhaTabela02(i));
        }

        return materialAtual;
    }

    private boolean montarEntrada() {
        //this.entrada.add(new Date());
        if (validarCampos()) {
            this.entrada = new Vector();
            this.lote = new Vector();

            Vector tipoEntradaCodigo = (Vector) this.tiposEntrada.get(this.cbTipoDem.getSelectedIndex());
            Vector fornecedorCodigo = (Vector) this.fornecedores.get(this.cbFornecedor.getSelectedIndex());
            Vector tipoAtual = (Vector) this.tiposEntrada.get(this.cbTipoDem.getSelectedIndex());

            this.entrada.add(tipoEntradaCodigo.get(0));
            this.entrada.add(fornecedorCodigo.get(0));

            this.entrada.add(this.tfNumeroNota.getText().toString());
            this.entrada.add(this.data.format(this.jcDataNota.getDate()));
            this.entrada.add(this.tfValorNota.getText().toString());
            this.entrada.add(this.tfNumeroEmpenho.getText().toString());
            this.entrada.add(Integer.parseInt(this.usuario.get(0).toString()));

            this.entrada.add(tipoAtual.get(3));
            this.lote = montarLote();
            return true;
        }
        return false;
    }

    private boolean verificaLinha(Vector linha) {
//        Vector linha = new Vector();
//        linha = criarLinhaTabela01(linhaTabela01);
        String codigo = linha.get(0).toString();
        for (int i = 0; i < this.tbMaterial_02.getModel().getRowCount(); i++) {
            if (codigo.equals(this.tbMaterial_02.getModel().getValueAt(i, 0).toString())) {
                return false;
            }
        }
        return true;
    }

    private Vector criarLinhaTabela02(int linhaSelecionada) {
        Vector linha = new Vector();
        for (int i = 0; i < 7; i++) {
            linha.add(this.tbMaterial_02.getModel().getValueAt(linhaSelecionada, i));
        }
        return linha;
    }

    private Vector criarLinhaTabela01(int linhaSelecionada) {
        Vector linha = new Vector();
        for (int i = 0; i < 5; i++) {
            linha.add(this.tbMaterial_01.getModel().getValueAt(linhaSelecionada, i));
        }
        return linha;
    }

    private Vector criarLinhaAdicionar() {
        Vector linha = new Vector();
        for (int i = 0; i < 5; i++) {
            if ((i != 2) && (i != 3)) {
                linha.add(this.tbMaterial_01.getModel().getValueAt(this.tbMaterial_01.getSelectedRow(), i));
            }
        }
        linha.add(null);
        linha.add(null);
        linha.add(null);
        linha.add(this.itemEntrada);
        return linha;
    }

    private Vector criarLinha(Vector material) {
        Vector linha = new Vector();
        linha.add(material.get(0));
        linha.add(material.get(1));
        linha.add(material.get(2));
        linha.add(null);
        linha.add(null);
        linha.add(null);
        linha.add(this.itemEntrada);
        return linha;
    }

    private boolean validarCampos() {
        if (validarNota() && validarData() && validarValorNota() && validarNotaEmpenho() && validarTabela() && validarDataTabela()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarNota() {
        if (!(this.tfNumeroNota.getText().equals("")) && (!this.cbTipoDem.getSelectedItem().equals("Doação"))) {
            return true;
        } else {
            Vector nomeDoc = (Vector) this.tiposEntrada.get(this.cbTipoDem.getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Preencha o Número da " + nomeDoc.get(2) + "", "Atenção", JOptionPane.OK_OPTION);
            return false;
        }
    }

    private boolean validarNotaEmpenho() {
        if (!(this.tfNumeroEmpenho.getText().equals("") && (!this.cbTipoDem.getSelectedItem().equals("Doação")))) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Preencha o Número de Empenho", "Atenção", JOptionPane.OK_OPTION);
            return false;
        }
    }

    private boolean validarValorNota() {
        if (!(this.tfValorNota.getText().equals("") && (!this.cbTipoDem.getSelectedItem().equals("Doação")))) {
            return true;
        } else {
            Vector nomeDoc = (Vector) this.tiposEntrada.get(this.cbTipoDem.getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Preencha o Valor da " + nomeDoc.get(2) + "", "Atenção", JOptionPane.OK_OPTION);
            return false;
        }
    }

    private boolean validarData() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Você deve digitar uma Data valida(DD/MM/AAAA) para a Entrada!", "Atenção", JOptionPane.OK_OPTION);
            return false;
        }
        return true;
    }

    private boolean validarDataTabela() {
        Vector linhaAtual = new Vector();
        Date dataLinha;
        Date dataAtual = new Date();
        for (int i = 0; i < this.tbMaterial_02.getRowCount(); i++) {
            linhaAtual = criarLinhaTabela02(i);
            try {
                String[] data = linhaAtual.get(4).toString().split("/");

                dataLinha = new Date(data[2]+"/"+data[1]+"/"+data[0]);
                if (dataAtual.after(dataLinha)) {
                    JOptionPane.showMessageDialog(null, "Digite uma Data que seja após o dia de hoje para o material: " + linhaAtual.get(1) + "!", "Atenção", JOptionPane.OK_OPTION);
                    return false;
                } else {
                    try {
                        Double.parseDouble(linhaAtual.get(5).toString().replace(',', '.'));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Digite um valor valido para o material: " + linhaAtual.get(1) + "!", "Atenção", JOptionPane.OK_OPTION);
                        return false;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Você deve digitar uma Data valida(DD/MM/AAAA) para o material: " + linhaAtual.get(1) + "!", "Atenção", JOptionPane.OK_OPTION);
                return false;
            }
        }
        return true;
    }

    private boolean validarTabela() {
        if (this.tbMaterial_02.getRowCount() > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Para realizar uma Entrada você deve selecionar ao menos um material!", "Atenção", JOptionPane.OK_OPTION);
            return false;
        }
    }

    private void acertarLinhasTabela(int linha) {
        int numLinha;
        Vector linhas;
        String valor;
        for (int i = linha; i < this.tbMaterial_02.getModel().getRowCount(); i++) {
            linhas = this.criarLinhaTabela02(i);
            valor = linhas.get(6).toString();
            numLinha = Integer.parseInt(valor) - 1;
            this.tbMaterial_02.getModel().setValueAt(numLinha, i, 6);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbFornecedor = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cbTipoDem = new javax.swing.JComboBox();
        lbNumeroDoc = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbDataDoc = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbValorDoc = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMaterial_02 = new javax.swing.JTable();
        bcConfirmar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tfPesquisar = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbMaterial_01 = new javax.swing.JTable();
        btAdicionarMaterial = new javax.swing.JButton();
        btRemoverMaterial = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        tfNumeroNota = new javax.swing.JFormattedTextField();
        tfValorNota = new javax.swing.JFormattedTextField();
        tfNumeroEmpenho = new javax.swing.JFormattedTextField();
        lbData = new javax.swing.JLabel();
        tfCodigoMaterial = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jcDataNota = new com.toedter.calendar.JDateChooser();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Descrição Material", "Unidade", "SIAFI", "Qnt. Estoque"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA - Registrar Entrada de Material");
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        jLabel1.setText("Fornecedor:");

        jLabel2.setText("Tipo de Entrada:");

        cbTipoDem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoDemItemStateChanged(evt);
            }
        });

        lbNumeroDoc.setText("Número");

        lbDataDoc.setText("Data");

        jLabel6.setText("Número Nota de Empenho:");

        lbValorDoc.setText("Valor Total da");

        tbMaterial_02.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição Material", "Qtd Estoque", "Quantidade", "Validade", "Valor Unitario", "Item DEM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbMaterial_02);
        tbMaterial_02.getColumnModel().getColumn(0).setMinWidth(50);
        tbMaterial_02.getColumnModel().getColumn(0).setMaxWidth(50);
        tbMaterial_02.getColumnModel().getColumn(2).setMinWidth(75);
        tbMaterial_02.getColumnModel().getColumn(2).setMaxWidth(75);
        tbMaterial_02.getColumnModel().getColumn(3).setMinWidth(75);
        tbMaterial_02.getColumnModel().getColumn(3).setMaxWidth(75);
        tbMaterial_02.getColumnModel().getColumn(4).setMinWidth(75);
        tbMaterial_02.getColumnModel().getColumn(4).setMaxWidth(75);
        tbMaterial_02.getColumnModel().getColumn(5).setMinWidth(80);
        tbMaterial_02.getColumnModel().getColumn(5).setMaxWidth(80);
        tbMaterial_02.getColumnModel().getColumn(6).setMinWidth(75);
        tbMaterial_02.getColumnModel().getColumn(6).setMaxWidth(75);

        bcConfirmar.setText("Confirmar");
        bcConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bcConfirmarMouseClicked(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCancelarMouseClicked(evt);
            }
        });

        jLabel8.setText("Buscar Materiais:");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btPesquisarMouseClicked(evt);
            }
        });

        tbMaterial_01.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição Material", "Unidade", "SIAFI", "Qnt. Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbMaterial_01);
        tbMaterial_01.getColumnModel().getColumn(0).setMinWidth(50);
        tbMaterial_01.getColumnModel().getColumn(0).setMaxWidth(50);
        tbMaterial_01.getColumnModel().getColumn(2).setMinWidth(80);
        tbMaterial_01.getColumnModel().getColumn(2).setMaxWidth(80);
        tbMaterial_01.getColumnModel().getColumn(3).setMinWidth(75);
        tbMaterial_01.getColumnModel().getColumn(3).setMaxWidth(75);
        tbMaterial_01.getColumnModel().getColumn(4).setMinWidth(80);
        tbMaterial_01.getColumnModel().getColumn(4).setMaxWidth(80);

        btAdicionarMaterial.setText("Adicionar Material ▼");
        btAdicionarMaterial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAdicionarMaterialMouseClicked(evt);
            }
        });

        btRemoverMaterial.setText("Remover da Entrada");
        btRemoverMaterial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btRemoverMaterialMouseClicked(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Data:");

        tfValorNota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfValorNotaFocusLost(evt);
            }
        });

        lbData.setText("<Data>");

        jLabel9.setText("Código:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btPesquisar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbValorDoc)
                                    .addComponent(jLabel2)
                                    .addComponent(lbNumeroDoc))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfValorNota)
                                    .addComponent(tfNumeroNota)
                                    .addComponent(cbTipoDem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1)
                                    .addComponent(lbDataDoc))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cbFornecedor, 0, 156, Short.MAX_VALUE)
                                        .addComponent(tfNumeroEmpenho, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jcDataNota, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCodigoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdicionarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(658, Short.MAX_VALUE)
                        .addComponent(btCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcConfirmar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(746, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbData))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(btRemoverMaterial)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbData)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbNumeroDoc)
                        .addGap(13, 13, 13)
                        .addComponent(lbValorDoc))
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbDataDoc)
                            .addComponent(jcDataNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNumeroEmpenho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbTipoDem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfValorNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdicionarMaterial)
                    .addComponent(tfCodigoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRemoverMaterial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bcConfirmar)
                    .addComponent(btCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPesquisarMouseClicked
        this.limparTabela();
        try {
            this.preencherTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao obter os Materiais!", "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btPesquisarMouseClicked

    private void btCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_btCancelarMouseClicked

    private void btAdicionarMaterialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAdicionarMaterialMouseClicked
        int linhaSelecionada = this.tbMaterial_01.getSelectedRow();
        int codigo = 0;
        if (linhaSelecionada == -1) {
            try {
                codigo = Integer.parseInt(this.tfCodigoMaterial.getText().toString());
                try {
                    try {
                        this.itemEntrada++;
                        DefaultTableModel modelo = (DefaultTableModel) this.tbMaterial_02.getModel();
                        Vector linha;

                        linha = criarLinha(this.controladoraMaterial.obterMaterialAtivoCodigo(codigo + ""));

                        if (verificaLinha(linha)) {
                            modelo.insertRow(modelo.getRowCount(), linha);
                            this.tbMaterial_02.clearSelection();
                        } else {
                            JOptionPane.showMessageDialog(null, "Você deve selecionar um outro Material !", "Atenção", JOptionPane.OK_OPTION);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Obter o Material!", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Código incorreto, material não encontrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite um Número ou Selecione um material!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } else if (linhaSelecionada != -1) {
            if (!verificaLinha(criarLinhaTabela01(linhaSelecionada))) {
                JOptionPane.showMessageDialog(null, "Você deve selecionar um outro Material !", "Atenção", JOptionPane.OK_OPTION);
            } else {
                this.itemEntrada++;
                DefaultTableModel modelo = (DefaultTableModel) this.tbMaterial_02.getModel();
                modelo.insertRow(modelo.getRowCount(), criarLinhaAdicionar());
                this.tbMaterial_01.clearSelection();

            }
        }
    }//GEN-LAST:event_btAdicionarMaterialMouseClicked

    private void btRemoverMaterialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRemoverMaterialMouseClicked
        this.tbMaterial_02.updateUI();
        int linhaSelecionada = this.tbMaterial_02.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar um Material !", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            this.removerLinha(linhaSelecionada);
        }
        this.acertarLinhasTabela(linhaSelecionada);
    }//GEN-LAST:event_btRemoverMaterialMouseClicked

    private void bcConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bcConfirmarMouseClicked
        try {
            if (montarEntrada()) {
                this.codigo_Entrada = this.controladoraEntrada.inserirEntrada(this.entrada, this.lote, this.documento);
                int opcao = JOptionPane.showConfirmDialog(null, "O Entrada " + this.codigo_Entrada + " foi inserido com Sucesso! \n Deseja o imprimir o Comprovante?", "Inserção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opcao == 0) {
                    this.controladoraRelatorio.getDEM(this.codigo_Entrada);
                }
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um Erro ao Adicionar o Entrada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bcConfirmarMouseClicked

    private void cbTipoDemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoDemItemStateChanged
        Vector nomeDoc = (Vector) this.tiposEntrada.get(this.cbTipoDem.getSelectedIndex());
        this.lbNumeroDoc.setText("Número da " + nomeDoc.get(2) + " :");
        this.lbValorDoc.setText("Valor total da " + nomeDoc.get(2) + " R$:");
        this.lbDataDoc.setText("Data da " + nomeDoc.get(2) + " :");
    }//GEN-LAST:event_cbTipoDemItemStateChanged

    private void tfValorNotaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfValorNotaFocusLost
        if (!(this.tfValorNota.getText().equals(""))) {
            try {
                Double.parseDouble(this.tfValorNota.getText().toString().replace(',', '.'));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite um Valor Valido!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                this.tfValorNota.setText("");
            }
        }
    }//GEN-LAST:event_tfValorNotaFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bcConfirmar;
    private javax.swing.JButton btAdicionarMaterial;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btRemoverMaterial;
    private javax.swing.JComboBox cbFornecedor;
    private javax.swing.JComboBox cbTipoDem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private com.toedter.calendar.JDateChooser jcDataNota;
    private javax.swing.JLabel lbData;
    private javax.swing.JLabel lbDataDoc;
    private javax.swing.JLabel lbNumeroDoc;
    private javax.swing.JLabel lbValorDoc;
    private javax.swing.JTable tbMaterial_01;
    private javax.swing.JTable tbMaterial_02;
    private javax.swing.JTextField tfCodigoMaterial;
    private javax.swing.JFormattedTextField tfNumeroEmpenho;
    private javax.swing.JFormattedTextField tfNumeroNota;
    private javax.swing.JTextField tfPesquisar;
    private javax.swing.JFormattedTextField tfValorNota;
    // End of variables declaration//GEN-END:variables
}
