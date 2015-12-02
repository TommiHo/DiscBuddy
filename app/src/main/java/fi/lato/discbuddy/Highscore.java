package fi.lato.discbuddy;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tommi on 2.12.2015.
 */
public class Highscore extends Activity implements Serializable {
    private ArrayList<Player> players;
    private final String fileName = "highscore.data";


    public Highscore() {
        this.players = new ArrayList();
        alustaLista();
    }

    // tallennetaan highscorelista tiedostoon
    public  void saveScores() {
        // Kalojen Tallennus tiedostoon
        ObjectOutputStream output = null;
        try {

            output = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            output.writeObject(players);
            Log.v(String.valueOf(output), "tallennus onnistui");
        } catch (IOException ex) {
            Log.v(String.valueOf(ex), "Virhe tiedostoon kirjoittamisessa");
        } finally {
            try {
                if (output != null) output.close();

            } catch (IOException ex) {
                Log.v(String.valueOf(ex),"Virhe tiedoston sulkemissa");
            }
        }
    }
    // Highscoren Lukeminen tiedostosta
    public void readScores() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(new File(fileName)));
            players = (ArrayList<Player>) input.readObject();
        } catch (IOException ex) {
            System.out.println("Virhe tiedoston lukemisessa :" + ex);
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException ex) {
                System.out.println("Virhe tiedoston sulkemisessa : " + ex);
            }
        }
        // debuggausta konsolille
        System.out.println("Highscore lista luettu uudelleen tiedostosta.");
        //tulosta();
    }
    // luetaan tiedostosta, tai luodaan oletuskalat
    public void alustaLista() {
        File f = new File(fileName);
        if (!f.exists()) {
            players.add(new Player("Hauki", 1));

        } else {
           readScores();
        }

        tulosta();
    }
    public void tulosta() {
        for (Player k : players) {
            System.out.println(k);
        }
    }
}