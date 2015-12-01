package fi.lato.discbuddy;

import android.app.Fragment;
import android.os.Bundle;
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
    private int page;

    public static ScoresFragment newInstance(int page){
        ScoresFragment fragment = new ScoresFragment();
        Bundle args = new Bundle();
        args.putInt("somePage", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("somePage",0);
        View rootView = inflater.inflate(R.layout.input_scores, container, false);
        listView = (ListView) rootView.findViewById(R.id.scores_listView);
        TextView courseName = (TextView) rootView.findViewById(R.id.courseTextView);
        TextView holeNumber = (TextView) rootView.findViewById(R.id.holenumber);
        courseName.setText(SelectCourse.course);
        holeNumber.setText(page+"");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlayerAdapter adapter = new PlayerAdapter(getActivity(),SelectPlayers.players);

        listView.setAdapter(adapter);
    }

}