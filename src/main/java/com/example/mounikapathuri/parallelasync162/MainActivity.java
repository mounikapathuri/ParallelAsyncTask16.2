package com.example.mounikapathuri.parallelasync162;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    Start firstDownload, secondDownload, thirdDownload, fourthDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        progressBar1 = (ProgressBar) findViewById(R.id.pbDownload1);
        progressBar2 = (ProgressBar) findViewById(R.id.pbDownload2);
        progressBar3 = (ProgressBar) findViewById(R.id.pbDownload3);
        progressBar4 = (ProgressBar) findViewById(R.id.pbDownload4);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstDownload = new Start(progressBar1);
                firstDownload.execute();
                secondDownload = new Start(progressBar2);
                startParallelTask(secondDownload);
                // secondDownload.execute();
                thirdDownload = new Start(progressBar3);
                thirdDownload.execute();
                fourthDownload = new Start(progressBar4);
                startParallelTask(fourthDownload);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startParallelTask(Start task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else
            task.execute();
    }

    class Start extends AsyncTask<Void, Integer, Void> {
        ProgressBar progressBar;

        public Start(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 12; i++) {
                sleep();
                publishProgress(i * 10);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this,"Download Completed !!!!",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


