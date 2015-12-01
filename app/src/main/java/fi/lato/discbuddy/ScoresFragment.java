package fi.lato.discbuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Lasse on 22.11.2015.
 */
public class ScoresFragment extends Fragment {
    private ListView listView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.input_scores, container, false);
        listView = (ListView) rootView.findViewById(R.id.scores_listView);


        TextView txtValue = (TextView) rootView.findViewById(R.id.holenumber);
        txtValue.setText(Integer.toString(currentHole));
        Log.e("moi", txtValue.getText().toString());

        TextView courseName = (TextView) rootView.findViewById(R.id.courseTextView);
        courseName.setText(SelectCourse.course);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlayerAdapter adapter = new PlayerAdapter(getActivity(),SelectPlayers.players);

        listView.setAdapter(adapter);
    }

}