package com.dong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dong.R;
import com.dong.util.ImageLoadUtil;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> paths = new ArrayList<String>();

    private Context context;
    public ImageAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(ArrayList<String> paths) {
        this.paths.clear();
        this.paths.addAll(paths);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_image, null);
            holder = new ViewHolder();
            holder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoadUtil.display(context,"file://" + paths.get(position),holder.iv_item);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_item;
    }

}
