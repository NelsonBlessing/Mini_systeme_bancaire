package Modelcompte;

public class Compte {
    private int idCompte;
    private String numeroCompte;
    private double solde;
    private String dateCreation;
    private int idClient;
    private String nomClient;
    private String prenomClient;
    // Constructeurs
    public Compte() {
    }

    public Compte(int idCompte, String numeroCompte, double solde, String dateCreation, int idClient) {
        this.idCompte = idCompte;
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.idClient = idClient;
    }

    // Getters et setters
    public int getIdCompte() {
        return idCompte;
    }
    public String getNomClient() {
        return nomClient;
    }
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
    public String getPrenomClient() {
        return prenomClient;
    }
    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    // Méthode utile pour l'affichage
    @Override
    public String toString() {
        return "Compte{" +
                "idCompte=" + idCompte +
                ", numeroCompte='" + numeroCompte + '\'' +
                ", solde=" + solde +
                ", dateCreation='" + dateCreation + '\'' +
                ", idClient=" + idClient +
                '}';
    }
}