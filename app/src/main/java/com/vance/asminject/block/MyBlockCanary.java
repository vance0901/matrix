package com.vance.asminject.block;

import android.os.Looper;
import android.util.Log;
import android.util.Printer;

import java.util.ArrayList;
import java.util.List;

public class MyBlockCanary {

    private StackSampler stackSampler;
    private boolean mPrintingStarted = false;
    private long mStartTimestamp;

    public MyBlockCanary() {
        stackSampler = new StackSampler();
    }

    public void start() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                if (!mPrintingStarted) {
                    mStartTimestamp = System.currentTimeMillis();
                    mPrintingStarted = true;
                    stackSampler.start();
                } else {
                    mPrintingStarted = false;
                    final long endTime = System.currentTimeMillis();
                    mPrintingStarted = false;
                    if (endTime - mStartTimestamp > 500) {
                        doBlock(mStartTimestamp, endTime);
                    }
                    stackSampler.stop();
                }
            }


        });
    }


    private void doBlock(long mStartTimestamp, long endTime) {
        List<String> threadStackEntries = stackSampler
                .getThreadStackEntries(mStartTimestamp, endTime);
        for (String threadStackEntry : threadStackEntries) {
            Log.e("vance", threadStackEntry);
        }
    }

}
