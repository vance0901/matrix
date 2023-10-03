package com.vance.asminject;

import android.app.Application;

import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

public class MatrixInitTask {

    public static void init(Application application){
        Matrix.Builder builder = new Matrix.Builder(application);
        TracePlugin tracePlugin = new TracePlugin(new TraceConfig.Builder()
                .enableEvilMethodTrace(true)
//                .enableSignalAnrTrace(true)
//                .enableAnrTrace(true)
                .build());

        builder.plugin(tracePlugin);
        Matrix matrix = builder.build();
        Matrix.init(matrix);

        matrix.startAllPlugins();
        tracePlugin.getEvilMethodTracer().modifyEvilThresholdMs(200);
    }
}
