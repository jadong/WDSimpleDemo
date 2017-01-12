package com.dong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwendong on 16/12/15.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList;
    private LayoutInflater layoutInflater;

    public TestAdapter(Context context) {
        this.dataList = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<String> list) {
        if (list != null) {
            this.dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_test_item, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TestViewHolder) {
            TestViewHolder testViewHolder = (TestViewHolder) holder;
            testViewHolder.initData(position + "---" + dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_test_text;

        public TestViewHolder(View itemView) {
            super(itemView);
            tv_test_text = (TextView) itemView.findViewById(R.id.tv_test_text);
        }

        public void initData(String text) {
            tv_test_text.setText(text);
        }
    }
}
