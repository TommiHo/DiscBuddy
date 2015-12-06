package fi.lato.discbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lasse on 5.12.2015.
 */
public class HighscoreAdapter extends ArrayAdapter<Player> {
    private final Context context;
    private final ArrayList<Player> players;

    public HighscoreAdapter(Context context, ArrayList<Player> players){
        super(context,-1,players);
        this.context = context;
        this.players = players;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.highscore_item, parent, false);
        // name
        TextView nameTextView = (TextView) rowView.findViewById(R.id.name);
        nameTextView.setText(players.get(position).getName());

        // count
        final TextView countTextView = (TextView) rowView.findViewById(R.id.score);
        countTextView.setText(players.get(position).getSum() + "");

        return rowView;
    }
}

