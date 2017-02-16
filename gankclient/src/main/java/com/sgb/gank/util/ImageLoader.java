package com.sgb.gank.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by panda on 16/9/27 下午4:27.
 */
public class ImageLoader {

    public static final int FIT = 1;
    public static final int CENTER_CROP = 2;
    public static final int CENTER_INSIDE = 3;

    @IntDef({FIT, CENTER_CROP, CENTER_INSIDE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScaleType {
    }

    private final Context context;
    private final String url;
    private final int targetWidth;
    private final int targetHeight;
    private final int placeholderResId;
    private final int errorResId;
    private final int scaleType;
    private final float rotate;
    private final Bitmap.Config config;

    public static Builder with(Context context) {
        return new Builder(context);
    }

    private ImageLoader(Context context, String url, int targetWidth, int targetHeight, int scaleType, float rotate, int placeholderResId, int errorResId, Bitmap.Config config) {
        this.context = context;
        this.url = url;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.scaleType = scaleType;
        this.rotate = rotate;
        this.placeholderResId = placeholderResId;
        this.errorResId = errorResId;
        this.config = config;
    }

    public void into(ImageView imageView) {
        RequestCreator requestCreator = Picasso.with(context).load(url);

        if (placeholderResId != 0) {
            requestCreator.placeholder(placeholderResId);
        }

        if (errorResId != 0) {
            requestCreator.error(errorResId);
        }

        if (targetWidth > 0 && targetHeight > 0) {
            requestCreator.resize(targetWidth, targetHeight);
        }

        switch (scaleType) {
            case FIT:
                requestCreator.fit();
                break;
            case CENTER_CROP:
                requestCreator.centerCrop();
                break;
            case CENTER_INSIDE:
                requestCreator.centerInside();
                break;
            default:
                break;
        }

        if (rotate != 0f) {
            requestCreator.rotate(rotate);
        }

        requestCreator.config(config != null ? config : Bitmap.Config.RGB_565);

        requestCreator.into(imageView);
    }

    public static class Builder {

        private final Context context;
        private String url;
        private int placeholderResId;
        private int errorResId;
        private int targetWidth;
        private int targetHeight;
        private int scaleType;
        private float rotate;
        private Bitmap.Config config;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder load(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Builder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder resize(int targetWidth, int targetHeight) {
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder scaleType(@ScaleType int scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder rotate(float rotate) {
            this.rotate = rotate;
            return this;
        }

        public Builder config(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(context, url, targetWidth, targetHeight, scaleType, rotate, placeholderResId, errorResId, config);
        }
    }
}
