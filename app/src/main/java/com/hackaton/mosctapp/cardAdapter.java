package com.hackaton.mosctapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackaton.mosctapp.CommonClasses.Route;
import com.hackaton.mosctapp.CommonClasses.Step;

import java.util.List;

/**
 * Created by tema on 26.04.15.
 *
 * Adapter Для карточек (Не трогаем)
 *
 */
public class cardAdapter extends ArrayAdapter<Step> {
    public cardAdapter (Context context, List<Step> routes) {
        super(context, R.layout.card_item, routes);
    }

    @Override
    public Step getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Step step = (Step)getItem(position);

        convertView = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.card_item, null);
        }

        TextView instruction = ((TextView) convertView.findViewById(R.id.instruction));
        instruction.setText(step.description);

        return convertView;
    }
}
