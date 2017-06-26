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
 * Created by HoenigDa on 14.06.2017.
 */

public class FolderAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Folder> items;

    public FolderAdapter(Context context, List<Folder> items) {
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
        View rowView = inflater.inflate(R.layout.folder_list_item, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.tvFolderItemTitle);
        Folder folder = (Folder) getItem(position);
        titleTextView.setText(folder.getFolderName());

        return rowView;
    }
}
