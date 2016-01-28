package com.kitkatdev.m2dl.chanllengeandroidclm.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.kitkatdev.m2dl.chanllengeandroidclm.R;
import com.kitkatdev.m2dl.chanllengeandroidclm.model.Score;

import java.util.List;

/**
 * Permet d'afficher la liste de tag sur l'activité ChooseTagActivity
 * Created by Julien on 24/01/2016.
 */
public class CustomAdapter extends ArrayAdapter<Score> {


    List<Score> tags = null;
    Context context;

    public CustomAdapter(Context context, List<Score> tags) {
        super(context, R.layout.listview_chooseactivity_row, tags);
        this.context = context;
        this.tags = tags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.listview_chooseactivity_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textViewRow);
        name.setTypeface(null, Typeface.BOLD);

        name.setText(tags.get(position).getName());
        TextView scoreViewRow = (TextView) convertView.findViewById(R.id.scoreViewRow);
        scoreViewRow.setText(tags.get(position).getPoint().toString() + " points");

        return convertView;
    }
}

