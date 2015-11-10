package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by tommi on 10.11.2015.
 */
public class InputScores extends Activity {

    private final String DATABASE_TABLE = "player";
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView scores_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_scores);


        // find list view
        scores_listView = (ListView)  findViewById(R.id.scores_listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(scores_listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        // get data with own made queryData method
        queryData();
    }

    // query data from database
    public void queryData() {
        //cursor = db.rawQuery("SELECT _id, name, score FROM highscores ORDER BY score DESC", null);
        // get data with query
        String[] resultColumns = new String[]{"_id","name"};
        cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,null);

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
