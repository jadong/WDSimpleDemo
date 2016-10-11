package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dong.R;

/**
 * Created by desmond on 31/5/15.
 */
public class SlidingTabStrip extends LinearLayout {
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 2;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private final int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;

    private Rect indicatorRect;

    private SlidingTabLayout.TabColorizer mCustomTabColorizer;
    private final SimpleTabColorizer mDefaultTabColorizer;

    public SlidingTabStrip(Context context) {
        this(context, null);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, outValue, true);
        final int themeForegroundColor = outValue.data;

        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new SimpleTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();
        mSelectedIndicatorPaint.setAntiAlias(true);
        mSelectedIndicatorPaint.setStyle(Paint.Style.FILL);
        indicatorRect = new Rect();
    }

    void setCustomTabColorizer(SlidingTabLayout.TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;

        // Thick colored underline below the current selection
        if (childCount > 0) {

            int color = tabColorizer.getIndicatorColor(mSelectedPosition);

            if (mSelectionOffset > 0f && mSelectedPosition < (childCount - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset);
                }

            }

            mSelectedIndicatorPaint.setColor(color);

            calculateIndicatorRect(indicatorRect);

            //绘制线条两端为两圆角
            canvas.drawRect(indicatorRect.left + mSelectedIndicatorThickness, height - mSelectedIndicatorThickness, indicatorRect.right - mSelectedIndicatorThickness, height, mSelectedIndicatorPaint);
            canvas.drawCircle(indicatorRect.left + mSelectedIndicatorThickness, height, mSelectedIndicatorThickness, mSelectedIndicatorPaint);
            canvas.drawCircle(indicatorRect.right - mSelectedIndicatorThickness, height, mSelectedIndicatorThickness, mSelectedIndicatorPaint);
        }

        // Thin underline along the entire bottom edge
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    /**
     * 计算滑动过程中矩形高亮区域的上下左右位置 默认认为Tab itemlayout中第一个节点是底部line 要对齐的节点
     */
    private void calculateIndicatorRect(Rect rect) {

        int tabCount = getChildCount();

        ViewGroup currentTab = (ViewGroup) getChildAt(mSelectedPosition);
        if (currentTab.getChildCount() == 0) {
            return;
        }
        View currentTabContent = currentTab.getChildAt(0);
        float left = (float) (currentTab.getLeft() + currentTabContent.getLeft());
        float width = ((float) currentTabContent.getWidth()) + left;

        if (mSelectionOffset > 0f && mSelectedPosition < tabCount - 1) {
            ViewGroup nextTab = (ViewGroup) getChildAt(mSelectedPosition + 1);
            if (nextTab.getChildCount() == 0) {
                return;
            }
            View nextTabContent = nextTab.getChildAt(0);
            float nextLeft = (float) (nextTab.getLeft() + nextTabContent.getLeft());
            left = left * (1.0f - mSelectionOffset) + nextLeft * mSelectionOffset;
            width = width * (1.0f - mSelectionOffset) + mSelectionOffset * (((float) nextTabContent.getWidth()) + nextLeft);
        }

        rect.set(((int) left) + getPaddingLeft(), getPaddingTop() + currentTab.getTop() + currentTabContent.getTop(),
                ((int) width) + getPaddingLeft(),
                currentTab.getTop() + getPaddingTop() + currentTabContent.getTop() + currentTabContent.getHeight());

    }

    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer {
        private int[] mIndicatorColors;

        @Override
        public final int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        void setIndicatorColors(int... colors) {
            mIndicatorColors = colors;
        }
    }
}
