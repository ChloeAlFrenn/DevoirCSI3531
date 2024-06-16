// To run the program, use the commands javac PrimeNumber.java and java PrimeNumber
//Nom: Chloe Al-Frenn, Chada Bendriss
//Numero d'etudiant:  300211508, 300266679
import java.util.Scanner;

//Étend la classe Thread
class Worker extends Thread{
    private int number=0;
    private boolean isPrime=false;

    public Worker(int number){
        this.number=number;
        
    }

    public void run() {
        for (int i = 2; i <= number; i++) {
            isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.println(i);
            }
        }
    }
}
public class PrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Le programme demande a l utilisateur d entrer un nombre
        System.out.print("Entrez un nombre : ");
        int number = scanner.nextInt();
        scanner.close();
        Worker primeThread = new Worker(number);
        //Les fils Java sont crées avec un appel à la méthode start() de la classe Worker
        primeThread.start();
        //Le programme crée un fil séparé qui affiche tous les nombres premiers < ou = au donné.
    }
}
