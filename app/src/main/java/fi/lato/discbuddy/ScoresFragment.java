package fi.lato.discbuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Lasse on 22.11.2015.
 */
public class ScoresFragment extends Fragment {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.input_scores, container, false);
        listView = (ListView) rootView.findViewById(R.id.scores_listView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlayerAdapter adapter = new PlayerAdapter(getActivity(),SelectPlayers.players);

        listView.setAdapter(adapter);
    }

}