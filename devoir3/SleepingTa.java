package devoir3;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

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


    private int id;
    private static Semaphore chairsMutex = new Semaphore(1);
    private static Semaphore students = new Semaphore(0);
    private static Semaphore ta = new Semaphore(0);
    private static int waitingStudents = 0;
    private static final int NUM_CHAIRS = 3;

    public Student(int id) {
        this.id = id;
    }


    

    public void run() {
        System.out.println("Student");
      }
}

class TA extends Thread {


    private int port = 0;  // On commence au port 0
    private int charge = 0;  // Aucune charge au debut
    private int nombreTours;  // nombres de tours a executer
    // Semaphores
    public Semaphore semEmbPort0;  // Semaphore pour embarquer au port 0
    public Semaphore semEmbPort1;  // Semaphore pour embarquer au port 1
    public Semaphore semDebarque;  // Semaphore pour debarquer 
    public Semaphore semDepart;    // Semaphore pour le depart du traversier

    public TA(int prt, int nbtours) {
        this.port = prt;
        semEmbPort0 = new Semaphore(0, true);
        semEmbPort1 = new Semaphore(0, true);
        semDebarque = new Semaphore(0, true);
        semDepart = new Semaphore(0, true);
        nombreTours = nbtours;
    }


    public void run() {
        System.out.println("TA");
      }
}