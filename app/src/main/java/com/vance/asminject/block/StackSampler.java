package com.vance.asminject.block;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class StackSampler {

    private Thread mainThread;
    private Handler handler;
    protected AtomicBoolean mShouldSample = new AtomicBoolean(false);
    private static final LinkedHashMap<Long, String> sStackMap = new LinkedHashMap<>();

    public StackSampler() {
        HandlerThread handlerThread = new HandlerThread("MyBlockCanary");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        mainThread = Looper.getMainLooper().getThread();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doSample();
            if (mShouldSample.get()) {
                handler.postDelayed(mRunnable, 300);
            }
        }

    };

    public void start() {
        if (mShouldSample.get()) {
            return;
        }
        mShouldSample.set(true);
        handler.removeCallbacks(mRunnable);
        handler.post(mRunnable);
    }

    public void stop() {
        if (!mShouldSample.get()) {
            return;
        }
        mShouldSample.set(false);
        handler.removeCallbacks(mRunnable);
    }

    private void doSample() {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : mainThread.getStackTrace()) {
            stringBuilder
                    .append(stackTraceElement.toString())
                    .append(SEPARATOR);
        }

        synchronized (sStackMap) {
            if (sStackMap.size() == 100) {
                sStackMap.remove(sStackMap.keySet().iterator().next());
            }
            sStackMap.put(System.currentTimeMillis(), stringBuilder.toString());
        }
    }

    public static final SimpleDateFormat TIME_FORMATTER =
            new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public static final String SEPARATOR = "\r\n";

    public List<String> getThreadStackEntries(long startTime, long endTime) {
        List<String> result = new ArrayList<>();
        synchronized (sStackMap) {
            for (Long entryTime : sStackMap.keySet()) {
                if (startTime < entryTime && entryTime < endTime) {
                    result.add(TIME_FORMATTER.format(entryTime)
                            + SEPARATOR
                            + SEPARATOR
                            + sStackMap.get(entryTime));
                }
            }
        }
        return result;
    }
}
