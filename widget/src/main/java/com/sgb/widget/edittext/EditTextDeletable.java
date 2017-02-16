package com.sgb.widget.edittext;

/**
 * Created by panda on 16/9/19 下午5:47.
 */

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.sgb.widget.R;

/**
 * 带删除功能的EditText
 * <p>
 * Created by panda on 16/2/1 上午10:57.
 */
public class EditTextDeletable extends EditText {

    private Drawable deleteImg;
    private Context mContext;

    public EditTextDeletable(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public EditTextDeletable(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public EditTextDeletable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        deleteImg = getCompoundDrawables()[2];
        if (deleteImg == null) {
            deleteImg = mContext.getResources().getDrawable(R.drawable.ic_delete);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setIconVisible(length() > 0);
                } else {
                    setIconVisible(false);
                }
            }
        });

        setDrawable();

        setFocusable(true);
    }

    private void setDrawable() {
        if (length() != 0) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], deleteImg, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }
    }

    private void setIconVisible(boolean isVisible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], isVisible ? deleteImg : null, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                //点击坐标是否在横向区域内
                boolean isInnerWidth = x > getWidth() - getTotalPaddingRight();
                Rect rect = deleteImg.getBounds();
                //删除图标的高度
                int deleteImgHeight = rect.height();
                //获取删除图标距edittext底部的距离
                int bottomDistance = (getHeight() - deleteImgHeight) / 2;
                boolean isInnerHeight = y > bottomDistance && y < getHeight() - bottomDistance;
                if (isInnerWidth && isInnerHeight) {
                    setText(null);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}

