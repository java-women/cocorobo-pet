package com.java_women.cocorobopet.enums.Feel;

/**
 * Feel APIの処理で使用する時間を格納するEnum.
 * Created by aya on 2016/11/22.
 */
public enum FeelTimeEnum {
    START_MS(0),
    INTERVAL_MS(10000);

    private final int time;

    FeelTimeEnum(final int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

}
