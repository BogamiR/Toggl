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


public class Projects extends AppCompatActivity implements TextWatcher, LoaderCallbacks<Cursor> {

    AutoCompleteTextView autoCTV;
    DBProjects dbProjects;
    ArrayAdapter<String> autoAd;
    DB db;
    TextView time;
    ListView lvProjects;
    SimpleCursorAdapter scAdapter;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DB(this);
        db.open();
        dbProjects = new DBProjects(this);
        dbProjects.open();
        cr = db.getAllData();
        startManagingCursor(cr);

        String[] from = new String[]{DB.COLUMN_TV1, DB.COLUMN_TV2, DB.COLUMN_TV3, DB.COLUMN_TV4};
        int[] to = new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
        scAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, cr, from, to);
        lvProjects = (ListView) findViewById(R.id.lvProjects);
        lvProjects.setAdapter(scAdapter);


        String[] item = new String[cr.getCount()];
        cr.moveToFirst();
        for(int j=0; j<cr.getCount(); j++){
            item[j] = cr.getString(1);
            cr.moveToNext();
        }
        cr.requery();

        cr.moveToFirst();
            for(int j=0; j<cr.getCount(); j++){
                db.addRec(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4));
            }
        cr.requery();

        autoAd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);

        autoCTV = (AutoCompleteTextView) findViewById(R.id.autoCTV);
        autoCTV.addTextChangedListener(this);
        autoCTV.setAdapter(autoAd);

        time = (TextView) findViewById(R.id.tvProjects);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cr.close();
        dbProjects.close();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        cr.moveToFirst();
            dbProjects.delAllRec();
        cr.requery();
        if (autoCTV.getText().toString() == ""){
            cr = db.getAllData();
        }
        else {
            cr = db.getData(autoCTV.getText().toString());
        }
        cr.moveToLast();
        for(int j=0; j<cr.getCount(); j++){
            dbProjects.addRec(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4));
        }
        cr.requery();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, dbProjects);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {
        DBProjects dbProject;

        public MyCursorLoader(Context context, DBProjects dbProject) {
            super(context);
            this.dbProject = dbProject;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = dbProject.getAllData();
            return cursor;
        }
    }
}
