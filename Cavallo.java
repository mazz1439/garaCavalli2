import java.util.List;
import java.util.Random;

public class Cavallo implements Runnable {
    private String nome; // Nome del cavallo
    private int lunghezzaPercorso; // Lunghezza totale del percorso della gara
    private int distanzaPercorsa = 0; // Distanza attuale percorsa dal cavallo
    private int velocita; // Velocità del cavallo in metri al secondo
    private boolean inGara = true; // Stato del cavallo (in gara o ritirato)
    private Random random = new Random(); // Oggetto Random per generare numeri casuali
    private List<Cavallo> classifica; // Riferimento alla lista per la classifica

    // Costruttore che inizializza il nome, la lunghezza del percorso, la velocità e la lista per la classifica
    public Cavallo(String nome, int lunghezzaPercorso, int velocita, List<Cavallo> classifica) {
        this.nome = nome;
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.velocita = velocita;
        this.classifica = classifica;
    }

    public String getNome() {
        return nome;
    }

    public int getDistanzaPercorsa() {
        return distanzaPercorsa;
    }

    public boolean isInGara() {
        return inGara;
    }

    // Metodo run che viene eseguito quando il thread del cavallo inizia
    @Override
    public void run() {
        System.out.println(nome + " è pronto con velocità " + velocita + " m/s!");

        while (distanzaPercorsa < lunghezzaPercorso && inGara) {
            // Simula possibilità di infortunio (5% di probabilità)
            if (random.nextInt(500) < 10) {
                inGara = false;
                System.out.println(nome + " si è infortunato");
                break;
            }

            // Aggiunge la velocità alla distanza percorsa
            distanzaPercorsa += velocita;

            if (distanzaPercorsa > lunghezzaPercorso) {
                distanzaPercorsa = lunghezzaPercorso;
            }

            System.out.println(nome + " ha percorso " + distanzaPercorsa + " metri.");

            try {
                Thread.sleep(1000); // Pausa di 1 secondo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (inGara) {
            System.out.println(nome + " ha concluso la gara!");
            synchronized (classifica) {
                classifica.add(this); // Aggiunge il cavallo alla classifica in ordine di arrivo
            }
        }
    }
}
