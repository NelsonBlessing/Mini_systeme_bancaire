package Utlis;

import Interfaces.Transfer;

public class Session {
    public static int idClient;
    private static String telephone;


    public static void setIdClient(int id) {
        idClient = id;
    }
    public static int getIdClient() {
        return idClient;
    }
    public static void clear() {
        idClient = 0;
        telephone = null;
    }
    public static boolean isConnected(){
        return idClient > 0;
    }
    public static void setTelephone(String tel){
        telephone = tel;
    }
    public static String getTelephone() {
        return telephone;
    }

}
