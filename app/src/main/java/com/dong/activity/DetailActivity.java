package com.dong.activity;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.style.TagImageSpan;
import com.dong.util.AppUtils;

import java.util.Random;

/**
 * Created by zengwendong on 16/5/5.
 */
public class DetailActivity extends BaseActivity {

    private TextView tv_cursor;
    private int currIndex = 0;
    private int position = 0;
    private int width = 100;
    private Random random = new Random();
    private ImageView iv_image;
    private TextView tv_text;

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate() {
        tv_cursor = (TextView) findViewById(R.id.tv_cursor);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_text = (TextView) findViewById(R.id.tv_text);
       // String url = "https://mp1.jmstatic.com/c_zoom,w_1080,q_70/dev_test/pop_store/000/123/123564_std/574e88d3d60a2_1024_304.png?1464764630593";
        /*Glide.with(this).load(url).into(iv_image);
        tv_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "AAAAAAA", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                position = random.nextInt(4);
                float fromX = currIndex * width;
                float toX = position * width;
                float value = toX - fromX;
                ObjectAnimator.ofFloat(tv_cursor, "translationX", value).setDuration(500).start();
                currIndex = position;
            }
        });*/

        String text = "运伟大之思者,必行伟大之迷途,背起行囊,独自旅行,做一个孤独的散步者。目标有价值,生活才有价值";
        String pre = " 星辰大海 ";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        SpannableString spannableString = new SpannableString(pre);

        TagImageSpan tagImageSpan = new TagImageSpan(0, 0);
        spannableString.setSpan(tagImageSpan, 0, pre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new AbsoluteSizeSpan(AppUtils.sp2px(12f)), 0, pre.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.white));
        spannableString.setSpan(foregroundColorSpan, 0, pre.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        stringBuilder.append(spannableString);
        stringBuilder.append(" ");
        stringBuilder.append(text);
        tv_text.setText(stringBuilder);


    }
}