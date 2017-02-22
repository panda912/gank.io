package com.sgb.gank.data.main.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sgb.gank.data.DBConfig;


/**
 * Created by panda on 2017/2/21 下午5:09.
 */
public class GankDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "gank_list";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CREATE_TIME = "create_time";
    public static final String COLUMN_PUBLISHED_TIME = "published_time";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_SOURCE + " TEXT," +
                    COLUMN_TYPE + " TEXT," +
                    COLUMN_URL + " TEXT," +
                    COLUMN_DESC + " TEXT," +
                    COLUMN_AUTHOR + " TEXT," +
                    COLUMN_CREATE_TIME + " TEXT," +
                    COLUMN_PUBLISHED_TIME + " TEXT )";

    public GankDBHelper(Context context) {
        super(context, DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
        getWritableDatabase().execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
