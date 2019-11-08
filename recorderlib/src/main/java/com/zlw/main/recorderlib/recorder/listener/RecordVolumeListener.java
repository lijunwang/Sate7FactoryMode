package com.zlw.main.recorderlib.recorder.listener;

/**
 * @author zhaolewei on 2018/7/11.
 */
public interface RecordVolumeListener {

    /**
     * 当前的录音录音音量大小
     *
     * @param volume 当前音量
     */
    void onVolume(double volume);

}
