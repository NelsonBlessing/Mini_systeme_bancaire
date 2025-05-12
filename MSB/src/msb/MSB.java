package msb;

import DB.clientBD;
import DB.compteBD;
import DB.transactionBD;
import Model.Client;
import Modeltransaction.Transaction;
import Service.service;
import Utlis.Session;

import java.util.List;
import java.util.Scanner;

public class MSB {
     static Client clientConnecte = null;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n==Bienvenu dans mini systeme de banque==");
        System.out.println("\nEntrer votre numéro de téléphone +257XXXXXXXX");
        String num = scanner.nextLine();

        while (!num.startsWith("+257")) {
            System.out.print("\nNuméro invalide. Veuillez entrer un numéro commençant par +257 : ");
            num = scanner.nextLine();
        }

        System.out.print("Entrer votre mot de passe : ");
        String mdp = scanner.nextLine();

        clientBD cbd = new clientBD();
        Client cl = cbd.getClientParConnexion(num, mdp);

        if (cl != null) {
            clientConnecte = cl;
            System.out.println("\nBonjour " + cl.getNom() + " " + cl.getPrenom() + " !");
            System.out.println("==Menu==");
            System.out.println("1. Dépôt");
            System.out.println("2. Retrait");
            System.out.println("3. Transfère");
            System.out.println("4. Afficher solde");
            System.out.println("5. Historique de transaction");
            System.out.println("6. Aide");
            System.out.println("7. Quitter");
            int choixMenu = scanner.nextInt();
            scanner.nextLine();
            String telephone = scanner.nextLine();

            switch (choixMenu) {
                case 1: // Dépôt
                    System.out.print("Entrer votre montant à déposer : ");
                    double montantDepot = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Montant à déposer : " + montantDepot);
                    boolean successDepot = service.deposer(num, montantDepot, true);

                    if (successDepot) {
                        System.out.println("Dépôt effectué avec succès !");
                    } else {
                        System.out.println("Échec du dépôt.");
                    }
                    break;

                case 2: // Retrait
                    System.out.print("Entrer le montant à retirer : ");
                    double montantRetrait = scanner.nextDouble();
                    scanner.nextLine();

                    boolean successRetrait = service.retirer(num, montantRetrait);

                    if (successRetrait) {
                        System.out.println("Retrait effectué avec succès !");
                    } else {
                        System.out.println("Échec du retrait.");
                    }
                    break;

                case 3: // Transfert
                    System.out.print("Entrer le numéro du destinataire : ");
                    String telDestinateur = scanner.nextLine();

                    System.out.print("Entrer le montant à transférer : ");
                    double montantTransfert = scanner.nextDouble();
                    scanner.nextLine();

                    service service = new service();
                    service.transfert(Session.getIdClient(), telDestinateur, montantTransfert);
                    break;

                case 4: // Vérifier Solde
                    double solde = compteBD.getSoldeParTelephone(num);
                    if (solde != -1) {
                        System.out.println("Votre solde actuel est de " + solde + " BIF");
                    } else {
                        System.out.println("Impossible de récupérer le solde.");
                    }
                    break;

                case 5: // Historique des transactions
                    List<Transaction> transactions = transactionBD.getTransactionParTelephone(num);

                    if (transactions.isEmpty()) {
                        System.out.println("Aucune Transaction trouvée.");
                    } else {
                        System.out.println("Historique des transactions : ");
                        for (Transaction t : transactions) {
                            System.out.println("\nID Transaction : " + t.getId()
                                    + ", \nType : " + t.getType()
                                    + ", \nMontant : " + t.getMontant()
                                    + ", \nDate : " + t.getDate());
                        }
                    }
                    break;

                case 6: // Aide
                    int aideChoix;
                        System.out.println("==Aide==");
                        System.out.println("1. Changer le mot de passe");
                        System.out.println("2. Changer la langue ");
                        System.out.println("3. Retour");
                        System.out.println("choix :");
                        aideChoix = scanner.nextInt();
                        scanner.nextLine();

                       switch (aideChoix){
                           case 1:
                               System.out.print("Entrer l'ancien mot de passe :");
                               String ancientMdp = scanner.nextLine();

                               System.out.print("Enter le nouveau mot de passe :");
                               String nouveauMdp = scanner.nextLine();

                               System.out.print("Confirmer le nouveau mot de passe :");
                               String confirmation = scanner.nextLine();

                               if (!nouveauMdp.equals(confirmation)) {
                                   System.out.println("Erreur : les mot de passe de confirmation ne correspondent pas !");
                                   break;

                               }
                               if (clientBD.changerMotDePasse(clientConnecte.getTelephone(),ancientMdp,nouveauMdp)){
                                   System.out.println("Mot de passe changé avec succès !");
                               } else {
                                   System.out.print("Erreur : ancien mot de passe est incorrect");

                               }
                               break;
                           case 2 :
                               // changer langue
                                break;

                           case 3:
                               System.out.println("Retour au menu principale !");
                               break;

                               default:
                               System.out.println("choix invalide");
                       }
                       break;

                case 7: // Quitter
                    System.out.println("Merci d'avoir utilisé notre système bancaire !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }


            // Tu pourras ajouter les autres options ici (retrait, solde, etc.)
        } else {
              clientBD clientBD = new clientBD();
               if (clientBD.telephoneExiste(num)){
                   System.out.println("Mot de passe incorrect !");
               } else {
                   System.out.println("\nNuméro saisi non trouvé.");
                   System.out.println("Voulez-vous créer un compte ? (1 = Oui, 0 = Non)");
                   int choix = scanner.nextInt();
                   scanner.nextLine();
                   if (choix == 1) { // Inscription
                       System.out.println("Entrer votre nom :");
                       String nom = scanner.nextLine();

                       System.out.println("Entrer votre prénom :");
                       String prenom = scanner.nextLine();

                       System.out.println("Entrer votre adresse :");
                       String adresse = scanner.nextLine();

                       System.out.println("Entrer votre numéro de téléphone :");
                       String telephone = scanner.nextLine();

                       while (!telephone.startsWith("+257")) {
                           System.out.print("Numéro invalide. Veuillez entrer un numéro commençant par +257 : ");
                           telephone = scanner.nextLine();

                       }
                       System.out.println("Entrer votre mot de passe :");
                       String motDepasse = scanner.nextLine();

                       Client nouveauClient = new Client(nom, prenom, adresse, telephone, motDepasse);

                       if (!clientBD.ajouterClient(nouveauClient)){
                           System.out.println("Erreur lors de la création du client !");
                       }
                   } else {
                       System.out.println("Merci d'avoir utilisé notre système !");
                   }
               }

                      scanner.close();
        }

    }

}










