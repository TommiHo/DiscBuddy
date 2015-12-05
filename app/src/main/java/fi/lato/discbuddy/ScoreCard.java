package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Lasse on 27.11.2015.
 */
public class ScoreCard extends Activity {
    private Highscore highscore;
    View score_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_card);
        TableLayout table = (TableLayout) findViewById(R.id.scoresTable);
        TableRow nameRow = (TableRow) findViewById(R.id.nameRow);
        TextView hole = new TextView(this);
        hole.setText("hole");
        nameRow.addView(hole);
        //add player names to table
        TextView tekstView = (TextView) findViewById(R.id.courseName);
        tekstView.setText(SelectCourse.course);
        highscore = new Highscore();
        highscore.saveScores(this);

        for(Player player : SelectPlayers.players){
            highscore.addNew(player,this);
            TextView t = new TextView(this);
            t.setText(player.getName());
            nameRow.addView(t);
        }
        //table.addView(nameRow);
        //add scores to table
        Bundle extras = getIntent().getExtras();
        ArrayList scores = extras.getIntegerArrayList("scores");
        Iterator<int[]> it = scores.iterator();
        int j = 1;
        while (it.hasNext()){
            int[] score = it.next();
            TableRow row = new TableRow(this);
            for(int i = -1;i<score.length; i++){
                TextView t = new TextView(this);
                if(i==-1)t.setText(j+"");
                else {
                    t.setText(score[i] + "");
                    switch (score[i]){
                        case -1:
                            t.setBackgroundColor(0xFF00FF00);
                            break;
                        case 1:
                            t.setBackgroundColor(0xFFFF0000);
                    }

                }
                row.addView(t);
            }
            table.addView(row);
            j++;
        }
        TableRow rowSum = new TableRow(this);
        TextView t = new TextView(this);
        t.setText("Total: ");
        rowSum.addView(t);
        for(Player player : SelectPlayers.players){
            TextView tv = new TextView(this);
            tv.setText(player.getSum()+"");
            rowSum.addView(tv);
        }
        table.addView(rowSum);
    }



    @Override
    public void onBackPressed() {
        // Navigate main-activity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_scorecard, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
