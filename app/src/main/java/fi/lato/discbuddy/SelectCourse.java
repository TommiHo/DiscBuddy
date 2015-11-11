package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Lasse on 10.11.2015.
 */
public class SelectCourse extends Activity {
    private final String DATABASE_TABLE = "courses";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_course);

        // find list view
        listView = (ListView)  findViewById(R.id.listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        // get data with own made queryData method
        queryData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("Cursor", DatabaseUtils.dumpCurrentRowToString(cursor));

                //get cursor row data
                String course = cursor.getString(cursor.getColumnIndex("name"));

                // create an explicit intent
                Intent intent = new Intent(SelectCourse.this, SelectPlayers.class);

                // add data to intent
                intent.putExtra("course", course);

                // start a new activity
                startActivity(intent);
            }
        });

    }

    // query data from database
    public void queryData() {
        //cursor = db.rawQuery("SELECT _id, name, score FROM highscores ORDER BY score DESC", null);
        // get data with query
        String[] resultColumns = new String[]{"_id","name"};
        cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,null,null);

        // add data to adapter
        final ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.course_list_item, cursor,
                new String[] {"name"},      // from
                new int[] {R.id.name}    // to
                ,0);  // flags

        // show data in listView
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // close cursor and db connection
        cursor.close();
        db.close();
    }
}