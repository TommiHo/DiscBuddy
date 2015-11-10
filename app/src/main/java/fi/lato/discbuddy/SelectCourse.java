package fi.lato.discbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Lasse on 10.11.2015.
 */
public class SelectCourse extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_course);

        // find list view
        ListView listview = (ListView) findViewById(R.id.courseListView);

        // generate some dummy data
        String[] phones = new String[]{
                "Laajavuori pro", "Tuomiojärvi"
        };

        // add data to ArrayList
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < phones.length; ++i) {
            list.add(phones[i]);
        }

        // add data to ArrayAdapter (default Android ListView style/layout)
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        // set data to listView with adapter
        listview.setAdapter(adapter);

    }


}
