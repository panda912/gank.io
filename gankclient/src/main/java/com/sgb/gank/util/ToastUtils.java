package com.sgb.gank.util;

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by panda on 16/9/7 上午11:04.
 */
public class ToastUtils {

    public static void showShort(String text, Context context) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String text, Context context) {
        makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
