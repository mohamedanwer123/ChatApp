package com.example.cm.chatapp.Activites;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.cm.chatapp.R;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        new moving().execute();
    }

    public void init()
    {
        // hide action bar
        getSupportActionBar().hide();
        //progress bar
        progressBar = findViewById(R.id.MainActivity_progressbar);

    }

    public class moving extends AsyncTask<Void,Void,Void>
    {
        int time=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
            progressBar.setMax(100);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (time<=100)
            {
                progressBar.setProgress(time);
                time+=10;
                try {
                    Thread.sleep(10);
                }catch (Exception e)
                {

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            startActivity(new Intent(MainActivity.this,Login.class));
        }
    }
}
