package com.sate7.sate7factorymode.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.MainActivity;
import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.view.OnItemTestCompleteListener;
import com.sate7.sate7factorymode.view.TouchPadView;

import java.util.Random;

public class SimpleInfoFragment extends BaseFragment implements OnSeekBarChangeListener, OnItemTestCompleteListener {
    private SeekBar mBrightnessSeekBar;
    private ViewGroup root;
    private TouchPadView touchPadView;

    @Override
    public int setContentView() {
        return R.layout.fragment_simple_info;
    }

    private final int MSG_START_COLOR_TEST = 0x100;
    private final int MSG_STOP_COLOR_TEST = 0x102;
    private final int MSG_LIGHT_TEST_START = 0x10;
    private Random lightRandom = new Random();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_COLOR_TEST:
                    buttonsContainer.setVisibility(View.GONE);
                    testColor();
                    break;
                case MSG_STOP_COLOR_TEST:
                    buttonsContainer.setVisibility(View.GONE);
                    showResultButtons();
                    break;
                case MSG_LIGHT_TEST_START:
                    int light = Math.abs(lightRandom.nextInt() % 255);
                    XLog.d("handleMessage ... " + light);
                    mBrightnessSeekBar.setProgress(light);
                    mHandler.sendEmptyMessageDelayed(MSG_LIGHT_TEST_START, 500);
                    break;
            }
        }
    };
    private int mCurrentColor = 0;
    private final int TEST_COUNTS = 8;

    private void testColor() {
        switch (mCurrentColor % 5) {
            case 0:
                root.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                root.setBackgroundColor(Color.BLACK);
                break;
            case 2:
                root.setBackgroundColor(Color.RED);
                break;
            case 3:
                root.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                root.setBackgroundColor(Color.BLUE);
                break;
            default:
                break;

        }
        XLog.d("mCurrentColor % 5 ==" + mCurrentColor % 5 + "," + mCurrentColor);
        mCurrentColor++;
        if (mCurrentColor == TEST_COUNTS) {
            mHandler.sendEmptyMessage(MSG_STOP_COLOR_TEST);
            root.setBackgroundColor(Color.WHITE);
        } else {
            mHandler.sendEmptyMessageDelayed(MSG_START_COLOR_TEST, 1000);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeMessages(MSG_STOP_COLOR_TEST);
        mHandler.removeMessages(MSG_START_COLOR_TEST);
        mHandler.removeMessages(MSG_LIGHT_TEST_START);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XLog.d("onActivityCreated ... " + testBean + "," + getActivity());
        if (testBean != null && testBean.getTitle().equals("背光")) {
            //Test 背光
            mBrightnessSeekBar.setVisibility(View.VISIBLE);
            mBrightnessSeekBar.setOnSeekBarChangeListener(this);
            mHandler.sendEmptyMessageDelayed(MSG_LIGHT_TEST_START, 100);
        } else if (testBean != null && testBean.getTitle().equals("色彩")) {
            //Test 色彩
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.fullScreen();
            mHandler.sendEmptyMessageDelayed(MSG_START_COLOR_TEST, 0);
        } /*else if (testBean != null && testBean.getTitle().equals("灵敏度")) {
            touchPadView.setVisibility(View.VISIBLE);
            touchPadView.setOnItemTestListener(this);
        }*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBrightnessSeekBar = view.findViewById(R.id.seekBar);
        root = view.findViewById(R.id.root);
        touchPadView = view.findViewById(R.id.touchPadView);
    }

    private void setScreenBrightness(int brightness) {
        Window localWindow = getActivity().getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = brightness / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            boolean success = Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
            XLog.d("onProgressChanged ... " + progress + "," + success);
            setScreenBrightness(progress);
        } catch (Exception e) {
            XLog.d("onProgressChanged Exception: " + e.getMessage());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        XLog.d("onStartTrackingTouch ... ");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        XLog.d("onStopTrackingTouch ... ");
    }

    @Override
    public void onItemComplete() {
        showResultButtons();
    }

}
