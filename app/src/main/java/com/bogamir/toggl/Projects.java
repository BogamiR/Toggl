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
    TextView time;
    ListView lvProjects;
    SimpleCursorAdapter scAdapter;
    Table table = new Table();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbProjects = new DBProjects(this);
        dbProjects.open();

        Cursor cr = table.GetCursor();
        cr.moveToFirst();
        String[] item = new String[cr.getCount()];
        int i = 0;
        while (cr.moveToNext()) {
            item[i++] = cr.getString(1);
            dbProjects.addRec(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4));
        }
        cr.close();

        autoAd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);

        autoCTV = (AutoCompleteTextView) findViewById(R.id.autoCTV);
        autoCTV.addTextChangedListener(this);
        autoCTV.setAdapter(autoAd);

        time = (TextView) findViewById(R.id.tvProjects);
        lvProjects = (ListView) findViewById(R.id.lvProjects);

        String[] from = new String[]{DB.COLUMN_TV1, DB.COLUMN_TV2, DB.COLUMN_TV3, DB.COLUMN_TV4};
        int[] to = new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
        scAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, null, from, to, 0);
        lvProjects.setAdapter(scAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbProjects.delAllRec();
        getSupportLoaderManager().getLoader(0).forceLoad();
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
        dbProjects.delAllRec();
        getSupportLoaderManager().getLoader(0).forceLoad();
        Cursor text;
        if (autoCTV.getText().toString() == ""){
            text = table.GetCursor();
        }
        else {
            text = table.GetCursor(autoCTV.getText().toString());
        }
        text.moveToFirst();
        do {
            dbProjects.addRec(text.getString(1), text.getString(2), text.getString(3), text.getString(4));
        }
        while (text.moveToNext());
        text.close();
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
        Table table;

        public MyCursorLoader(Context context, DBProjects dbProject) {
            super(context);
            this.dbProject = dbProject;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = this.dbProject.getAllData();
            return cursor;
        }
    }
}
