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
        int numOfChairs = 3;
        Semaphore availableChairs = new Semaphore(numOfChairs);
        Semaphore availableTa = new Semaphore(1);
        Scanner scanner = new Scanner(System.in);
        System.out.println("entrez le nombre d'etudiant");
        int n = scanner.nextInt();
        // crée un TA
        TA ta = new TA(availableChairs, availableTa);
        ta.start();
        // crée les fils etudiants
        for (int i = 0; i < n; i++) {
            Student student = new Student(i, availableChairs, availableTa);
            student.start();
        }
        scanner.close();
    }

}

class Student extends Thread {

    private int id;
    private Semaphore availableChairs;
    private Semaphore availableTa;

    public Student(int id, Semaphore availableChairs, Semaphore availableTA) {
        this.id = id;
        this.availableChairs = availableChairs;
        this.availableTa = availableTA;
    }

    public void run() {
        System.out.println("Student" + id + "is programming");
        // check if the ta is avalaible
        if(availableTa.tryAcquire()){
            System.out.println("Student" + id + "is getting help from the TA");
            availableTa.release();
        } else if(availableChairs.tryAcquire()){
            System.out.println("Student" + id + "is waiting on a chair help from the TA");
            if(availableTa.tryAcquire()){
                availableChairs.release();
                System.out.println("Student" + id + "is getting help from the TA");
                availableTa.release();
            }
        }

        // else check if a chair is avalaible

        // if not then program again
    }
}

class TA extends Thread {

    private Semaphore availableChairs;
    private Semaphore availableTa;

    public TA(Semaphore availableChairs, Semaphore availableTA) {
        this.availableChairs = availableChairs;
        this.availableTa = availableTA;
    }

    public void run() {
        if(availableTa.availablePermits() == 1){
            System.out.println("TA sleeping");
        }
        // student wakes ta
        System.out.println("TA waken up to help");
        // check if anyone is in chair
        if (availableChairs.availablePermits() == 3) {
            System.out.println("TA Back to sleep");
        } else {
            System.out.println("TA still helping");
        }
       
    }
}