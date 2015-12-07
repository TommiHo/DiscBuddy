package fi.lato.discbuddy;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Lasse on 10.11.2015.
 */
public class SelectCourse extends Activity implements AddCourseDialogFragment.DialogListener{
    private final String DATABASE_TABLE = "courses";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;
    public static String course;
    public static int holeCount;

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
        //Log.v("Cursor", DatabaseUtils.dumpCursorToString(cursor));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Cursor", DatabaseUtils.dumpCurrentRowToString(cursor));

                //get cursor row data
                //String course = cursor.getString(cursor.getColumnIndex("name"));

                // create an explicit intent
                Intent intent = new Intent(SelectCourse.this, SelectPlayers.class);

                // add data to intent
                //intent.putExtra("course", course);
                course = cursor.getString(cursor.getColumnIndex("name")).toString();
                holeCount = cursor.getInt(cursor.getColumnIndex("holeCount"));
                // start a new activity
                startActivity(intent);
                finish();
            }
        });

    }

    // query data from database
    public void queryData() {
        //cursor = db.rawQuery("SELECT _id, name, score FROM highscores ORDER BY score DESC", null);
        // get data with query
        String[] resultColumns = new String[]{"_id","name","par","holeCount"};
        cursor = db.query(DATABASE_TABLE, resultColumns, null, null, null, null, null, null);

        // add data to adapter
        final ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.course_list_item, cursor,
                new String[] {"name", "par","holeCount"},      // from
                new int[] {R.id.name, R.id.par, R.id.holeCount}    // to
                ,0);  // flags

        // show data in listView
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handles item selections
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddCourseDialogFragment eDialog = new AddCourseDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new course");
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, int par, int holeCount) {
        ContentValues values=new ContentValues(3);
        values.put("name", name);
        values.put("par", par);
        values.put("holeCount", holeCount);
        db.insert("courses", null, values);
        // get data again
        queryData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("courses", "_id=?", args);
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // close cursor and db connection
        cursor.close();
        db.close();
    }
}