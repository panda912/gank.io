package com.sgb.gank;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by panda on 2017/2/11 下午10:27.
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest1 {
    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
//        assertEquals();
    }
}