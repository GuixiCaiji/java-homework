package com.java.yesheng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeopleTextActivity extends AppCompatActivity {

    protected ImageView iv;

    private void Load_pic(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getpic(url);
            }
        }).start();
    }

    private void showpic(final Bitmap bm) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iv.setImageBitmap(bm);
            }
        });
    }

    private void getpic(String url){
        try {
            if (!url.equals("")) {
                Log.i("enter","enter");
                URL img_obj = new URL(url);
                HttpURLConnection img_conn = (HttpURLConnection) img_obj.openConnection();
                img_conn.connect();
                InputStream imgis = img_conn.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(imgis);
                showpic(bm);
            }
        } catch (Exception e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_text);

        androidx.appcompat.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String info = intent.getStringExtra("Info");
        String url = intent.getStringExtra("Avater");
        iv = findViewById(R.id.people_avater_pic);


        Load_pic(url);

        TextView text_affiliation = findViewById(R.id.text_affiliation);
        TextView text_bio = findViewById(R.id.text_bio);
        TextView text_edu = findViewById(R.id.text_edu);
        TextView text_position = findViewById(R.id.text_position);
        TextView text_work = findViewById(R.id.text_work);


        try {
            JSONObject jsonobj = new JSONObject(info);
            String affiliation = jsonobj.getString("affiliation");
            text_affiliation.setText(affiliation);
            String bio = jsonobj.getString("bio");
            text_bio.setText(bio);
            String edu = jsonobj.getString("edu");
            text_edu.setText(edu);
            String position = jsonobj.getString("position");
            text_position.setText(position);
            String work = jsonobj.getString("work");
            text_work.setText(work);
        } catch (Exception e) {

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
