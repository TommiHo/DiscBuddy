package fi.lato.discbuddy;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tommi on 10.11.2015.
 */
public class InputScores extends Activity {

    private final String DATABASE_PLAYERS = "players";
    private final String DATABASE_SCORES = "scores";
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView scores_listView;
    public static ArrayList playerNames;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_scores_pager);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // get intent which has used to open this activity
        Intent intent = getIntent();
        // get data from intent
        Bundle bundle = intent.getExtras();
        // get course
        playerNames = bundle.getStringArrayList("data");

        // update text views to show data
        TextView textView = (TextView) findViewById(R.id.courseTextView);
        //textView.setText(playerName);

        // find list view
        scores_listView = (ListView)  findViewById(R.id.scores_listView);
        // register listView's context menu (to delete row)
        //registerForContextMenu(scores_listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        // get data with own made queryData method
        queryData(playerNames);
        //Toast.makeText(getApplicationContext(), playerNames.get(0).toString(), Toast.LENGTH_SHORT).show();
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

    // query data from database
    public void queryData(ArrayList<String> playerNames) {
        String[] names = new String[playerNames.size()];
        names = playerNames.toArray(names);
        //names =playerNames.to
        cursor = db.rawQuery("SELECT _id, name FROM players WHERE name IN (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", names);
        // get data with query
        String[] resultColumns = new String[]{"_id","name"};
        //String[] scoreColumns = new String[]{"_id","hole","holeScore","courseScore","_id"};
        cursor = db.query(DATABASE_PLAYERS,resultColumns,null,null,null,null,null);
        //cursor = db.query(DATABASE_SCORES,scoreColumns,null,null,null,null,null);
        // add data to adapter
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.score_list_item, cursor,
                new String[] {"name"},      // from
                new int[] {R.id.name}    // to
                ,0);  // flags

        // show data in listView
        //scores_listView.setAdapter(adapter);
        Log.v("Cursor", DatabaseUtils.dumpCursorToString(cursor));
    }

    public void onPageScrollStateChanged(int state, String name, Integer par) {
        ContentValues values=new ContentValues(3);
        values.put("name",name);
        db.insert(DATABASE_PLAYERS, null, values);

        values.put("holeScore", par);
        db.insert(DATABASE_SCORES, null, values);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new InputScoresSlideFragment();
        }

        @Override
        public int getCount() {
            int count = SelectCourse.holeCount;
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
