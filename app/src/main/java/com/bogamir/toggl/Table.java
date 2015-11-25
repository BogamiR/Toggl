package com.bogamir.toggl;

import android.database.Cursor;

public class Table {
    static Cursor cursor;
    MainActivity mA;

    public void SetCursor (Cursor c){
        cursor = c;
    }

    public Cursor GetCursor (){
        return cursor;
    }

    public Cursor GetCursor (String s){
        cursor = mA.GetCursor(s);
        return cursor;
    }
}
