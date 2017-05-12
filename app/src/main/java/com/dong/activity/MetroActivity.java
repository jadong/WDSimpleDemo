package com.dong.activity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.util.AppUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zengwendong on 16/9/21.
 */
public class MetroActivity extends BaseActivity {

    private GridView gridView;

    private Context context;

    private TestGridAdapter testGridAdapter;

    private TextView tv_test_text;

    @Override
    protected int getContentView() {
        return R.layout.activity_metro_layout;
    }

    @Override
    protected void onCreate() {
        gridView = (GridView) findViewById(R.id.gridView);
        tv_test_text = (TextView) findViewById(R.id.tv_test_text);
        context = this;

        testGridAdapter = new TestGridAdapter();
        gridView.setAdapter(testGridAdapter);


        //Toast.makeText(this, "width--" + maxWidth + "||height--" + height + "||textSize--" + tv_test_text.getTextSize(), Toast.LENGTH_LONG).show();

        int gridViewHeight = 0;
        int imageHeight = AppUtils.dip2px(70);
        int marginTop = AppUtils.dip2px(5);
        int verticalSpacing = AppUtils.dip2px(4);
        List<TestGridAdapter.ObjectItem> dataList = testGridAdapter.getDataList();

        for (int i = 0; i < dataList.size(); i++) {
            TestGridAdapter.ObjectItem objectItem = dataList.get(i);
            objectItem.textPlaceHeight = getTextViewHeight(objectItem.data);
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (i > 0 && i % 2 == 1) {
                TestGridAdapter.ObjectItem objectItem = dataList.get(i);
                TestGridAdapter.ObjectItem lastObjectItem = dataList.get(i-1);
                if (objectItem.textPlaceHeight > lastObjectItem.textPlaceHeight) {
                    lastObjectItem.textPlaceHeight = objectItem.textPlaceHeight;
                } else if (objectItem.textPlaceHeight < lastObjectItem.textPlaceHeight) {
                    objectItem.textPlaceHeight = lastObjectItem.textPlaceHeight;
                }
            }
        }



        for (int i = 0; i < dataList.size(); i++) {
            TestGridAdapter.ObjectItem objectItem = dataList.get(i);
            if (i % 2 == 0) {
                gridViewHeight += imageHeight + marginTop + objectItem.textPlaceHeight + verticalSpacing;
            }
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) gridView.getLayoutParams();
        layoutParams.height = gridViewHeight+AppUtils.dip2px(12);
        gridView.setLayoutParams(layoutParams);

    }

    private int getTextViewHeight(String text) {
        Paint paint = new Paint();
        paint.setTextSize(AppUtils.dip2px(22));
        int line = 1;
        int maxWidth = 0;
        int screen_width = AppUtils.getScreenWidth() / 2 - AppUtils.dip2px(4);
        int height = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            Rect bounds = new Rect();
            paint.getTextBounds(text, i, i + 1, bounds);
            maxWidth += bounds.width();
            if (maxWidth >= screen_width) {
                maxWidth = 0;
                line++;
            }
            height = bounds.height();
        }

        height = line * height;
        return height;
    }

    private char getRandomChar() {
        String str = "";
        int heightPos;
        int lowPos;

        Random random = new Random();

        heightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(heightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str.charAt(0);
    }

    class TestGridAdapter extends BaseAdapter {

        private List<ObjectItem> dataList = new ArrayList<>();

        public List<ObjectItem> getDataList() {
            return dataList;
        }

        public TestGridAdapter() {

            Random random = new Random();

            for (int i = 0; i < 5; i++) {
                int len = random.nextInt(30) + 1;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    sb.append(getRandomChar());
                }
                this.dataList.add(new ObjectItem(sb.toString()));
            }
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public ObjectItem getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder gridViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_grid_item, null);
                gridViewHolder = new GridViewHolder(convertView);
                convertView.setTag(gridViewHolder);
            } else {
                gridViewHolder = (GridViewHolder) convertView.getTag();
            }
            gridViewHolder.initData(getItem(position));
            return convertView;
        }

        class ObjectItem {
            public String data;
            public int textPlaceHeight;

            public ObjectItem(String data) {
                this.data = data;
            }
        }


        class GridViewHolder {

            ImageView iv_image;
            TextView tv_text;
            View view;

            public GridViewHolder(View view) {
                this.view = view;
                this.iv_image = (ImageView) view.findViewById(R.id.iv_image);
                this.tv_text = (TextView) view.findViewById(R.id.tv_text);
            }

            public void initData(final ObjectItem objectItem) {
                tv_text.setText(objectItem.data);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_text.getLayoutParams();
                layoutParams.height = objectItem.textPlaceHeight;
                tv_text.setLayoutParams(layoutParams);
            }

        }
    }
}
