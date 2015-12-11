package com.bogamir.toggl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    protected static String DB_NAME = "myDBase";
    private static final int DB_VERSION = 1;
    protected static String DB_TABLE = "mytab";
    protected static String DB_TABLE_P = "sort";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TV1= "tv1";
    public static final String COLUMN_TV2= "tv2";
    public static final String COLUMN_TV3= "tv3";
    public static final String COLUMN_TV4= "tv4";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TV1 + " text, " +
                    COLUMN_TV2 + " text, " +
                    COLUMN_TV3 + " text, " +
                    COLUMN_TV4 + " text" +
                    ");";

    private static final String DB_CREATE_P =
            "create table " + DB_TABLE_P + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TV1 + " text, " +
                    COLUMN_TV2 + " text, " +
                    COLUMN_TV3 + " text, " +
                    COLUMN_TV4 + " text" +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
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
    public Cursor getAllData() { return mDB.query(DB_TABLE, null, null, null, null, null, null); }
    public Cursor getAllData_P() { return mDB.query(DB_TABLE_P, null, null, null, null, null, null); }

    // добавить запись в DB_TABLE
    public void addRec(String tv1, String tv2, String tv3, String tv4) {
        ContentValues cv = new ContentValues();
        add(cv, tv1, tv2, tv3, tv4);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void addRec_P(String tv1, String tv2, String tv3, String tv4) {
        ContentValues cv = new ContentValues();
        add(cv, tv1, tv2, tv3, tv4);
        mDB.insert(DB_TABLE_P, null, cv);
    }

    public ContentValues add(ContentValues cv, String tv1, String tv2, String tv3, String tv4){
        cv.put(COLUMN_TV1, tv1);
        cv.put(COLUMN_TV2, tv2);
        cv.put(COLUMN_TV3, tv3);
        cv.put(COLUMN_TV4, tv4);
        return cv;
    }

    public Cursor getData(String s) {
        String selection = "tv1 LIKE \"" + s + "%\"";
        return mDB.query(DB_TABLE, null, selection, null, null, null, COLUMN_TV1); }

    // удалить запись из DB_TABLE
    public void delRec(long id) { mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null); }

    public void delAllRec() { mDB.delete(DB_TABLE, null, null); }
    public void delAllRec_P() { mDB.delete(DB_TABLE_P, null, null); }

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
            db.execSQL(DB_CREATE_P);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}