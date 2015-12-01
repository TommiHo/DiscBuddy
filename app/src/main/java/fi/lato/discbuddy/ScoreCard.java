package fi.lato.discbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Lasse on 27.11.2015.
 */
public class ScoreCard extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_card);
        TableLayout table = (TableLayout) findViewById(R.id.scoresTable);
        TableRow nameRow = new TableRow(this);
        TextView hole = new TextView(this);
        hole.setText("hole");
        nameRow.addView(hole);

        for(Player player : SelectPlayers.players){

            TextView t = new TextView(this);
            t.setText(player.getName());
            nameRow.addView(t);

        }
        table.addView(nameRow);
        Bundle extras = getIntent().getExtras();
        ArrayList scores = extras.getIntegerArrayList("scores");
        Iterator<int[]> it = scores.iterator();
        int j = 1;
        while (it.hasNext()){
            int[] score = it.next();
            Log.d("luvut", Arrays.toString(score));
            TableRow row = new TableRow(this);
            for(int i = -1;i<score.length; i++){
                TextView t = new TextView(this);
                if(i==-1)t.setText(j+"");
                else t.setText(score[i] + "");
                row.addView(t);
            }
            table.addView(row);
            j++;
        }


    }
}
