package boundary;

import control.ControladoraFornecedor;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import util.ActionFechar;
import util.ValidaCpfCnpj;
import util.WebServiceCep;

public abstract class FrmFormularioFornecedor extends javax.swing.JDialog {

    protected ControladoraFornecedor controladora = new ControladoraFornecedor();
    private Vector ufs = new Vector();
    private Vector tes = new Vector();
    private Vector cidades = new Vector();

    public FrmFormularioFornecedor() {
        initComponents();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.setModal(true);
        this.adicionarMap();
        this.setLocationRelativeTo(null);
        this.ufs = this.controladora.obterLinasUfs();
        for (int i = 0; i < ufs.size(); i++) {
            Vector uf = new Vector();
            uf = (Vector) this.ufs.get(i);
            cbUf.addItem(uf.get(1));
        }
        this.tes = this.controladora.obterLinhasTe();
        for (int i = 0; i < tes.size(); i++) {
            Vector te = new Vector();
            te = (Vector) this.tes.get(i);
            cbTipoLogradouro.addItem(te.get(1));
        }
        this.setCursor(Cursor.getDefaultCursor());

    }

    protected boolean validarCampos() {

        if (this.tfNomeFantasia.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo Nome esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfRazao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo Razão Social esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfCep.getText().equals("  .   -  ")) {
            JOptionPane.showMessageDialog(null, "O campo CEP esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.cbTipoLogradouro.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "O tipo de Logradouro não foi selecionado, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.cbCidade.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "A cidade não foi selecionada, por favor verifique - a !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.cbUf.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "A UF não foi selecionada, por favor verifique - a !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfBairro.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo Bairro esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfComplemento.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo Complemento esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfTel1.getText().equals("(  )    -    ")) {
            JOptionPane.showMessageDialog(null, "O campo telefone 1 esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!this.validarEmail(this.tfEmail.getText())) {
            JOptionPane.showMessageDialog(null, "O E-Mail digitado está incorreto !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfTel2.getText().equals("(  )    -    ")) {
            JOptionPane.showMessageDialog(null, "O campo telefone 2 esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (this.tfFax.getText().equals("(  )    -    ")) {
            JOptionPane.showMessageDialog(null, "O campo Fax esta vazio, por favor verifique - o !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    protected void setarAtributos(Vector linha) {
        String id = linha.get(0).toString();
        if (id.length() == 11) {
            this.cbId.setSelectedIndex(1);
        }
        this.tfId.setText(id);
        this.tfRazao.setText(linha.get(1).toString());
        this.tfNomeFantasia.setText(linha.get(2).toString());
        this.tfCep.setText(linha.get(3).toString());
        this.cbTipoLogradouro.setSelectedIndex(getIndexTe(linha.get(4).toString()));
        this.tfLogradouro.setText(linha.get(5).toString());
        this.cbUf.setSelectedIndex(getIndexUf(linha.get(6).toString()));
        this.cbCidade.setSelectedIndex(getIndexCidade(linha.get(7).toString()));
        this.tfBairro.setText(linha.get(8).toString());
        this.tfComplemento.setText(linha.get(9).toString());
        this.tfTel1.setText(linha.get(10).toString());
        this.tfTel2.setText(linha.get(11).toString());
        this.tfEmail.setText(linha.get(12).toString());
        this.tfFax.setText(linha.get(13).toString());
    }

    private void adicionarMap() {
        this.getRootPane().getActionMap().put("FECHAR", new ActionFechar(this));
        this.getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "FECHAR");
    }

    private void carregarCidades() {
        this.cidades = this.controladora.obterCidades(this.cbUf.getSelectedItem().toString());
        for (int i = 0; i < cidades.size(); i++) {
            Vector cidade = (Vector) this.cidades.get(i);
            cbCidade.addItem(cidade.get(1));
        }
    }

    private int getIndexUf(String nome) {
        for (int i = 0; i < ufs.size(); i++) {
            Vector v = (Vector) ufs.get(i);
            if (v.get(0).equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexTe(String nome) {
        for (int i = 0; i < tes.size(); i++) {
            Vector v = (Vector) tes.get(i);
            if (v.get(1).equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexCidade(String nome) {
        for (int i = 0; i < cidades.size(); i++) {
            Vector v = (Vector) cidades.get(i);
            if (v.get(1).equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    private void consultarCep() {
        try {
            String cep, txt = this.tfCep.getText();
            cep = txt.substring(0, 2);
            cep += txt.substring(3, 6);
            cep += txt.substring(7, 10);

            WebServiceCep cepWebService = new WebServiceCep(cep);

            if (cepWebService.getResultado() == 1) {
                this.tfBairro.setText(cepWebService.getBairro());
                this.tfLogradouro.setText(cepWebService.getLogradouro());
                this.cbTipoLogradouro.setSelectedIndex(getIndexTe(cepWebService.getTipo_logradouro()));
                this.cbUf.setSelectedIndex(getIndexUf(cepWebService.getUf().toLowerCase()));
                this.carregarCidades();
                this.cbCidade.setSelectedIndex(getIndexCidade(cepWebService.getCidade()));

            }
        } catch (Exception ex) {
        }
    }

    private boolean validarId() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String id;
        if (cbId.getSelectedItem().toString().equals("CPF")) {
            String txt = this.tfId.getText();
            id = txt.substring(0, 3);
            id += txt.substring(4, 7);
            id += txt.substring(8, 11);
            id += txt.substring(12, 14);
        } else {
            String txt = this.tfId.getText();
            id = txt.substring(0, 2);
            id += txt.substring(3, 6);
            id += txt.substring(7, 10);
            id += txt.substring(11, 15);
            id += txt.substring(16, 18);
        }
        if (controladora.verificarId(id)) {
            ValidaCpfCnpj valida = new ValidaCpfCnpj();
            if (!valida.validarId(id, cbId.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null, "O " + cbId.getSelectedItem().toString() + " digitado não é valido !\n" +
                        "Por favor ferifique-o", "Erro", JOptionPane.ERROR_MESSAGE);
                this.setCursor(Cursor.getDefaultCursor());
                return false;
            }
            this.setCursor(Cursor.getDefaultCursor());
            return true;
        } else {
            this.tfId.setValue("");
            this.tfId.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
        return false;
    }

    private boolean validarEmail(String email) {
        if (email.equals("")) {
            return false;
        } else {
            Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = p.matcher(email);
            return m.matches();
        }

    }

    protected Vector criarLinha() {
        Vector linha = new Vector();
        String txt = new String();
        txt = this.tfId.getText();
        String id = new String();
        if (cbId.getSelectedItem().toString().equals("CPF")) {
            id = txt.substring(0, 3);
            id += txt.substring(4, 7);
            id += txt.substring(8, 11);
            id += txt.substring(12);
        } else {
            id = txt.substring(0, 2);
            id += txt.substring(3, 6);
            id += txt.substring(7, 10);
            id += txt.substring(11, 15);
            id += txt.substring(16);
        }
        linha.addElement(id);

        linha.addElement(this.tfRazao.getText());
        linha.addElement(this.tfNomeFantasia.getText());
        txt = this.tfCep.getText();
        String cep = new String();
        cep = txt.substring(0, 2);
        cep += txt.substring(3, 6);
        cep += txt.substring(7);
        linha.addElement(cep);
        linha.addElement(this.cbTipoLogradouro.getSelectedItem().toString());
        linha.addElement(this.tfLogradouro.getText());
        linha.addElement(this.cbUf.getSelectedItem().toString());
        linha.addElement(this.cbCidade.getSelectedItem().toString());
        linha.addElement(this.tfBairro.getText());
        linha.addElement(this.tfComplemento.getText());
        linha.addElement(convertTel(this.tfTel1.getText()));
        linha.addElement(convertTel(this.tfTel2.getText()));
        linha.addElement(this.tfEmail.getText());
        linha.addElement(convertTel(this.tfFax.getText()));
        return linha;
    }

    private String convertTel(String txt) {
        String tel = txt.substring(1, 3);
        tel += txt.substring(5, 9);
        tel += txt.substring(10);
        return tel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        tfRazao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNomeFantasia = new javax.swing.JTextField();
        tfCep = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        btObter = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbTipoLogradouro = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbUf = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfComplemento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfTel1 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfTel2 = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        tfFax = new javax.swing.JFormattedTextField();
        btOk = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        cbId = new javax.swing.JComboBox();
        tfId = new javax.swing.JFormattedTextField();
        cbCidade = new javax.swing.JComboBox();
        tfLogradouro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel2.setText("Razão Social:");

        jLabel3.setText("Nome Fantasia:");

        try {
            tfCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("CEP:");

        btObter.setText("Obter Dados");
        btObter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btObterMouseClicked(evt);
            }
        });
        btObter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btObterActionPerformed(evt);
            }
        });

        jLabel5.setText("Tipo de Logradouro:");

        jLabel6.setText("Logradouro:");

        jLabel7.setText("Uf:");

        cbUf.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbUfItemStateChanged(evt);
            }
        });

        jLabel8.setText("Cidade:");

        jLabel9.setText("Bairro:");

        jLabel10.setText("Complemento:");

        jLabel11.setText("Telefone:");

        try {
            tfTel1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel12.setText("E-Mail:");

        tfEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfEmailFocusLost(evt);
            }
        });

        jLabel13.setText("Telefone 2:");

        try {
            tfTel2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setText("Fax:");

        try {
            tfFax.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btOk.setText("Ok");

        jButton3.setText("Cancelar");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        cbId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CNPJ", "CPF" }));
        cbId.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbIdItemStateChanged(evt);
            }
        });

        try {
            tfId.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfIdFocusLost(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfRazao, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(tfNomeFantasia, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbUf, 0, 144, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfTel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                    .addComponent(tfTel2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                    .addComponent(tfBairro, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
                            .addComponent(cbTipoLogradouro, javax.swing.GroupLayout.Alignment.LEADING, 0, 163, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCidade, 0, 138, Short.MAX_VALUE)
                                    .addComponent(tfComplemento, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(tfFax, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(tfLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btObter))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btObter)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbTipoLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(tfFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOk)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btObterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btObterMouseClicked
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        consultarCep();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btObterMouseClicked

    private void cbIdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbIdItemStateChanged
        this.tfId.setValue("");
        if (cbId.getSelectedItem().equals("CNPJ")) {
            try {
                this.tfId.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
            } catch (ParseException ex) {
            }
        } else {
            try {
                this.tfId.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
            } catch (ParseException ex) {
            }
        }
}//GEN-LAST:event_cbIdItemStateChanged

    private void tfIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfIdFocusLost
        if ((!this.tfId.getText().equals("  .   .   /    -  ")) && (!this.tfId.getText().equals("   .   .   -  "))) {
            this.validarId();
        }
}//GEN-LAST:event_tfIdFocusLost

    private void tfEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfEmailFocusLost
        if (!this.tfEmail.getText().equals("")) {
            if (!this.validarEmail(this.tfEmail.getText())) {
                JOptionPane.showMessageDialog(null, "O E-Mail digitado está incorreto !", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_tfEmailFocusLost

    private void cbUfItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbUfItemStateChanged
        cbCidade.removeAllItems();
        this.carregarCidades();
    }//GEN-LAST:event_cbUfItemStateChanged

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void btObterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btObterActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        consultarCep();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btObterActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btObter;
    protected javax.swing.JButton btOk;
    private javax.swing.JComboBox cbCidade;
    protected javax.swing.JComboBox cbId;
    private javax.swing.JComboBox cbTipoLogradouro;
    private javax.swing.JComboBox cbUf;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JFormattedTextField tfCep;
    private javax.swing.JTextField tfComplemento;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JFormattedTextField tfFax;
    protected javax.swing.JFormattedTextField tfId;
    private javax.swing.JTextField tfLogradouro;
    private javax.swing.JTextField tfNomeFantasia;
    private javax.swing.JTextField tfRazao;
    private javax.swing.JFormattedTextField tfTel1;
    private javax.swing.JFormattedTextField tfTel2;
    // End of variables declaration//GEN-END:variables
}
