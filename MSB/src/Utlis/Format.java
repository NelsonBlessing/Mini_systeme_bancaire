package Utlis;

public class Format {
    public static String formatTelephone(String numero){

        if (numero == null || numero.trim().isEmpty()) {
            return null;
        }

        numero = numero.trim();

        if (numero.startsWith("+257")) {
               return numero;
        } else if (numero.startsWith("0")) {
            return "+257" + numero.substring(1);
        } else if (numero.length() == 8 && numero.matches("\\d+")) {
            return "+257" + numero;
        }

       return null;
    }
}