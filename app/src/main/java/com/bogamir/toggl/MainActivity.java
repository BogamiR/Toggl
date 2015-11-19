package com.bogamir.toggl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    LinearLayout Main;
    EditText editText, selectProject;
    TextView timer;
    Button go;
    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    Handler handler = new Handler();
    boolean flag = true;
    int i = 0;

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
            case R.id.timerMenu:
                Intent timer = new Intent(this, MainActivity.class);
                startActivity(timer);
                break;
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
                    go.setText(R.string.stop);
                    flag = false;
                    run();
                } else {
                    handler.removeCallbacks(this);
                    timer.setText(R.string.timer);
                    go.setText(R.string.go);
                    String timeStart = time.format(new Date(System.currentTimeMillis()));
                    LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    LinearLayout.LayoutParams lParams0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    CheckBox chb = new CheckBox(this);
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

                    Main.addView(chb, lParams0);
                    Main.addView(tv1, lParams1);
                    Main.addView(tv2, lParams1);
                    Main.addView(btn1, lParams0);
                    Main.addView(btn2, lParams0);
                    Main.addView(tv3, lParams0);
                    Main.addView(tv4, lParams0);
                    i=0;
                    flag = true;
                }
                break;
        }
    }


    @Override
    public void run() {
        timer.setText(i++ + " sec");
        handler.postDelayed(this, 1000);
    }
}
