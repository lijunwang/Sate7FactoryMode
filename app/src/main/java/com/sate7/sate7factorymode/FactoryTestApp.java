package com.sate7.sate7factorymode;

import android.app.Application;

import com.sate7.sate7factorymode.data.FactoryTestOptionJsonReader;
import com.sate7.sate7factorymode.data.TestBean;

import java.io.IOException;
import java.util.ArrayList;

public class FactoryTestApp extends Application {
    public static ArrayList<TestBean> mTestList = new ArrayList<>();
    private static FactoryTestApp mInstance;

    public static FactoryTestApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<TestBean> all = FactoryTestOptionJsonReader.read(FactoryTestApp.this);
                    for (TestBean bean : all) {
                        if (bean.isShow()) {
                            mTestList.add(bean);
                        } else {
                            XLog.d("onCreate delete do not show ... " + bean.getTitle());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    XLog.d("FactoryTestApp ..." + e.getMessage());
                }
            }
        }.start();
    }
}
