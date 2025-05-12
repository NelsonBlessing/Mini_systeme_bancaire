package Model;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String motDepasse;

    public Client() {

    }
    
   public Client(String nom, String prenom, String adresse, String telephone, String motDepasse){
       this.nom = nom;
       this.prenom = prenom;
       this.adresse = adresse;
       this.telephone = telephone;
       this.motDepasse = motDepasse;
   } 
            
   public int getId() { return id; }
   public void setId(int id) { this.id = id; }
   
   public String getNom() { return nom;}
   public void SetNom(String nom) { this.nom = nom; }
   
    public String getPrenom() { return prenom;}
   public void SetPrenom(String prenom) { this.prenom = prenom; }
   
    public String getAdresse() { return adresse;}
   public void SetAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone;}
   public void SetTelephone(String telephone) { this.telephone = telephone; }

    public String getmotDepasse() { return motDepasse;}
    public void SetmotDepasse(String motDepasse) { this.motDepasse = motDepasse; }
}