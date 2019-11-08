package com.sate7.sate7factorymode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

public class TempTestActivity extends AppCompatActivity {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<String> test = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        test.add("AAAA");
        test.add("BBBB");
        test.add("CCCC");
        test.add("DDDD");

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };

        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(View view) {
        for (String msg : test) {
            new MyAsyncTask().execute(msg);
        }
    }

    public void startAA(View view) {
        for (String msg : test) {
            new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msg);
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            XLog.d("doInBackground ... " + strings[0] + "," + Thread.currentThread().getName() + "," + simpleDateFormat.format(new Date(System.currentTimeMillis())));
            return null;
        }
    }
}
