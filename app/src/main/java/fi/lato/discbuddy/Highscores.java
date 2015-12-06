package fi.lato.discbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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
        highscore.reArrange(this);

        ListView listView = (ListView) findViewById(R.id.listView2);

        HighscoreAdapter adapter = new HighscoreAdapter(this,highscore.getList());

        listView.setAdapter(adapter);
    }

}
