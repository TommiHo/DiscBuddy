package fi.lato.discbuddy;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Lasse on 5.12.2015.
 */
public class Highscores extends Activity {
    private Highscore highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        highscore = new Highscore();
        highscore.readScores(this);
        highscore.tulosta();


    }


}
