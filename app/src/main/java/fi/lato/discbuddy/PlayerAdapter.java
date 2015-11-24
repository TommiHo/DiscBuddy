package fi.lato.discbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by H4273 on 24.11.2015.
 */
public class PlayerAdapter extends ArrayAdapter<Player>{
    private final Context context;
    private final ArrayList<Player> players;

    public PlayerAdapter(Context context, ArrayList<Player> players){
        super(context,-1,players);
        this.context = context;
        this.players = players;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.score_list_item, parent, false);
        // name
        TextView nameTextView = (TextView) rowView.findViewById(R.id.playerName);
        nameTextView.setText(players.get(position).getName());
        // count
        final TextView countTextView = (TextView) rowView.findViewById(R.id.par);
        countTextView.setText(players.get(position).getCount()+"");
        // + button
        Button addButton = (Button) rowView.findViewById(R.id.plusbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPlayers.players.get(position).addCount();
                countTextView.setText(players.get(position).getCount() + "");
            }
        });
        // - button
        Button decreaseButton = (Button) rowView.findViewById(R.id.minusbutton);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPlayers.players.get(position).decreaseCount();
                countTextView.setText(players.get(position).getCount() + "");
            }
        });
        return rowView;
    }
}
