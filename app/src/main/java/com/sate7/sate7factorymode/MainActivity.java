package com.sate7.sate7factorymode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sate7.sate7factorymode.data.TestAdapter;
import com.sate7.sate7factorymode.data.TestBean;
import com.sate7.sate7factorymode.fragment.BaseFragment;
import com.sate7.sate7factorymode.fragment.KeyFragment;
import com.sate7.sate7factorymode.fragment.StartFragment;

public class MainActivity extends AppCompatActivity {
    private StartFragment startFragment;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TestBean currentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        startFragment = new StartFragment();
        goToMain();
    }

    private void showToolBarBack(boolean show) {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(show);
            actionBar.setDisplayHomeAsUpEnabled(show);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goTo(BaseFragment fragment, TestBean bean) {
        currentBean = bean;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.act_content, fragment);
        if (bean != null) {
            fragment.setTestBean(bean);
        }
        showToolBarBack(bean != null);
        if (bean != null && bean.isFullscreen()) {
            fullScreen();
        }
        transaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        XLog.d("onPrepareOptionsMenu 11 ...");
        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(android.R.drawable.ic_menu_help);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        XLog.d("onCreateOptionsMenu 22 ...");
        return true;
    }

    public void goToMain() {
        goTo(startFragment, null);
        mFirstBack = true;
        exitFullScreen();
        if (mInAutoTestMode) {
            mCurrentTestBeanIndex++;
            startTestItem();
        }
    }

    private boolean mFirstBack = true;

    private AlertDialog exitDialog = null;
    private void showExitDialog(){
        if(exitDialog == null){
            exitDialog = new AlertDialog.Builder(this).
                    setTitle(R.string.exit_auto_title).
                    setMessage(R.string.exit_auto_content).
                    setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    exitAutoTest();
                    goToMain();
                    return;
                }
            }).create();
        }
        exitDialog.show();
    }
    @Override
    public void onBackPressed() {
        XLog.d("onBackPressed ... " + startFragment.isVisible());
        if (startFragment.isVisible()) {
            finish();
        } else {
            if (mInAutoTestMode) {
                showExitDialog();
                return;
            }
            if (currentBean != null && currentBean.getTitle().equals("按键") && mFirstBack) {
                mFirstBack = false;
                return;
            }

            XLog.d("onBackPressed 22 ... " + mFirstBack);
            if (currentBean != null && currentBean.getTitle().equals("按键") && !mFirstBack) {
                BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.act_content);
                if (baseFragment instanceof KeyFragment) {
                    XLog.d("onBackPressed 33 ... " + baseFragment.isResultShowing());
                    if (baseFragment.isResultShowing()) {
                        baseFragment.hideResultButtons();
                    } else {
                        baseFragment.showResultButtons();
                    }
                    return;
                }
            }
            goToMain();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        XLog.d("MainActivity onKeyDown ... " + keyCode);
        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.act_content);
        if (baseFragment instanceof KeyFragment) {
            ((KeyFragment) baseFragment).onKeyDown(event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void fullScreen() {
        toolbar.setVisibility(View.GONE);
    }

    public void exitFullScreen() {
        toolbar.setVisibility(View.VISIBLE);
    }


    private int mCurrentTestBeanIndex = 0;
    private boolean mInAutoTestMode = false;

    public void exitAutoTest() {
        mInAutoTestMode = false;
    }

    ;

    public void startAutoTest() {
        XLog.d("main activity startAutoTest ...");
        mInAutoTestMode = true;
        mCurrentTestBeanIndex = 0;
        startTestItem();
    }

    private void startTestItem() {
        if (mCurrentTestBeanIndex <= FactoryTestApp.mTestList.size() - 1) {
            TestBean currentBean = FactoryTestApp.mTestList.get(mCurrentTestBeanIndex);
            try {
                Class baseFragmentClass = Class.forName("com.sate7.sate7factorymode.fragment." + currentBean.getFragmentName());
                BaseFragment baseFragment = (BaseFragment) baseFragmentClass.newInstance();
                goTo(baseFragment, currentBean);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("startFragment", "Exception :" + e.getMessage());
            }
        } else {
            XLog.d("startTestItem ... index is out of index ..");
            Toast.makeText(this, R.string.auto_test_ok, Toast.LENGTH_LONG).show();
            exitAutoTest();
        }

    }

}
