package com.bogamir.toggl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBProjects {

    private static final String DB_NAME = "projectDBase";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "myBase";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TV1= "tv1";
    public static final String COLUMN_TV2= "tv2";
    public static final String COLUMN_TV3= "tv3";
    public static final String COLUMN_TV4= "tv4";

    private static final String DB_CREATE =
            "create table " + "myBase" + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TV1 + " text, " +
                    COLUMN_TV2 + " text, " +
                    COLUMN_TV3 + " text, " +
                    COLUMN_TV4 + " text" +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DBProjects(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() { return mDB.query("myBase", null, null, null, null, null, null); }

    // добавить запись в DB_TABLE
    public void addRec(String tv1, String tv2, String tv3, String tv4) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TV1, tv1);
        cv.put(COLUMN_TV2, tv2);
        cv.put(COLUMN_TV3, tv3);
        cv.put(COLUMN_TV4, tv4);
        mDB.insert("myBase", null, cv);
    }

    // удалить все записи
    public void delAllRec() { mDB.delete("myBase", null,null); }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}