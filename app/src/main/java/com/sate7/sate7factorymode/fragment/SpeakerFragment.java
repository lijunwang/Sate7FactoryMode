package com.sate7.sate7factorymode.fragment;

import android.content.res.AssetFileDescriptor;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;

import java.io.IOException;

public class SpeakerFragment extends HintFragment {
    private MediaPlayer player;

    @Override
    public String setTestContent() {
        return "喇叭测试";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XLog.d("Speaker onDestroyView ...");
    }

    private int pausePosition = -1;
    @Override
    public void onPause() {
        super.onPause();
        XLog.d("Speaker onPause ...");
        if (player != null && player.isPlaying()) {
            player.pause();
            pausePosition = player.getCurrentPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.release();
        }
        startTest();
    }

    private void startTest() {
        XLog.d("Speaker startTest ... ");
        player = MediaPlayer.create(getContext(), R.raw.test);
        player.setLooping(true);

        if(pausePosition >0){
            player.seekTo(pausePosition);
        }
        player.start();
    }
}
