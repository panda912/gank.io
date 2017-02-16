package com.sgb.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sgb.widget.R;

/**
 * Created by panda on 16/9/18 上午10:52.
 */
public class RatioImageView extends ImageView {

    /**
     * 宽高比
     */
    private float mRatio;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView, 0, 0);
        mRatio = ta.getFloat(R.styleable.RatioImageView_ratio, 0);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int width = getMeasuredWidth();
            int height = (int) (width / mRatio + 0.5);
            if (width > 0 && height > 0) {
                setMeasuredDimension(width, height);
            }
        }
    }

    public void setRatio(@FloatRange(from = 0.0) float ratio) {
        this.mRatio = ratio;
    }

}
