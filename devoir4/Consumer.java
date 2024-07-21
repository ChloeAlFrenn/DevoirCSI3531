//Nom: Chloe Al-Frenn, Chada Bendriss
//Numero d'etudiant:  300211508, 300266679

//Compilez les deux programmes
//javac Producer.java
//javac Consumer.java
//java Producer (number)
//java Consumer

// Ce programme producteur genere une séquence de nombres de Catalan 
//jusqu'a un nombre specifie par l'utilisateur.


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class Consumer {
    
    public static void main(String[] args) {

        // Cree un Socket pour se connecter au producteur sur le port 12345 
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Connected to producer.");

            // Utilise un ObjectInputStream pour recevoir la liste des nombres de Catalan envoyée par le producteur.
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                @SuppressWarnings("unchecked")
                List<Long> catalanNumbers = (List<Long>) in.readObject();
                System.out.println("Received Catalan numbers:");
                for (Long number : catalanNumbers) {

                    // Affiche les nombres de Catalan reçus.
                    System.out.print(number + " ");
                }
                System.out.println();
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); // Affiche les erreurs
            }

        } catch (IOException e) {
            e.printStackTrace(); // Affiche les erreurs
        }
    }
}
