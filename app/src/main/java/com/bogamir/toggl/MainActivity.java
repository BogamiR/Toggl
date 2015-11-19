package com.bogamir.toggl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout Main;
    EditText editText;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Main = (LinearLayout) findViewById(R.id.Main);
        editText = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                CheckBox chb = new CheckBox(this);
                TextView tv1 = new TextView(this);
                TextView tv2 = new TextView(this);
                Button btn1 = new Button(this);
                Button btn2 = new Button(this);
                Button btn3 = new Button(this);
                TextView tv3 = new TextView(this);
                TextView tv4 = new TextView(this);
                tv1.setText(editText.getText().toString());

                Main.addView(chb, lParams);
                Main.addView(tv1, lParams);
                Main.addView(tv2, lParams);
                Main.addView(btn1, lParams);
                Main.addView(btn2, lParams);
                Main.addView(btn3, lParams);
                Main.addView(tv3, lParams);
                Main.addView(tv4, lParams);
                break;

        }
    }
}
