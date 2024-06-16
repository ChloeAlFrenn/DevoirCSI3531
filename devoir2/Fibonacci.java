// To run the program, use the commands javac Fibonacci.java and java Fibonacci (your number)
//package devoir2;
//Nom: Chloe Al-Frenn, Chada Bendriss
//Numero d'etudiant:  300211508, 300266679
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
        int[] sequence = new int[nombre];

        // lance un fil
        ThreadFibonacci threadFibonacci = new ThreadFibonacci(nombre, sequence);
        threadFibonacci.start();

        try {
            threadFibonacci.join(); // Attend la fin du fil enfant
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Afficher la séquence de Fibonacci recue par l'enfant
        System.out.println("Séquence de Fibonacci:");
        for (int i = 0; i < nombre; i++) {
            System.out.println(sequence[i]);
        }

    }

}

class ThreadFibonacci extends Thread { // fil enfant
    // espace memoire partagé par les fils
    private int[] sequence;
    private int n;

    public ThreadFibonacci(int n, int[] sequence) { // recoit le tableau a remplir et le nombre de fibonnaci n
        this.n = n;
        this.sequence = sequence;
    }

    public void run() { // rempli le tableau

        if (n >= 1)
            sequence[0] = 0;
        if (n >= 2)
            sequence[1] = 1;

        for (int i = 2; i < n; i++) {
            sequence[i] = sequence[i - 1] + sequence[i - 2];
        }
    }
}