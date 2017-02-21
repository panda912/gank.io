package com.sgb.gank.data.search.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sgb.gank.data.search.module.SearchListObj;
import com.sgb.gank.data.search.source.SearchDataSource;
import com.sgb.gank.util.DateUtils;

import org.reactivestreams.Publisher;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by panda on 2017/2/17 上午10:58.
 */
public class SearchLocalDataSource implements SearchDataSource {

    private static SearchLocalDataSource sInstance;

    private SearchDBHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    private SearchLocalDataSource(Context context) {
        mDbHelper = new SearchDBHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public static SearchLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SearchLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<SearchListObj>> getSearchList(String keyword) {
        return Flowable.just(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<String, Publisher<List<SearchListObj>>>() {
                    @Override
                    public Publisher<List<SearchListObj>> apply(String s) throws Exception {
                        String sql = "SELECT * FROM " + SearchDBHelper.TABLE_NAME +
                                " WHERE " + SearchDBHelper.COLUMN_KEYWORD + "='" + keyword + "'";
                        Cursor cursor = mDatabase.rawQuery(sql, null);
                        if (cursor.moveToFirst()) {
                            do {
                                String str = cursor.getString(cursor.getColumnIndex(SearchDBHelper.COLUMN_KEYWORD));
                                if (TextUtils.equals(keyword, str)) {
                                    String updateTime = cursor.getString(cursor.getColumnIndex(SearchDBHelper.COLUMN_UPDATE_DATE));
                                    String insertTime = cursor.getString(cursor.getColumnIndex(SearchDBHelper.COLUMN_INSERT_DATE));
                                    String lastUpdateTime = !TextUtils.isEmpty(updateTime) ? updateTime : insertTime;
                                    Date lastUpdateDate = DateUtils.format(lastUpdateTime);
                                    long duration = System.currentTimeMillis() - lastUpdateDate.getTime();
                                    Log.e("duration", duration + "");
                                    if (duration < SearchDBHelper.CACHE_DURATION) {
                                        String json = cursor.getString(cursor.getColumnIndex(SearchDBHelper.COLUMN_DATA));
                                        List<SearchListObj> searchList = new GsonBuilder().create().fromJson(json, new TypeToken<List<SearchListObj>>() {
                                        }.getType());
                                        cursor.close();
                                        return Flowable.just(searchList);
                                    }
                                    cursor.close();
                                    return Flowable.empty();
                                }
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        return Flowable.empty();
                    }
                });
    }

    @Override
    public void saveSearchList(String keyword, List<SearchListObj> list) {
        Flowable.just(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchListObj>>() {
                    @Override
                    public void accept(List<SearchListObj> searchList) throws Exception {
                        Gson gson = new GsonBuilder().create();
                        String json = gson.toJson(searchList);
                        boolean isExist = false;

                        String sql = "SELECT * FROM " + SearchDBHelper.TABLE_NAME;
                        Cursor cursor = mDatabase.rawQuery(sql, null);
                        if (cursor.moveToFirst()) {
                            do {
                                String str = cursor.getString(cursor.getColumnIndex(SearchDBHelper.COLUMN_KEYWORD));
                                if (TextUtils.equals(keyword, str)) {
                                    isExist = true;
                                    cursor.close();
                                    break;
                                }
                            } while (cursor.moveToNext());
                        }

                        if (isExist) {
//                            ContentValues updateValues = new ContentValues();
//                            updateValues.put(SearchDBHelper.COLUMN_DATA, json);
//                            updateValues.put(SearchDBHelper.COLUMN_UPDATE_DATE, "datetime('now','localtime')");
//                            String where = SearchDBHelper.COLUMN_KEYWORD + "='" + keyword + "'";
//                            mDatabase.update(SearchDBHelper.TABLE_NAME, updateValues, where, null);
                            String updateSql = "UPDATE " + SearchDBHelper.TABLE_NAME +
                                    " SET " + SearchDBHelper.COLUMN_UPDATE_DATE + "=datetime('now','localtime')" +
                                    " WHERE " + SearchDBHelper.COLUMN_KEYWORD + "='" + keyword + "'";
                            mDatabase.execSQL(updateSql);
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(SearchDBHelper.COLUMN_KEYWORD, keyword);
                            values.put(SearchDBHelper.COLUMN_DATA, json);
                            mDatabase.insert(SearchDBHelper.TABLE_NAME, null, values);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("SearchLocalDataSource", throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("SearchLocalDataSource", "onComplete");
                    }
                });
    }
}
