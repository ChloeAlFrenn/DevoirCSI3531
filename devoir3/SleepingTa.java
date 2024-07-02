package devoir3;

import java.util.Scanner;

/**
 * Devoir 3 The Sleeping Ta
 * Par: Chloé Al-Frenn et Chada Bendriss
 * Numero d'etudiant: 300211508, 300266679
 */
public class SleepingTa {

    public static void main(String[] args) {
        int waitingChairs = 3;
        Scanner scanner = new Scanner(System.in);
        System.out.println("entrez le nombre d'etudiant");
        int n = scanner.nextInt();
        // crée un TA
        TA ta = new TA();
        ta.start();
        // crée les fils etudiants
        for ( int i = 0; i < n; i++) {
            Student student = new Student();
            student.start();
        }
    }

}

class Student extends Thread {
    public void run() {
        System.out.println("Student");
      }
}

class TA extends Thread {
    public void run() {
        System.out.println("TA");
      }
}