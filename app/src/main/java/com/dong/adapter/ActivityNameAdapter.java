package com.dong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dong.R;
import com.dong.entity.ActivityName;
import com.dong.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwendong on 16/8/24.
 */
public class ActivityNameAdapter extends BaseAdapter {

    private List<ActivityName> activityNameList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ActivityNameAdapter(Context context, List<ActivityName> activityNameList) {
        this.activityNameList = activityNameList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return activityNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.layout_act_name_item, parent, false);

        final ActivityName act = activityNameList.get(position);
        TextView tv_name = ViewUtils.find(view, R.id.tv_name);
        tv_name.setText(act.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, act.getClazz()));
            }
        });
        return view;
    }
}
