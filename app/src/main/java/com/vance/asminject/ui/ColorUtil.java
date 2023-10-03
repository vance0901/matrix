package com.vance.asminject.ui;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
    /**
     * 根据RGB色值获取整型颜色
     *
     * @param rgb rgb色值,空代表获取随机颜色
     * @return int型色值
     */
    public static int getColorByRgb(String rgb) {
        int color = Color.WHITE;
        try {
            if (rgb != null) {
                color = Color.parseColor(rgb);
            } else {
                color = Color.parseColor(getRanDomColor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return color;
    }


    /**
     * 获取随机颜色
     *
     * @return 随机六位色值
     */
    public static String getRanDomColor() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("#");
        for (int i = 0; i < 6; i++) {
            stringBuffer.append(getRandomBeen());
        }
        return String.valueOf(stringBuffer);
    }

    /**
     * 获取色值单元
     *
     * @return 单个色值单元值
     */
    public static String getRandomBeen() {
        String been = "";
        int random = getRandom(16);
        if (random > 9) {
            switch (random) {
                case 10:
                    been = "a";
                    break;
                case 11:
                    been = "b";
                    break;
                case 12:
                    been = "c";
                    break;
                case 13:
                    been = "d";
                    break;
                case 14:
                    been = "e";
                    break;
                case 15:
                    been = "f";
                    break;
            }
        } else {
            been = String.valueOf(random);
        }
        return been;
    }

    /**
     * 获取随机整形数字
     *
     * @return 随机数
     */
    public static int getRandom(int range) {
        Random random = new Random();
        return random.nextInt(range);
    }


}