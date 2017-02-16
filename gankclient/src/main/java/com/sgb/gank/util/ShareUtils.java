package com.sgb.gank.util;

import android.content.Context;
import android.content.Intent;

import com.sgb.gank.R;

/**
 * Created by panda on 16/9/19 下午5:19.
 */
public class ShareUtils {

    public static void share(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_share)));
    }
}
