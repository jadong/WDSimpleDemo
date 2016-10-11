package com.dong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwendong on 16/7/19.
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_PLACEHOLDER = 0;
    public static final int TYPE_SINGLE_ROWS = 1;
    public static final int TYPE_FOOTER = 2;
    private static final int TYPE_DOUBLE_ROWS = 3;

    private Context context;
    private LayoutInflater layoutInflater;

    private List<ItemBean> dataList;

    public ItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void setData(List<ItemBean> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<ItemBean> list) {
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_PLACEHOLDER) {
            view = layoutInflater.inflate(R.layout.layout_place, parent, false);
            return new PlaceViewHolder(view);
        } else if (viewType == TYPE_SINGLE_ROWS) {
            view = layoutInflater.inflate(R.layout.layout_item, parent, false);
            return new SingleRowHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = layoutInflater.inflate(R.layout.layout_load_more, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_DOUBLE_ROWS){

        }

        throw new RuntimeException("Invalid view type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SingleRowHolder) {
            SingleRowHolder singleRowHolder = (SingleRowHolder) holder;
            ItemBean itemBean = getItem(position);
            singleRowHolder.tv_text.setText(itemBean.title);
            if (itemBean.imgId!=0){
                singleRowHolder.iv_icon.setImageResource(itemBean.imgId);
            }

        }
    }

    private ItemBean getItem(int position) {
        try {
            return dataList.get(position - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PLACEHOLDER;
        }

        int footerPosition = getItemCount() - 1;
        if (footerPosition == position) {
            return TYPE_FOOTER;
        }

        return TYPE_SINGLE_ROWS;
    }

    @Override
    public int getItemCount() {
        int count = dataList == null ? 0 : dataList.size();
        count++;//占位
        count++;//底部

        return count;
    }

    //单列holder
    class SingleRowHolder extends RecyclerView.ViewHolder {

        public TextView tv_text;
        public ImageView iv_icon;

        public SingleRowHolder(View itemView) {
            super(itemView);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
    //双列holder
    class DoubleRowHolder extends RecyclerView.ViewHolder {

        public TextView tv_text;
        public ImageView iv_icon;

        public DoubleRowHolder(View itemView) {
            super(itemView);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder {

        public PlaceViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;
        public TextView tv_loading_text;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tv_loading_text = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }

    }
}
