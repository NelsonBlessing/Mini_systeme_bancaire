package Modeltransaction;

import java.util.Date;


public class Transaction {
    private int id;
    private int idcompte;
    private String type;
    private double montant;
    private Date date;

    public Transaction() {

    }

    public Transaction(int id, int idcompte, String type, double montant, Date date){
        this.id = id;
        this.idcompte = idcompte;
        this.type = type;
        this.montant = montant;
        this.date = date;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getIdcompte(){
        return idcompte;
    }
    public void setIdcompte(int idcompte){
        this.idcompte = idcompte;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public double getMontant(){
        return montant;
    }
    public void setMontant(double montant){
        this.montant = montant;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
}