package com.sgb.gank.data.search.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sgb.gank.data.DBConfig;

/**
 * Created by panda on 2017/2/17 上午11:50.
 */
public class SearchDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "search_history";

    public static final long CACHE_DURATION = 60 * 60 * 1000; //ms  1小时

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KEYWORD = "keyword";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_INSERT_DATE = "insert_date";
    public static final String COLUMN_UPDATE_DATE = "update_date";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_KEYWORD + " TEXT, " +
                    COLUMN_DATA + " TEXT," +
                    COLUMN_INSERT_DATE + " TIMESTAMP NOT NULL DEFAULT (datetime('now','localtime'))," +
                    COLUMN_UPDATE_DATE + " TIMESTAMP " +
                    ")";

    public SearchDBHelper(Context context) {
        super(context, DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
