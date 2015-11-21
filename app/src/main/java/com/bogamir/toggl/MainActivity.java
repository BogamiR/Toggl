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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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
    List<View> allEds;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linear = (LinearLayout) findViewById(R.id.linear);
        editText = (EditText) findViewById(R.id.editText);
        selectProject = (EditText) findViewById(R.id.selectProject);
        timer = (TextView) findViewById(R.id.timer);
        go = (Button) findViewById(R.id.go);
        allEds = new ArrayList<View>();

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

                    view = getLayoutInflater().inflate(R.layout.custom_layout, null);

                    TextView tv1 = (TextView) view.findViewById(R.id.tv1);
                    TextView tv2 = (TextView) view.findViewById(R.id.tv2);
                    TextView tv3 = (TextView) view.findViewById(R.id.tv3);
                    TextView tv4 = (TextView) view.findViewById(R.id.tv4);


                    tv1.setText(editText.getText().toString());
                    tv2.setText(selectProject.getText().toString());
                    tv3.setText(time_calc + "sec");
                    tv4.setText(timeStart + " - " + time.format(new Date(System.currentTimeMillis())));

                    view.setId(j);

                    allEds.add(view);
                    registerForContextMenu(allEds.get(j));

                    linear.addView(view);

                    time_calc = 0;
                    j++;
                    flag = true;
                }
                break;
            case R.id.btn2:
                timeStart = time.format(new Date(System.currentTimeMillis()));
                go.setText(R.string.stop);
                flag = false;
                run();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "Delete");
        menu.add(0, 1, 0, "Deleted all");
        //super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                for (Integer i = 0; i < allEds.size(); i++)
                    if (view.getId() == i) {
                        ((LinearLayout) allEds.get(i).getParent()).removeView(allEds.get(i));
                        allEds.remove(i);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                break;
            case 1:
                linear.removeAllViews();
                Toast.makeText(MainActivity.this, "Deleted all", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void run() {
        timer.setText(time_calc++ + " sec");
        handler.postDelayed(this, 1000);
    }
}
