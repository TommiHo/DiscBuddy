package fi.lato.discbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        String[] courses = new String[]{
                "Laajavuori pro", "Tuomiojärvi"
        };

        // add data to ArrayList
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < courses.length; ++i) {
            list.add(courses[i]);
        }

        // add data to ArrayAdapter (default Android ListView style/layout)
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        // set data to listView with adapter
        listview.setAdapter(adapter);

        // item listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get list row data (now String as a phone name)
                String course = list.get(position);
                // create an explicit intent
                Intent intent = new Intent(SelectCourse.this,SelectPlayers.class);
                // add data to intent
                intent.putExtra("course",course);
                // start a new activity
                startActivity(intent);
            }
        });

    }


}
