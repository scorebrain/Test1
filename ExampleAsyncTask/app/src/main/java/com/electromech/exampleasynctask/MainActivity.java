package com.electromech.exampleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn;
    AsyncTask<Void, Void, String> runningTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // detect the view that was "clicked"
        switch (view.getId()) {
            case R.id.button1:
                if (runningTask != null) runningTask.cancel(true);
                runningTask = new LongOperation();
                runningTask.execute();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cancel running task(s) to avoid memory leaks
        if (runningTask != null) runningTask.cancel(true);
    }

    private final class LongOperation extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // we were cancelled, stop sleeping!
                }
            }
            return "Executed";
        }

        @Override
        protected void onPreExecute() {
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText("Beginning...");
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText(result);
        }
    }
}