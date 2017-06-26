package com.projektarbeit.rss_feeder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projektarbeit.rss_feeder.R;

import java.util.List;

/**
 * Created by HoenigDa on 22.06.2017.
 */

public class SuggestionDomainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> items;

    public SuggestionDomainAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.suggestiondomain_list_item, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.tvSuggestionDomainItemTitle);
        String itemTitle = getItem(position).toString();
        titleTextView.setText(itemTitle);

        return rowView;
    }
}
