package com.sgb.gank.data.main.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainDataSource;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panda on 2017/2/21 下午2:58.
 */
public class MainLocalDataSource implements MainDataSource {
    private static MainLocalDataSource sInstance;

    private AndroidDBHelper mDbHelper;
    private SQLiteDatabase mDB;

    private MainLocalDataSource(Context context) {
        mDbHelper = new AndroidDBHelper(context);
        mDB = mDbHelper.getWritableDatabase();
    }

    public static MainLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MainLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<MainListResBody.ResultsObj>> getDatas(@MainConstant.Category String category, int count, int reqPage) {
        return Flowable.empty()
                .flatMap(new Function<Object, Publisher<List<MainListResBody.ResultsObj>>>() {
                    @Override
                    public Publisher<List<MainListResBody.ResultsObj>> apply(Object o) throws Exception {

                        List<MainListResBody.ResultsObj> list = new ArrayList<>();

                        String sql = "SELECT * FROM " + AndroidDBHelper.TABLE_NAME;
                        Cursor cursor = mDB.rawQuery(sql, null);
                        if (cursor.moveToFirst()) {
                            do {
                                String id = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_ID));
                                String url = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_URL));
                                String desc = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_DESC));
                                String type = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_TYPE));
                                String author = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_AUTHOR));
                                String time = cursor.getColumnName(cursor.getColumnIndex(AndroidDBHelper.COLUMN_PUBLISHED_TIME));

                                MainListResBody.ResultsObj obj = new MainListResBody.ResultsObj();
                                obj.id = id;
                                obj.url = url;
                                obj.desc = desc;
                                obj.type = type;
                                obj.who = author;
                                obj.publishedAt = time;
                                list.add(obj);
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        return Flowable.just(list);
                    }
                });
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {
        Flowable.just(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MainListResBody.ResultsObj>>() {
                    @Override
                    public void accept(List<MainListResBody.ResultsObj> list) throws Exception {
                        mDB.beginTransaction();
                        for (MainListResBody.ResultsObj obj : list) {
                            ContentValues value = new ContentValues();
                            value.put(AndroidDBHelper.COLUMN_ID, obj.id);
                            value.put(AndroidDBHelper.COLUMN_TYPE, obj.type);
                            value.put(AndroidDBHelper.COLUMN_SOURCE, obj.source);
                            value.put(AndroidDBHelper.COLUMN_URL, obj.url);
                            value.put(AndroidDBHelper.COLUMN_DESC, obj.desc);
                            value.put(AndroidDBHelper.COLUMN_AUTHOR, obj.who);
                            value.put(AndroidDBHelper.COLUMN_CREATE_TIME, obj.createdAt);
                            value.put(AndroidDBHelper.COLUMN_PUBLISHED_TIME, obj.publishedAt);
                            mDB.insert(AndroidDBHelper.TABLE_NAME, null, value);
                        }
                        mDB.setTransactionSuccessful();
                        mDB.endTransaction();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainLocalDataSource", throwable.getMessage());
                    }
                });
    }
}
