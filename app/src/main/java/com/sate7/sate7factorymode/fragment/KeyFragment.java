package com.sate7.sate7factorymode.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.view.OnKeyListener;

import java.util.HashSet;

import static android.view.KeyEvent.KEYCODE_1;
import static android.view.KeyEvent.KEYCODE_DEL;
import static android.view.KeyEvent.KEYCODE_F8;
import static android.view.KeyEvent.KEYCODE_F9;
import static android.view.KeyEvent.KEYCODE_POWER;
import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

public class KeyFragment extends BaseFragment implements OnKeyListener {
    private final String TAG_IN_KEY_TEST = "key_test";
    private final int INTO = 1;
    private final int EXIT = 0;

    @Override
    public int setContentView() {
        return R.layout.fragment_key;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Settings.System.putInt(getContext().getContentResolver(), TAG_IN_KEY_TEST, INTO);
        initViews(view);
        getContext().registerReceiver(menuReceiver, intentFilter);
        keySet.clear();
    }

    private ImageView mKeyPower;
    private ImageView mKeyVolumeUp;
    private ImageView mKeyVolumeDown;
    private ImageView mKeyMenu;
    private ImageView mKeyHome;
    private ImageView mKeyBack;
    private ImageView mKeyDialerS;
    private ImageView mKeyDialerT;
    private ImageView mKeyOne;
    private ImageView mKeyTwo;
    private ImageView mKeyThree;
    private ImageView mKeyDelete;
    private ImageView mKeyFour;
    private ImageView mKeyFive;
    private ImageView mKeySix;
    private ImageView mKeyArrows;
    private ImageView mKeySeven;
    private ImageView mKeyEight;
    private ImageView mKeyNine;
    private ImageView mKeySpeaker;
    private ImageView mKeyXin;
    private ImageView mKeyZero;
    private ImageView mKeyJing;
    private ImageView mKeyBT;
    private ImageView mKeySos;
    private HashSet<Integer> keySet = new HashSet<>();
    private final int TOTAL_KEY = 25;

    private void initViews(View view) {
        mKeyPower = view.findViewById(R.id.key_power);
        mKeyVolumeUp = view.findViewById(R.id.key_volume_up);
        mKeyVolumeDown = view.findViewById(R.id.key_volume_down);
        mKeyMenu = view.findViewById(R.id.key_menu);
        mKeyHome = view.findViewById(R.id.key_home);
        mKeyBack = view.findViewById(R.id.key_back);
        mKeyDialerS = view.findViewById(R.id.key_s);
        mKeyDialerT = view.findViewById(R.id.key_t);
        mKeyOne = view.findViewById(R.id.key_1);
        mKeyTwo = view.findViewById(R.id.key_2);
        mKeyThree = view.findViewById(R.id.key_3);
        mKeyDelete = view.findViewById(R.id.key_delete);
        mKeyFour = view.findViewById(R.id.key_4);
        mKeyFive = view.findViewById(R.id.key_5);
        mKeySix = view.findViewById(R.id.key_6);
        mKeyArrows = view.findViewById(R.id.key_arrow);
        mKeySeven = view.findViewById(R.id.key_7);
        mKeyEight = view.findViewById(R.id.key_8);
        mKeyNine = view.findViewById(R.id.key_9);
        mKeySpeaker = view.findViewById(R.id.key_sound);
        mKeyXin = view.findViewById(R.id.key_xin);
        mKeyZero = view.findViewById(R.id.key_0);
        mKeyJing = view.findViewById(R.id.key_jing);
        mKeyBT = view.findViewById(R.id.key_bt);
        mKeySos = view.findViewById(R.id.key_sos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Settings.System.putInt(getContext().getContentResolver(), TAG_IN_KEY_TEST, EXIT);
        getContext().unregisterReceiver(menuReceiver);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        XLog.d("KeyFragment onKeyDown .. " + keyEvent.getKeyCode());
        switch (keyEvent.getKeyCode()) {
            case KEYCODE_POWER:
                mKeyPower.setImageResource(R.drawable.key_power);
                break;
            case KEYCODE_VOLUME_UP:
                mKeyVolumeUp.setImageResource(R.drawable.key_volume_up);
                break;
            case KEYCODE_VOLUME_DOWN:
                mKeyVolumeDown.setImageResource(R.drawable.key_volume_down);
                break;
            case KeyEvent.KEYCODE_MENU:
                mKeyMenu.setImageResource(R.drawable.key_menu);
                break;
            case KeyEvent.KEYCODE_HOME:
                mKeyHome.setImageResource(R.drawable.key_home);
                break;
            case KeyEvent.KEYCODE_BACK:
                mKeyBack.setImageResource(R.drawable.key_back);
                break;
            case KeyEvent.KEYCODE_F11:
                mKeyDialerS.setImageResource(R.drawable.key_s);
                break;
            case KeyEvent.KEYCODE_F10:
                mKeyDialerT.setImageResource(R.drawable.key_t);
                break;
            case KeyEvent.KEYCODE_1:
                mKeyOne.setImageResource(R.drawable.key_one);
                break;
            case KeyEvent.KEYCODE_2:
                mKeyTwo.setImageResource(R.drawable.key_two);
                break;
            case KeyEvent.KEYCODE_3:
                mKeyThree.setImageResource(R.drawable.key_three);
                break;
            case KEYCODE_DEL:
                mKeyDelete.setImageResource(R.drawable.key_delete);
                break;
            case KeyEvent.KEYCODE_4:
                mKeyFour.setImageResource(R.drawable.key_four);
                break;
            case KeyEvent.KEYCODE_5:
                mKeyFive.setImageResource(R.drawable.key_five);
                break;
            case KeyEvent.KEYCODE_6:
                mKeySix.setImageResource(R.drawable.key_six);
                break;
            case KEYCODE_F9:
                mKeyArrows.setImageResource(R.drawable.key_arrow);
                break;
            case KeyEvent.KEYCODE_7:
                mKeySeven.setImageResource(R.drawable.key_seven);
                break;
            case KeyEvent.KEYCODE_8:
                mKeyEight.setImageResource(R.drawable.key_eight);
                break;
            case KeyEvent.KEYCODE_9:
                mKeyNine.setImageResource(R.drawable.key_nine);
                break;
            case KEYCODE_F8:
                mKeySpeaker.setImageResource(R.drawable.key_speaker);
                break;
            case KeyEvent.KEYCODE_STAR:
                mKeyXin.setImageResource(R.drawable.key_xin);
                break;
            case KeyEvent.KEYCODE_0:
                mKeyZero.setImageResource(R.drawable.key_zero);
                break;
            case KeyEvent.KEYCODE_POUND:
                mKeyJing.setImageResource(R.drawable.key_jing);
                break;
            case KeyEvent.KEYCODE_F7:
                mKeyBT.setImageResource(R.drawable.key_bt);
                break;
            case KeyEvent.KEYCODE_F12:
                mKeySos.setImageResource(R.drawable.key_sos);
                break;
            default:
                break;

        }
        boolean add = keySet.add(keyEvent.getKeyCode());
        XLog.d("onKeyDown ... " + add + "," + keySet.size());
        if (keySet.size() == TOTAL_KEY) {
            showResultButtons();
        }
    }

    private MenuReceiver menuReceiver = new MenuReceiver();
    private IntentFilter intentFilter = new IntentFilter("com.key.test.menu");

    private class MenuReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mKeyMenu.setImageResource(R.drawable.key_menu);
            keySet.add(KeyEvent.KEYCODE_MENU);
        }
    }

}
