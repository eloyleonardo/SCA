package boundary;

import control.ControladoraUsuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrmLogin extends javax.swing.JFrame {

    private ControladoraUsuario controladora;

    public FrmLogin() {
        initComponents();
        this.tfServidor.setText(lerArquivo());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/img/SCA-Logo_4.png")).getImage());
    }

    private String lerArquivo() {
        try {
            File f = new File(System.getProperty("user.home") + "/sca.bin");
            Scanner s = new Scanner(f);
            return s.next();
        } catch (Exception ex) {
            return "";
        }
    }

    private void gravarDados() {
        try {
            Formatter f = new Formatter(System.getProperty("user.home") + "/sca.bin");
            f.format("%s", this.tfServidor.getText());
            f.close();
        } catch (FileNotFoundException ex) {
        }
    }

    private void logar() {
//        this.tfLogin.setText("almoxarife");
//        this.tfLogin.setText("verson");
//        this.tfSenha.setText("1234");
        String servidor = this.tfServidor.getText();
        gravarDados();
        this.controladora = new ControladoraUsuario(servidor);
        Vector usuario = this.controladora.logar(this.tfLogin.getText(), new String(this.tfSenha.getPassword()));
        if (usuario.size() == 13 && (!usuario.get(0).equals("0"))) {
            JFrame janela = new FrmPrincipal(usuario, servidor);
            this.dispose();
            janela.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou senha incorretos !", "Erro ao Logar", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfSenha = new javax.swing.JPasswordField();
        btEntrar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfServidor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SCA-Login de Usuario");
        setResizable(false);

        jLabel1.setText("Login:");

        jLabel2.setText("Senha:");

        tfSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSenhaActionPerformed(evt);
            }
        });

        btEntrar.setText("Entrar");
        btEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btEntrarMouseClicked(evt);
            }
        });

        btSair.setText("Cancelar");
        btSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSairMouseClicked(evt);
            }
        });

        jLabel3.setText("Servidor:");

        tfServidor.setToolTipText("<html>Indique o servidor de banco de dados,<br/> deixe vazio para usar a máquina local !</html>");
        tfServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServidorActionPerformed(evt);
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
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(tfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(tfServidor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEntrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(btEntrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEntrarMouseClicked
        this.logar();
}//GEN-LAST:event_btEntrarMouseClicked

    private void btSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btSairMouseClicked

    private void tfSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSenhaActionPerformed
        logar();
    }//GEN-LAST:event_tfSenhaActionPerformed

    private void tfServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServidorActionPerformed
        this.logar();
    }//GEN-LAST:event_tfServidorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JButton btSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tfLogin;
    private javax.swing.JPasswordField tfSenha;
    private javax.swing.JTextField tfServidor;
    // End of variables declaration//GEN-END:variables
}
