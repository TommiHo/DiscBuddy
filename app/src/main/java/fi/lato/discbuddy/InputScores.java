package fi.lato.discbuddy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by tommi on 10.11.2015.
 */
public class InputScores extends FragmentActivity {
    private ListView scores_listView;
    public static ArrayList<int[]> scores = new ArrayList<int[]>();
    private int[] pages = {0,0};


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.input_scores_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled((mPager.getCurrentItem()>=1));

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = (mPager.getCurrentItem() == mAdapter.getCount() - 1)
                ? menu.add(Menu.NONE, R.id.action_finish, Menu.NONE,R.string.action_finish)
                : menu.add(Menu.NONE, R.id.action_next, Menu.NONE,R.string.action_next);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_finish:
                //save last page scores
                updatePages();
                saveScores();
                // Navigate scorecard-activity.
                Intent intent = new Intent(this, ScoreCard.class);
                intent.putExtra("scores", scores);
                startActivity(intent);
                Log.d("scores",scores.size()+"");
                return true;

            case R.id.action_previous:
                // Go to the previous course. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next course. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener =
            new ViewPager.OnPageChangeListener(){


                @Override
                public void onPageScrollStateChanged(int state) {
                    //Called when the scroll state changes.
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
                    invalidateOptionsMenu();
                    updatePages();
                    if (pages[0] > pages[1] )saveScores();

                }
            };
    public void updatePages(){
        int currentPage = mPager.getCurrentItem();
        pages[1] = pages[0];
        pages[0] = currentPage;
        Log.e("PAGES", Arrays.toString(pages));
    }

    public void saveScores(){
        Log.d("size",mAdapter.getCount()+"");
        Log.d("page",mPager.getCurrentItem()+"");

        Iterator<Player> it = SelectPlayers.players.iterator();
        int i = 0;
        int[] points = new int[SelectPlayers.players.size()];
        while (it.hasNext()) {
            Player pelaaja = it.next();
            points[i] = pelaaja.getCount();
            pelaaja.resetCount();
            i++;
        }

        try{
            scores.set(pages[1],points);
            Toast.makeText(getApplicationContext(), "Sivun"+(pages[1]+1)+"pisteet päivitetty", Toast.LENGTH_SHORT).show();
        }catch ( IndexOutOfBoundsException e ) {
            Log.e("Add Scores", e.toString());
            scores.add(pages[1], points);
            Toast.makeText(getApplicationContext(), "Sivun"+(pages[1]+1)+"pisteet lisätty", Toast.LENGTH_SHORT).show();
        }
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
