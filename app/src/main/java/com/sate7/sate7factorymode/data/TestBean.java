package com.sate7.sate7factorymode.data;

import androidx.annotation.NonNull;


public class TestBean {
    private String title;
    private String fragmentName;
    private boolean show = true;
    private boolean fullscreen = false;

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getTitle() {
        return title;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public TestBean(String title, String fragmentName) {
        this.title = title;
        this.fragmentName = fragmentName;
    }

    public TestBean() {
        this.title = "";
        this.fragmentName = "";
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + title + "," + fragmentName + "," + show + "," + fullscreen + "]";
    }
}
