import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CorsaCavalli {
    public static void main(String[] args) {
        List<Cavallo> classifica = new ArrayList<>(); // Lista sincronizzata per l'ordine di arrivo

        try (Scanner scanner = new Scanner(System.in)) {
            // Richiesta della lunghezza del percorso
            System.out.print("Inserisci la lunghezza del percorso della gara (in metri): ");
            int lunghezzaPercorso = scanner.nextInt();

            // Richiesta del numero di cavalli partecipanti
            System.out.print("Inserisci il numero di cavalli che partecipano alla gara: ");
            int numeroCavalli = scanner.nextInt();
            scanner.nextLine();

            List<Thread> threads = new ArrayList<>();

            // Creazione dei cavalli con nome e velocità
            for (int i = 1; i <= numeroCavalli; i++) {
                System.out.print("Inserisci il nome del cavallo " + i + ": ");
                String nomeCavallo = scanner.nextLine();
                System.out.print("Inserisci la velocità del cavallo " + i + " (in m/s): ");
                int velocitaCavallo = scanner.nextInt();
                scanner.nextLine();
                Cavallo cavallo = new Cavallo(nomeCavallo, lunghezzaPercorso, velocitaCavallo, classifica);
                threads.add(new Thread(cavallo));
            }

            System.out.println("La gara sta per iniziare");

            // Avvio dei thread dei cavalli
            for (Thread thread : threads) {
                thread.start();
            }

            // Attesa che tutti i thread terminino
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Stampa della classifica finale basata sull'ordine di arrivo
            System.out.println("Classifica finale:");
            for (int i = 0; i < Math.min(3, classifica.size()); i++) {
                System.out.println((i + 1) + ". " + classifica.get(i).getNome() + " | " + classifica.get(i).getDistanzaPercorsa() + " metri");
            }

            // Salvataggio dei risultati in un file
            System.out.print("Inserisci il nome del file in cui salvare i risultati: ");
            String nomeFile = scanner.nextLine();

            try (FileWriter writer = new FileWriter(nomeFile, true)) {
                writer.write("Classifica finale:\n");
                for (int i = 0; i < Math.min(3, classifica.size()); i++) {
                    writer.write((i + 1) + ". " + classifica.get(i).getNome() + " | " + classifica.get(i).getDistanzaPercorsa() + " metri\n");
                }
                writer.write("-----------\n");
                System.out.println("Risultati salvati su " + nomeFile);
            } catch (IOException e) {
                System.out.println("Errore durante il salvataggio del file: " + e.getMessage());
            }
        }

        System.out.println("La gara è terminata");
    }
}
