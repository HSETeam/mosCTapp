package com.hackaton.mosctapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hackaton.mosctapp.CommonClasses.GoogleAPIRequest;
import com.hackaton.mosctapp.CommonClasses.Place;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tema on 26.04.15.
 */
public class autoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final int MAX_RESULTS = 10;
    GoogleAPIRequest request = new GoogleAPIRequest();
    private final Context mContext;
    private List<Place> mResults;

    public autoCompleteAdapter(Context context) {
        mContext = context;
        mResults = new ArrayList<Place>();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Place getItem(int index) {
        return mResults.get(index);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.auto_hint, parent, false);
        }
        String text = getItem(position).name;
        ((TextView) convertView.findViewById(R.id.hintAutoText)).setText(text);

        return convertView;
    }

    @Override
    public android.widget.Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Place> books = findLocations(mContext, constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<Place>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<Place> findLocations(Context context, String bookTitle) {
        List<Place> arrayList = new ArrayList<Place>();
        arrayList = request.getAutoComplete(bookTitle);

        return arrayList;
    }
}
