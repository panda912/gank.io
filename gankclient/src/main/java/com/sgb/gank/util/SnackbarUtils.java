package com.sgb.gank.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by panda on 16/9/7 上午11:09.
 */
public class SnackbarUtils {

    public static void show(String text, View view) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
