package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.view.WindowManager;

/**
 * Created by Tales of symphonia on 28/01/2016.
 */
public class WindowManagerInstancier {

    private static WindowManagerInstancier instance;
    private static WindowManager windowManager;

    private WindowManagerInstancier() {
    }

    public static WindowManagerInstancier getInstance() {
        if(instance == null)
        {
            instance = new WindowManagerInstancier();
        }
        return instance;
    }

    public static void setWindowManager(WindowManager wM) {
        windowManager = wM;
    }

    public static WindowManager getWindowManager() {
        return windowManager;
    }
}
