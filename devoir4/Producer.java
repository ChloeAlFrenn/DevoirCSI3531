//Nom: Chloe Al-Frenn, Chada Bendriss
//Numero d'etudiant:  300211508, 300266679

//Compilez les deux programmes
//javac Producer.java
//javac Consumer.java
//java Producer (number)
//java Consumer

//Le programme consommateur se connecte au producteur via une socket

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Producer {
    public static long catalan(int n) {
        
        if (n <= 1) {
            return 1;
        }
        long[] catalan = new long[n + 1];
        catalan[0] = 1;
        catalan[1] = 1;

        for (int i = 2; i <= n; i++) {
            catalan[i] = 0;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }
        return catalan[n];
    }

    public static void main(String[] args) {

        //Verifie que le nombre d'arguments est valide

        int n = Integer.parseInt(args[0]);
        if (n <= 0) {
            System.err.println("Please enter a positive integer: ");
            System.exit(1);
        }

        List<Long> catalanNumbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            catalanNumbers.add(catalan(i));
        }

        //Cree un ServerSocket pour écouter les connexions sur le port 12345.

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Producer waiting for connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established with consumer.");

            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(catalanNumbers);
                System.out.println("Catalan numbers sent to consumer.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
