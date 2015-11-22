package com.bogamir.toggl;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    LinearLayout linear;
    EditText editText, selectProject;
    TextView timer;
    Button go;
    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    Handler handler = new Handler();
    String timeStart;
    boolean flag = true;
    int time_calc = 0, j = 0;
    //List<View> allEds;
    //View view;
    DB db;
    SimpleCursorAdapter scAdapter;
    ListView lvData;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DB (this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        String[] from = new String[] { DB.COLUMN_TV1, DB.COLUMN_TV2, DB.COLUMN_BTN1, DB.COLUMN_TV3, DB.COLUMN_TV4 };
        int[] to = new int[] { R.id.tv1, R.id.tv2, R.id.btn1, R.id.tv3, R.id.tv4 };
        scAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, null, from, to, 0);

        lvData = (ListView) findViewById(R.id.lvData);

        //getSupportLoaderManager().initLoader(0, null, this);
        //linear = (LinearLayout) findViewById(R.id.linear);
        editText = (EditText) findViewById(R.id.editText);
        selectProject = (EditText) findViewById(R.id.selectProject);
        timer = (TextView) findViewById(R.id.timer);
        go = (Button) findViewById(R.id.go);
        //allEds = new ArrayList<View>();

        lvData.setAdapter(scAdapter);
        registerForContextMenu(lvData);
        go.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.Reports:
                Intent reports = new Intent(this, Reports.class);
                startActivity(reports);
                break;
            case R.id.Projects:
                Intent projects = new Intent(this, Projects.class);
                startActivity(projects);
                break;
            case R.id.Team:
                Intent team = new Intent(this, Team.class);
                startActivity(team);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.go:
                if (flag) {
                    timeStart = time.format(new Date(System.currentTimeMillis()));
                    go.setText(R.string.stop);
                    flag = false;
                    run();
                } else {
                    handler.removeCallbacks(this);
                    timer.setText(R.string.timer);
                    go.setText(R.string.go);

                    db.addRec(editText.getText().toString(), selectProject.getText().toString(), R.drawable.ic_play_arrow_black_18dp,
                            time_calc + "sec", timeStart + " - " + time.format(new Date(System.currentTimeMillis())));
                    cursor.requery();


                    /*view = getLayoutInflater().inflate(R.layout.custom_layout, linear, false);

                    TextView tv1 = (TextView) view.findViewById(R.id.tv1);
                    TextView tv2 = (TextView) view.findViewById(R.id.tv2);
                    TextView tv3 = (TextView) view.findViewById(R.id.tv3);
                    TextView tv4 = (TextView) view.findViewById(R.id.tv4);

                    tv1.setText(editText.getText().toString());
                    tv2.setText(selectProject.getText().toString());
                    tv3.setText(time_calc + "sec");
                    tv4.setText(timeStart + " - " + time.format(new Date(System.currentTimeMillis())));*/


                    /*String[] from = new String[] {editText.getText().toString(), selectProject.getText().toString(), time_calc + "sec",
                            timeStart + " - " + time.format(new Date(System.currentTimeMillis()))};*/


                    //linear.addView(view);


                    //view.setId(j);

                    //allEds.add(view);
                    //registerForContextMenu(allEds.get(j));



                    time_calc = 0;
                    j++;
                    flag = true;
                }
                break;
            case R.id.btn1:
                timeStart = time.format(new Date(System.currentTimeMillis()));
                go.setText(R.string.stop);
                flag = false;
                run();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Delete");
        menu.add(0, 1, 0, "Deleted all");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                db.delRec(acmi.id);
                cursor.requery();
                return true;
                //break;
            case 1:
                linear.removeAllViews();
                Toast.makeText(MainActivity.this, "Deleted all", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @Override
    public void run() {
        timer.setText(time_calc++ + " sec");
        handler.postDelayed(this, 1000);
    }
/*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader{
        DB db;

        public MyCursorLoader(Context context, DB db){
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground(){
            Cursor cursor = db.getAllData();
            return cursor;
        }
    }*/
}
