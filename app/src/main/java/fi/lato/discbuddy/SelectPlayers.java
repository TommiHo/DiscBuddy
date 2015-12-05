package fi.lato.discbuddy;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tommi on 10.11.2015.
 */
public class SelectPlayers extends Activity implements AddPlayerDialogFragment.DialogListener {
    private final String DATABASE_TABLE = "players";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;
    public static ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_players);
        players = new ArrayList<Player>();

        // update text views to show data
        TextView textView = (TextView) findViewById(R.id.courseName);
        textView.setText(SelectCourse.course.toString());

        // find list view
        listView = (ListView)  findViewById(R.id.listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(listView);

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
                R.layout.list_item, cursor,
                new String[] {"name"},      // from
                new int[] {R.id.name}    // to
                ,0);  // flags

        // show data in listView
        listView.setAdapter(adapter);
    }

    public void startGame(View view) {
        Intent intent = new Intent(SelectPlayers.this, InputScores.class);
        startActivity(intent);
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            String playerName = ((CheckBox) v).getText().toString();
            players.add(new Player(playerName, 0));
        }else {
            String playerName = ((CheckBox) v).getText().toString();
            Iterator<Player> it = players.iterator();
            while(it.hasNext()){
                if(it.next().getName().equals(playerName)){
                    it.remove();
                }
            }
        }
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
                AddPlayerDialogFragment eDialog = new AddPlayerDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new player");
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        ContentValues values=new ContentValues(2);
        values.put("name", name);
        db.insert("players", null, values);
        // get data again
        queryData();
        players.clear();
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
                db.delete("player", "_id=?", args);
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
