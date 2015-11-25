package fi.lato.discbuddy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Iterator;

/**
 * Created by tommi on 10.11.2015.
 */
public class InputScores extends FragmentActivity {
    private SQLiteDatabase db;
    private ListView scores_listView;
    private final String DATABASE_PLAYERS = "players";
    private final String DATABASE_SCORES = "scores";
    //public static ArrayList<Player> playerNames;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_scores_pager);

        mAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(myOnPageChangeListener);

        // find list view
        scores_listView = (ListView)  findViewById(R.id.scores_listView);
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener =
            new ViewPager.OnPageChangeListener(){


                @Override
                public void onPageScrollStateChanged(int state) {
                    //Called when the scroll state changes.
                    Log.d("Scrolli testi", "Toimiii");
                }

                @Override
                public void onPageScrolled(int position,
                                           float positionOffset, int positionOffsetPixels) {
                    //This method will be invoked when the current page is scrolled,
                    //either as part of a programmatically initiated smooth scroll
                    //or a user initiated touch scroll.
                }

                @Override
                public void onPageSelected(int position) {
                    //This method will be invoked when a new page becomes selected.
                    Log.d("Scrolli testi", "UUUUSI SIVU");
                    Iterator<Player> it = SelectPlayers.players.iterator();
                    while(it.hasNext()){
                        it.next().resetCount();
                    }
                }
            };


        // tuloksien syöttäminen

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
            return new ScoresFragment();
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
