package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tommi on 2.12.2015.
 */
public class Highscore extends Activity implements Serializable {
    private ArrayList<Player> players;
    private final String fileName = "highscore.data";
    private File path;

    public Highscore() {
        this.players = new ArrayList();
        players.add(new Player("jussi",100));
        //alustaLista();
    }

    // tallennetaan highscorelista tiedostoon
    public  void saveScores(Context ctx) {
        // Pelaajien Tallennus tiedostoon
        ObjectOutputStream output = null;
        try {
            path = ctx.getFilesDir();
            File file = new File(path, "/" + fileName);
            output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(SelectPlayers.players);
            Log.v(String.valueOf(output), "tallennus onnistui");
            Log.v("Testi", path.toString());
        } catch (IOException ex) {
            Log.v(String.valueOf(ex), "Virhe tiedostoon kirjoittamisessa");
        } finally {
            try {
                if (output != null) output.close();

            } catch (IOException ex) {
                Log.v(String.valueOf(ex), "Virhe tiedoston sulkemissa");
            }
        }
    }
    // Highscoren Lukeminen tiedostosta
    public void readScores(Context ctx) {
        ObjectInputStream input = null;
        try {
            path = ctx.getFilesDir();
            File file = new File(path, "/" + fileName);
            input = new ObjectInputStream(new FileInputStream(file));
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

    public void addNew(Player player,Context ctx) {
        players.add(player);
        saveScores(ctx);
    }

    public ArrayList getList(){
        return this.players;
    }

    // luetaan tiedostosta
    public void formatList(Context ctx) {
        File f = new File(fileName);
        if (!f.exists()) {

        } else {
           readScores(ctx);
        }

        print();
    }
    public void print() {
        Log.d("tulostetaan....", "ja noin");
        for (Player p : players) {
            Log.d("pelaaja", p.getName());
        }
    }

    // Pelaajien järjestäminen (sort) uudelleen
    public void reArrange(Context ctx) {
        Collections.sort(players, new SortPlayers());
        System.out.println("Highscorelista on järjestetty pisteiden mukaan.");
        saveScores(ctx);
    }

    class SortPlayers implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            if (p1.getSum() < p2.getSum()) return -1;
            if (p2.getSum() > p1.getSum()) return 1;
            return 0;
        }
    }
}