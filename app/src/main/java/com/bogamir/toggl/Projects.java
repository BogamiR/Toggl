package com.bogamir.toggl;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Projects extends AppCompatActivity implements TextWatcher, LoaderCallbacks<Cursor> {

    AutoCompleteTextView autoCTV;
    ArrayAdapter<String> autoAd;
    DB db;
    TextView time;
    ListView lvProjects;
    SimpleCursorAdapter scAdapter;
    Cursor cr;
    List<String> item = new ArrayList<String>();
    HashSet<String> mass = new HashSet<String>();
    int timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DB(this);
        db.open();

        String[] from = new String[]{DB.COLUMN_TV1, DB.COLUMN_TV2, DB.COLUMN_TV3, DB.COLUMN_TV4};
        int[] to = new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
        scAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, cr, from, to, 0);
        lvProjects = (ListView) findViewById(R.id.lvProjects);
        lvProjects.setAdapter(scAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        Cursor cursor = db.getAllData();
        db.delAllRec_P();
        getSupportLoaderManager().getLoader(0).forceLoad();


        cursor.moveToFirst();
        for (int j = 0; j < cursor.getCount(); j++) {
            mass.add(cursor.getString(1));
            db.addRec_P(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            getSupportLoaderManager().getLoader(0).forceLoad();
            timer += calcTime(cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.requery();
        cursor.close();

        for (String str : mass)
            item.add(str);

        cr = db.getAllData_P();
        getSupportLoaderManager().getLoader(0).forceLoad();

        autoAd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);

        autoCTV = (AutoCompleteTextView) findViewById(R.id.autoCTV);
        autoCTV.addTextChangedListener(this);
        autoCTV.setAdapter(autoAd);

        time = (TextView) findViewById(R.id.tvProjects);
        time.setText(timer + " sec");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        db.delAllRec_P();
        getSupportLoaderManager().getLoader(0).forceLoad();
        timer = 0;

        Cursor cursor;
        if (autoCTV.getText().toString() == "") {
            cursor = db.getAllData();
        } else {
            cursor = db.getData(autoCTV.getText().toString());
        }
        cursor.moveToFirst();
        for (int j = 0; j < cursor.getCount(); j++) {
            db.addRec_P(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            getSupportLoaderManager().getLoader(0).forceLoad();
            timer += calcTime(cursor.getString(3));
            cursor.moveToNext();
        }
        //db.addRec_P("Vasia","","","");
        cursor.requery();
        cursor.close();
        time.setText(timer + " sec");

        cr = db.getAllData_P();
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bnd1) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {
        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData_P();
            return cursor;
        }
    }

    public int calcTime(String s) {
        String[] words = s.split(" ");
        return Integer.parseInt(words[0]);
    }
}
