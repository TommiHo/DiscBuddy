package fi.lato.discbuddy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lasse on 22.11.2015.
 */
public class InputScoresSlideFragment extends Fragment {
    private Cursor cursor;
    private ListView scores_listView;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.input_scores, container, false);
        // get intent which has used to open this activity

        // update text views to show data
        TextView textView = (TextView) rootView.findViewById(R.id.courseTextView);
        textView.setText(SelectCourse.course);

        // find list view
        scores_listView = (ListView)  rootView.findViewById(R.id.scores_listView);

        // get database instance
        db = (new DatabaseOpenHelper(rootView.getContext())).getWritableDatabase();
        // get data with own made queryData method
        queryData(InputScores.playerNames);
        return rootView;
    }

    // query data from database
    public void queryData(ArrayList<String> playerNames) {
        String[] names = new String[playerNames.size()];
        names = playerNames.toArray(names);
        //names =playerNames.to
        cursor = db.rawQuery("SELECT _id, name FROM players WHERE name IN (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", names);
        // add data to adapter
        ListAdapter adapter = new SimpleCursorAdapter(scores_listView.getContext(),
                R.layout.score_list_item, cursor,
                new String[] {"name"},      // from
                new int[] {R.id.name}    // to
                ,0);  // flags

        // show data in listView
        scores_listView.setAdapter(adapter);
        Log.v("Cursor", DatabaseUtils.dumpCursorToString(cursor));
    }

    // tuloksien syöttäminen
    public void plusbuttonClick(View v){
        int position = scores_listView.getPositionForView(v);
        TextView tv = (TextView) scores_listView.getChildAt(position).findViewById(R.id.par);
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+1));
    }

    public void minusbuttonClick(View v){
        int position = scores_listView.getPositionForView(v);
        TextView tv = (TextView) scores_listView.getChildAt(position).findViewById(R.id.par);
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())-1));
    }

}