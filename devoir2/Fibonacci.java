package devoir2;

public class Fibonacci {

    public static void main(String[] args) {
        int nombre;
        if (args.length != 1) {
            System.out.println("Il faut entrer un seul argument");
            return;
        }

        try {
            nombre = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Il faut entrer un nombre\nNumberFormatException: " + nfe.getMessage());
            return;
        }

        if (nombre < 0) {
            System.out.println("Le nombre doit etre positif");
            return;
        }

        System.out.println("le nombre est:" + nombre);

    }

}
