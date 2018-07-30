package util;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;
 
import javax.swing.SwingUtilities;

import javafx.stage.Stage;
 
/**
 * https://www.developpez.net/forums/d148068/java/general-java/seule-instance-d-meme-programme-java/
 * https://www.developpez.net/forums/anocode.php?id=7a039645038e355111ae1a358f3dad73
 * @author rom1v
 */
@SuppressWarnings("all")
public final class UniqueInstanceTester {
 
    /** Pour utilisé pour l'unique instance de MyFreeTV. */
    private static final int PORT = 27321;
 
    /** Interdiction d'instancier. */
    private UniqueInstanceTester() {}
 
    /**
     * Donne le focus à l'éventuelle application MyFreeTV déjà lancée.
     * 
     * @return {@code true} si aucune autre instance de MyFreeTV n'existe.
     */
    public static boolean launch(Stage stage) {
 
        /* Indique si l'instance de MyFreeTV est unique. */
        boolean unique;
 
        try {
            /* On crée une socket sur le port défini. */
            final ServerSocket server = new ServerSocket(PORT);
 
            /* Si la création de la socket réussi, c'est que l'instance de MyFreeTV est unique, aucune autre n'existe. */
            unique = true;
 
            /* On lance un Thread d'écoute sur ce port. */
            Thread portListenerThread = new Thread() {
 
                @Override public void run() {
                    /* Tant que l'application MyFreeTV est lancée... */
                    while(true) {
                        try {
                            /* On attend qu'une socket se connecte sur le serveur. */
                            final Socket socket = server.accept();
 
                            /* Si une socket est connectée, on écoute le message envoyé dans un nouveau Thrad. *TODO/
                            new Thread() {
 
                                @Override public void run() {
                                    receive(socket,stage);
                                }
                            }.start();*/
                            socket.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
 
            /* Le Thread d'écoute de port est démon. */
            portListenerThread.setDaemon(true);
 
            /* On démarre le Thread. */
            portListenerThread.start();
        } catch(IOException e) {
            /* Si la création de la socket échoue, c'est que l'instance de MyFreeTV n'est unique, une autre n'existe. */
            unique = false;
 
            /* Dans ce cas, on envoie un message à l'autre application pour lui demander d'avoir le focus. */
            //TODO send();
        }
        return unique;
    }
 
    /**
     * Envoie un message à l'instance de l'application déjà ouverte.
     */
    private static void send() {
        PrintWriter pw = null;
        try {
            /* On se connecte sur l'ip locale. */
            Socket socket = new Socket("localhost", PORT);
 
            /* On définit un PrintWriter pour écrire sur la sortie de la socket. */
            pw = new PrintWriter(socket.getOutputStream());
 
            /* On écrit "myfreetv" sur la socket. */
            pw.write("myfreetv");
        } catch(IOException e) {
            Logger.getLogger("MyFreeTV").warning("Écriture sur flux de sortie de la socket échoué.");
        } finally {
            if(pw != null)
                pw.close();
        }
    }
 
    /**
     * Reçoit un message d'une socket s'étant connectée au serveur d'écoute. Si ce message est "{@code myfreetv}",
     * l'application demande le focus.
     * 
     * @param socket
     *            Socket connecté au serveur d'écoute.
     * @param stage 
     */
    private static void receive(Socket socket, Stage stage) {
        Scanner sc = null;
 
        try {
            /* On n'écoute que 5 secondes, si aucun message n'est reçu, tant pis... */
            socket.setSoTimeout(5000);
 
            /* On définit un Scanner pour lire sur l'entrée de la socket. */
            sc = new Scanner(socket.getInputStream());
 
            /* On ne lit qu'une ligne. */
            String s = sc.nextLine();
 
            /* Si cette ligne est "myfreetv"... */
            if("myfreetv".equals(s)) {
 
                /* On demande le focus dans l'EDT. */
                SwingUtilities.invokeLater(new Runnable() {
 
                    public void run() {
 
                        /*
                         * La fenêtre peut ne pas être encore ouverte (très peu probable), par exemple au tout début du
                         * démarrage de l'application.
                         */
                        if(stage != null) {
                            stage.toFront();//FIXME
                        }
                    }
                });
            }
        } catch(IOException e) {
            Logger.getLogger("MyFreeTV").warning("Lecture du flux d'entrée de la socket échoué.");
        } finally {
            if(sc != null)
                sc.close();
        }
    }
 
}
