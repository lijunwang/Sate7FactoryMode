package com.sate7.sate7factorymode.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.BuildConfig;
import com.sate7.sate7factorymode.FactoryTestApp;
import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.util.U;
import com.sate7.sate7factorymode.view.AudioView;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;

import java.io.File;
import java.util.Locale;
import java.util.Random;

public class RecordFragment extends BaseFragment implements RecordResultListener {

    final RecordManager recordManager = RecordManager.getInstance();
    private AudioView audioView;
    private Button mBtStart;
    private Button mBtStop;
    private TextView mTvShow;
    private final int MSG_START_RECORD = 0x100;
    private final int MSG_END_RECORD = 0x101;
    private final int MSG_END_COUNT_DOWN = 0x102;
    private int mCounts = 5;
    private ImageView imageView;
    private Drawable drawable;
    private Random random = new Random();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_RECORD:
                    recordManager.start();
                    mHandler.sendEmptyMessageDelayed(MSG_END_COUNT_DOWN, 1000);
                    break;
                case MSG_END_COUNT_DOWN:
                    mCounts--;
                    mTvShow.setText(getResources().getString(R.string.speaker_test_hint, mCounts));
                    mHandler.sendEmptyMessageDelayed(MSG_END_COUNT_DOWN, 1000);
                    if (mCounts == 0) {
                        mHandler.sendEmptyMessage(MSG_END_RECORD);
                    }
                    drawable.setLevel((int) Math.abs(random.nextDouble() * 10000));
                    break;
                case MSG_END_RECORD:
                    recordManager.stop();
                    mTvShow.setText(R.string.speaker_play);
                    audioView.setVisibility(View.INVISIBLE);
                    mHandler.removeMessages(MSG_END_COUNT_DOWN);
                    drawable.setLevel(0);
                    break;
            }
        }
    };

    @Override
    public int setContentView() {
        return R.layout.fragment_record2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioView = view.findViewById(R.id.audioView);
        mBtStart = view.findViewById(R.id.start);
        mBtStop = view.findViewById(R.id.stop);
        imageView = view.findViewById(R.id.voice);
        drawable = imageView.getDrawable();
        mTvShow = view.findViewById(R.id.showInfo);
        mTvShow.setText(getResources().getString(R.string.speaker_test_hint, mCounts));
        mBtStart.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeMessages(MSG_START_RECORD);
        mHandler.removeMessages(MSG_END_COUNT_DOWN);
        mHandler.removeMessages(MSG_END_RECORD);
        recordManager.stop();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        XLog.d("onClick child...");
        switch (view.getId()) {
            case R.id.start:
                recordManager.start();
                break;
            case R.id.stop:
                recordManager.stop();
                break;
            case R.id.success:
                recordManager.stop();
                stopPlay();
                break;
            case R.id.fail:
                recordManager.stop();
                stopPlay();
                break;
            default:
                break;
        }
    }

    private void initRecord() {
        recordManager.init(FactoryTestApp.getInstance(), BuildConfig.DEBUG);
        recordManager.changeRecordConfig(recordManager.getRecordConfig().setSampleRate(16000));
        recordManager.changeFormat(RecordConfig.RecordFormat.WAV);
        String recordDir = String.format(Locale.getDefault(), "%s/Record/com.zlw.main/",
                Environment.getExternalStorageDirectory().getAbsolutePath());
        recordManager.changeRecordDir(recordDir);
        try {
            File[] files = new File(recordDir).listFiles();
            for (File file : files) {
                boolean delete = file.delete();
                XLog.d("initRecord clean file ... " + file.getAbsolutePath() + "," + delete);
            }
        } catch (Exception e) {
            XLog.d("delete exception ... " + e.getMessage());
        }

        audioView.setStyle(AudioView.ShowStyle.getStyle("STYLE_HOLLOW_LUMP"), audioView.getDownStyle());
        audioView.setStyle(AudioView.ShowStyle.getStyle("STYLE_HOLLOW_LUMP"), audioView.getUpStyle());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XLog.d("onActivityCreated ... record 22 ... ");
        initRecord();
        recordManager.setRecordResultListener(this);
        mHandler.sendEmptyMessage(MSG_START_RECORD);
    }

    @Override
    public void onResult(File result) {
        XLog.d("onResult ... " + result.getAbsolutePath() + "," + isVisible() + "," + Thread.currentThread().getName());
        if (isVisible()) {
            playAudio(result);
        }
    }

    private MediaPlayer mediaPlayer;

    private void playAudio(File file) {
        mediaPlayer = MediaPlayer.create(getContext(), Uri.fromFile(file));
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }

    private void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
