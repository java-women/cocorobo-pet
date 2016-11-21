package com.java_women.cocorobopet.enums.Move;

/**
 * Move APIの処理で使用する時間を格納するEnum.
 * Created by aya on 2016/11/22.
 */
public enum MoveTimeEnum {
    START_MS(2000),
    INTERVAL_MS(5000);

    private final int time;

    MoveTimeEnum(final int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

}
