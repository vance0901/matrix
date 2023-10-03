package com.vance.asminject;

import android.content.Context;
import android.os.SystemClock;

import com.tencent.matrix.trace.core.AppMethodBeat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class VanceTest implements Serializable {

    public static void a() {

        b();
    }


    public static void b() {
        c();
    }

    public static void c() {
        d();
    }

    public static void d() {
        e();
    }

    public static void e() {
        //SystemClock.sleep(400); //模拟耗时 业务
        f();
    }

    public static void f() {
        g();
    }

    public static void g() {
        h();
    }

    public static void h() {
        i();
    }

    public static void i() {
        j();
    }

    public static void j() {
        k();
    }

    public static void k() {
        //
        l();
    }

    public static void l() {
        m();
    }

    public static void m() {
        n();
    }

    public static void n() {
        o();
    }

    public static void o() {
        //SystemClock.sleep(100);//模拟耗时操作
    }


}
