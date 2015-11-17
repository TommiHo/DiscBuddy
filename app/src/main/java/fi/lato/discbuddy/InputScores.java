package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class InputScores extends Activity {

    private final String DATABASE_PLAYERS = "players";
    private final String DATABASE_SCORES = "scores";
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView scores_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_scores);

        // get intent which has used to open this activity
        Intent intent = getIntent();
        // get data from intent
        Bundle bundle = intent.getExtras();
        // get course
        String course = bundle.getString("course");

        // update text views to show data
        TextView textView = (TextView) findViewById(R.id.courseTextView);
        textView.setText(course);

        // find list view
        scores_listView = (ListView)  findViewById(R.id.scores_listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(scores_listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        // get data with own made queryData method
        queryData();
    }


        // tuloksien syöttäminen
    public void plusbuttonClick(View v){
        TextView textView = (TextView) findViewById(R.id.par);
        textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString())+1));
    }

    public void minusbuttonClick(View v){
        TextView textView = (TextView) findViewById(R.id.par);
        textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString())-1));
    }

    // query data from database
    public void queryData() {
        //cursor = db.rawQuery("SELECT _id, name, score FROM highscores ORDER BY score DESC", null);
        // get data with query
        String[] resultColumns = new String[]{"_id","name"};
        String[] scoreColumns = new String[]{"_id","hole","holeScore","courseScore",""};
        cursor = db.query(DATABASE_PLAYERS,resultColumns,null,null,null,null,null);
        cursor = db.query(DATABASE_SCORES,scoreColumns,null,null,null,null,null);
        // add data to adapter
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.score_list_item, cursor,
                new String[] {"name"},      // from
                new int[] {R.id.name}    // to
                ,0);  // flags

        // show data in listView
        scores_listView.setAdapter(adapter);
    }

}
