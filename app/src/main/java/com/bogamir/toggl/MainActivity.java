package com.bogamir.toggl;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    LinearLayout Main, layout;
    EditText editText, selectProject;
    TextView timer;
    Button go;
    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    Handler handler = new Handler();
    String timeStart;
    boolean flag = true;
    int i = 0, j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Main = (LinearLayout) findViewById(R.id.Main);
        editText = (EditText) findViewById(R.id.editText);
        selectProject = (EditText) findViewById(R.id.selectProject);
        timer = (TextView) findViewById(R.id.timer);
        go = (Button) findViewById(R.id.go);


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

                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

                    layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setId(j++);


                    LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4f);
                    TextView tv1 = new TextView(this);
                    TextView tv2 = new TextView(this);
                    Button btn1 = new Button(this);
                    ImageButton btn2 = new ImageButton(this);
                    TextView tv3 = new TextView(this);
                    TextView tv4 = new TextView(this);

                    tv1.setText(editText.getText().toString());
                    tv2.setText(selectProject.getText().toString());
                    btn2.setImageResource(R.drawable.ic_play_arrow_black_18dp);
                    tv3.setText(i + "sec");
                    tv4.setText(timeStart + " - " + time.format(new Date(System.currentTimeMillis())));

                    layout.addView(tv1, params1);
                    layout.addView(tv2, params1);
                    layout.addView(btn1, params0);
                    layout.addView(btn2, params0);
                    layout.addView(tv3, params0);
                    layout.addView(tv4, params0);

                    registerForContextMenu(layout);

                    Main.addView(layout);
                   /* Main.addView(chb, lParams0);
                    Main.addView(tv1, lParams1);
                    Main.addView(tv2, lParams1);
                    Main.addView(btn1, lParams0);
                    Main.addView(btn2, lParams0);
                    Main.addView(tv3, lParams0);
                    Main.addView(tv4, lParams0);*/
                    i=0;
                    j++;
                    flag = true;
                }
                break;
            case 0:

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        layout.removeAllViews();
        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

        return super.onContextItemSelected(item);
    }

    @Override
    public void run() {
        timer.setText(i++ + " sec");
        handler.postDelayed(this, 1000);
    }
}
