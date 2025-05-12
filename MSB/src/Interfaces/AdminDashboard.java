package Interfaces;

import javax.swing.*;
import java.awt.*;

import DB.BDConnexion;
import DB.compteBD;
import DB.transactionBD;
import Model.Client;
import Modeltransaction.Transaction;
import Modelcompte.Compte;
import DB.clientBD;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JTabbedPane tabbedPane;
    private JTable clientsTable;
    private JTable comptesTable;
    private JTable transactionsTable;
    private JLabel totalTransactionsLabel;
    private JLabel totalClientsLabel;
    private JLabel totalComptesLabel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();


        // Onglet Dashboard
        totalClientsLabel = new JLabel("Clients : 0");
        totalComptesLabel = new JLabel("Comptes : 0");
        totalTransactionsLabel = new JLabel("Transactions : 0");

        totalTransactionsLabel.setForeground(Color.WHITE);
        totalTransactionsLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        totalClientsLabel.setForeground(Color.WHITE);
        totalClientsLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        totalComptesLabel.setForeground(Color.WHITE);
        totalComptesLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(new Color(45, 45, 45));
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));

        JLabel welcomLabel = new JLabel("Bienvenue sur le tableau de bord administrateur");
        welcomLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        welcomLabel.setForeground(Color.WHITE);
        welcomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


       totalClientsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalComptesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalTransactionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(welcomLabel);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(totalClientsLabel);
        dashboardPanel.add(Box.createVerticalStrut(10));
        dashboardPanel.add(totalComptesLabel);
        dashboardPanel.add(Box.createVerticalStrut(10));
        dashboardPanel.add(totalTransactionsLabel);

        tabbedPane.addTab("Dashboard", dashboardPanel);

         chargerStatistiquesDashboard();

        // Onglet Clients
        JPanel clientsPanel = new JPanel(new BorderLayout());
        clientsPanel.setBackground(new Color(30, 30, 30));
        String[] clientColumns = {"ID", "Nom", "Prénom", "Adresse", "Téléphone", "Mot de Passe"};
        DefaultTableModel clientModel = new DefaultTableModel(clientColumns, 0);
        clientsTable = new JTable(clientModel);
        JScrollPane clientScrollPane = new JScrollPane(clientsTable);
        JPanel clientButtons = new JPanel();
       JButton ajouterClientBtn = new JButton("Ajouter");
       clientButtons.add(ajouterClientBtn);
        JButton modifierClientBtn = new JButton("Modifier");
        clientButtons.add(modifierClientBtn);
        JButton supprimerClientBtn = new JButton("Supprimer");
        clientButtons.add(supprimerClientBtn);
        clientsPanel.add(clientScrollPane, BorderLayout.CENTER);
        clientsPanel.add(clientButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Clients", clientsPanel);

        // Onglet Comptes
        JPanel comptesPanel = new JPanel(new BorderLayout());
        comptesPanel.setBackground(new Color(30, 30, 30));
        String[] compteColumns = {"Numéro", "Solde", "Date Création", "ID Client"};
        comptesTable = new JTable(new DefaultTableModel(compteColumns, 0));
        JScrollPane compteScrollPane = new JScrollPane(comptesTable);
        JPanel compteButtons = new JPanel();
       // compteButtons.add(new JButton("Bloquer"));
      //  compteButtons.add(new JButton("Supprimer"));
       comptesPanel.add(compteScrollPane, BorderLayout.CENTER);
        comptesPanel.add(compteButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Comptes", comptesPanel);

        // Onglet Transactions
        JPanel transactionsPanel = new JPanel(new BorderLayout());
        transactionsPanel.setBackground(new Color(30, 30, 30));
        String[] transactionColumns = {"ID", "Type", "Montant", "Date", "Compte"};
        transactionsTable = new JTable(new DefaultTableModel(transactionColumns, 0));
        JScrollPane transactionScrollPane = new JScrollPane(transactionsTable);
        JPanel transactionButtons = new JPanel();
       // transactionButtons.add(new JButton("Filtrer"));
      //  transactionButtons.add(new JButton("Exporter"));
        transactionsPanel.add(transactionScrollPane, BorderLayout.CENTER);
        transactionsPanel.add(transactionButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Transactions", transactionsPanel);

        // Onglet Paramètres
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(new Color(45, 45, 45));
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.setFont(new Font("Roboto", Font.BOLD, 14));
        settingsPanel.add(logoutButton);
        tabbedPane.addTab("Paramètres", settingsPanel);



        add(tabbedPane);

        ajouterClientBtn.addActionListener(e -> {
            INSCRI dialog = new INSCRI(this, true, this, true, true, null);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        logoutButton.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment vous déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                dispose(); // Ferme AdminDashboard
                new Menu().setVisible(true); // Ouvre la fenêtre de connexion
            }
        });

        supprimerClientBtn.addActionListener(e -> {
            int selectedRow = clientsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.");
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce client et toutes ses données associées ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
                    int idClient = (int) model.getValueAt(selectedRow, 0);

                    Connection con = new BDConnexion().getConnection();

                    // Supprimer d'abord les transactions
                    String deleteTransactions = "DELETE FROM transaction WHERE id_compte IN (SELECT id_compte FROM compte WHERE id_client = ?)";
                    PreparedStatement pstTrans = con.prepareStatement(deleteTransactions);
                    pstTrans.setInt(1, idClient);
                    pstTrans.executeUpdate();

                    // Puis les comptes
                    String deleteComptes = "DELETE FROM compte WHERE id_client = ?";
                    PreparedStatement pstComptes = con.prepareStatement(deleteComptes);
                    pstComptes.setInt(1, idClient);
                    pstComptes.executeUpdate();

                    // Enfin le client
                    String deleteClient = "DELETE FROM client WHERE id_client = ?";
                    PreparedStatement pstClient = con.prepareStatement(deleteClient);
                    pstClient.setInt(1, idClient);
                    int rows = pstClient.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Client et ses données supprimés avec succès.");
                        chargerClients();
                        chargerComptes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Échec de la suppression.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
                }
            }
        });


        modifierClientBtn.addActionListener(e -> {
            int selectedRow = clientsTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
                int id = (int) model.getValueAt(selectedRow, 0);
                String nom = (String) model.getValueAt(selectedRow, 1);
                String prenom = (String) model.getValueAt(selectedRow, 2);
                String adresse = (String) model.getValueAt(selectedRow, 3);
                String telephone = (String) model.getValueAt(selectedRow, 4);
                String motDepasse = (String) model.getValueAt(selectedRow, 5);

                Client client = new Client(nom, prenom, adresse, telephone, motDepasse);
                client.setId(id);

                INSCRI dialog = new INSCRI(this, true, this, false, false, client); // false = mode modification
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);

                chargerClients();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à modifier.");
            }
        });


        // Appel de la méthode ici
        chargerTransactions();
        chargerComptes();
        chargerClients();
    }

    // Méthode chargée en dehors du constructeur
    public void chargerClients() {
        List<Client> clients = clientBD.getAllClients();
        DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
        model.setRowCount(0); // Vider les anciennes lignes


        for (Client c : clients) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getNom(),
                    c.getPrenom(),
                    c.getAdresse(),
                    c.getTelephone(),
                    c.getmotDepasse()
            });
        }
    }
    private void chargerTransactions() {
        List<Transaction> transactions = DB.transactionBD.getAllTransactions(); // adapte le chemin si besoin
        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();
        model.setRowCount(0); // Vider l'ancienne liste

        for (Transaction t : transactions) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getType(),
                    t.getMontant(),
                    t.getDate(),
                    t.getIdcompte()
            });
        }
    }

    public void chargerStatistiquesDashboard() {
        int totalClients = clientBD.getTotalClients();
        int totalComptes = compteBD.getTotalComptes();
        int totalTransactions = transactionBD.getTotalTransactions();

        totalClientsLabel.setText("Clients : " + totalClients);
        totalComptesLabel.setText("Comptes : " + totalComptes);
        totalTransactionsLabel.setText("Transactions : " + totalTransactions);
    }

    private void chargerComptes() {
        List<Compte> comptes = DB.compteBD.getAllComptes(); // Adapte le chemin si nécessaire
        DefaultTableModel model = (DefaultTableModel) comptesTable.getModel();
        model.setRowCount(0); // Vider l'ancien contenu

        for (Compte c : comptes) {
            model.addRow(new Object[]{
                    c.getNumeroCompte(),
                    c.getSolde(),
                    c.getDateCreation(),
                    c.getIdClient()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);

        });
    }
}