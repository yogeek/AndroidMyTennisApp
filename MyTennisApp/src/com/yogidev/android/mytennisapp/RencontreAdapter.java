package com.yogidev.android.mytennisapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RencontreAdapter extends ArrayAdapter<Rencontre> {
	
	private ArrayList<Rencontre> items;

    public RencontreAdapter(Context context, int textViewResourceId, ArrayList<Rencontre> items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.affichageitempalmares, null);
            }
            Rencontre r = items.get(position);
            if (r != null) {
                    TextView tt = (TextView) v.findViewById(R.id.nomAdversaire);
                    TextView bt = (TextView) v.findViewById(R.id.classementAdversaire);
                    if (tt != null) {
                          tt.setText(r.getAdversaire().getNomComplet());                            }
                    if(bt != null){
                          bt.setText(TennisCalculRules.getClassementAsStringAffichable(r.getAdversaire().getClassement()));
                    }
                    ImageView img = (ImageView) v.findViewById(R.id.imgMatch);
                    if (r.isVictoire())
    					img.setImageResource(R.drawable.victorytiny);
    				else 
    					img.setImageResource(R.drawable.defeattiny);
            }
            return v;
    }

}
