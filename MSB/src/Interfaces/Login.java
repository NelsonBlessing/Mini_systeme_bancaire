package Interfaces;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import DB.BDConnexion;
import Interfaces.Menu;
import Utlis.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 *
 * @author prisc
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public Login() {

        initComponents();

        String placeholder = "+257XXXXXXXX";
        txtnumero.setForeground(Color.GRAY);
        txtnumero.setText(placeholder);

        final boolean[] isPlaceholder = {true};
        txtnumero.addFocusListener(new java.awt.event.FocusAdapter()
        {
            @Override
            public void focusGained(java.awt.event.FocusEvent e){
                if (isPlaceholder[0]){
                    txtnumero.setText("");
                    txtnumero.setForeground(Color.BLACK);
                    isPlaceholder[0] = false;
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e){
                if (txtnumero.getText().isEmpty()){
                    txtnumero.setForeground(Color.GRAY);
                    txtnumero.setText(placeholder);
                    isPlaceholder[0] = true;
                }
            }
        });

        //BLoquer les caratère
        txtnumero.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            public void changedUpdate(javax.swing.event.DocumentEvent e){
                limitLength();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e){
                limitLength();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e){
                limitLength();
            }
            public void limitLength(){
                SwingUtilities.invokeLater(()-> {
                    String text = txtnumero.getText();
                    if (text.length() > 12){
                        txtnumero.setText(text.substring(0,12));
                    }
                });
            }
        });
    }

    private boolean estNumeroValider(String numero){
        if (numero.equals("+257XXXXXXXX") || numero.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Veillez enter votre numéro de téléphone !");
            return false;
        }
        if (!numero.matches("\\+257\\d{8}")){
            JOptionPane.showMessageDialog(null,"Le numéro doit commencer par +257 suivi de 8 chiffres !");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnconnexion = new javax.swing.JButton();
        txtnumero = new javax.swing.JTextField();
        txtmotdepasse = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(6, 29, 27));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("==Bienvenu dans Mini_Systèm_Bancaire==");

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Numéro :");

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mot de passe :");

        btnconnexion.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        btnconnexion.setForeground(new java.awt.Color(6, 29, 27));
        btnconnexion.setText("Se connecter");
        btnconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconnexionActionPerformed(evt);
            }
        });

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtmotdepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(btnconnexion)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtmotdepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btnconnexion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconnexionActionPerformed
        // TODO add your handling code here:
        String numero = txtnumero.getText();
        if (!estNumeroValider(numero)){
            return;
        }
        String motDePasse = new String(txtmotdepasse.getPassword());

        if (numero.isEmpty() || motDePasse.isEmpty()){
            JOptionPane.showMessageDialog(this,"Veillez remplir tous les champs. ");
            return;
        }
        if (!numero.startsWith("+257")){
            JOptionPane.showMessageDialog(this, "Numéro invalide . Veillez enter un numéro commençant par +257");
            return;
        }

        try {
            Connection con = new BDConnexion().getConnection();
            String sql = "SELECT * FROM client WHERE telephone =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String motDePasseBD = rs.getString("mot_de_passe");

                int idClient = rs.getInt("id_client");
                Session.setIdClient(idClient);
                if (motDePasse.equals(motDePasseBD)){
                   // JOptionPane.showMessageDialog(this,"Connexion réussie !");
                    //Menu
                    Menu menu = new Menu();
                    menu.setVisible(true);
                    this.dispose();

                } else{
                    tentativeConnexion++;
                    if (tentativeConnexion >= MAX_TENTATIVES){
                        JOptionPane.showMessageDialog(this,"Trop de tentatives échouées. Veillez réessayer après 10 seconde.");
                        btnconnexion.setEnabled(false); //desactiver le bouton

                        Timer timer = new Timer(10000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                tentativeConnexion = 0;
                                btnconnexion.setEnabled(true);
                                JOptionPane.showMessageDialog(null,"Vous pouvez réessayer maintenant.");
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        JOptionPane.showMessageDialog(this,"Mot de passe incorrect. Tentative :" +tentativeConnexion +"/"+ MAX_TENTATIVES);
                    }
                }
            } else {
             int choix = JOptionPane.showConfirmDialog(this,"Numero saisie est introuvable. Voulez-vous vous inscrire ?","Inscription", JOptionPane.YES_NO_OPTION);
                 if (choix == JOptionPane.YES_OPTION){
                     //inscription
                    INSCRI popup = new INSCRI(this, true);
                    popup.setLocationRelativeTo(null);
                    popup.setVisible(true);
                 }
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de la connection à la base de données");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnconnexionActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()){
            txtmotdepasse.setEchoChar((char) 0);
        } else {
            txtmotdepasse.setEchoChar('*');
        }

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnconnexion;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtmotdepasse;
    private javax.swing.JTextField txtnumero;
    private int tentativeConnexion = 0;
    private final int MAX_TENTATIVES = 3;
    // End of variables declaration//GEN-END:variables
}
