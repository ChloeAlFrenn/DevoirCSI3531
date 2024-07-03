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
        Semaphore studentReady = new Semaphore(0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("entrez le nombre d'etudiant");
        int n = scanner.nextInt();
        // crée un TA
        TA ta = new TA(availableChairs, availableTa, studentReady);
        ta.start();
        // crée les fils etudiants
        for (int i = 0; i < n; i++) {
            Student student = new Student(i+1, availableChairs, availableTa, studentReady);
            student.start();
        }
        scanner.close();
    }

}

class Student extends Thread {

    private int id;
    private Semaphore availableChairs;
    private Semaphore availableTa;
    private Semaphore studentReady;

    public Student(int id, Semaphore availableChairs, Semaphore availableTa, Semaphore studentReady) {
        this.id = id;
        this.availableChairs = availableChairs;
        this.availableTa = availableTa;
        this.studentReady = studentReady;
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Student " + id + " is programming");
                Thread.sleep((int) (Math.random() * 5000));
                // Check TA is available
                System.out.println("Student " + id + " will go ask for help");
                studentReady.release(); //student is ready
                if (availableTa.tryAcquire()) {
                    System.out.println("Student " + id + " is getting help from the TA");
                    Thread.sleep(1000); // Same help time as ta
                    availableTa.release();
                } else if (availableChairs.tryAcquire()) {
                    System.out.println("Student " + id + " is waiting on a chair for help");
                    studentReady.release(); //student is ready
                    availableTa.acquire(); // wait for the TA to become available
                    availableChairs.release(); // Leave the chair
                    System.out.println("Student " + id + " is getting help from the TA");
                    Thread.sleep(1000); // Same help time as ta
                    availableTa.release();
                } else {
                    System.out.println("Student " + id + " found no chair so will program");
                }
                Thread.sleep(2000); // programming
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TA extends Thread {

    private Semaphore availableChairs;
    private Semaphore availableTa;
    private Semaphore studentReady;

    public TA(Semaphore availableChairs, Semaphore availableTa, Semaphore studentReady) {
        this.availableChairs = availableChairs;
        this.availableTa = availableTa;
        this.studentReady = studentReady;
    }

    public void run() {
        try {
            while (true) {
                if (availableTa.availablePermits() == 1 && availableChairs.availablePermits() == 3) {
                    System.out.println("TA sleeping");
                }
                studentReady.acquire(); // Wait for a student to be ready when they do studentReady.release()
                System.out.println("TA helping a student");
                // Help the student
                Thread.sleep(1000); // Same help time as student
                System.out.println("TA finished helping a student");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
