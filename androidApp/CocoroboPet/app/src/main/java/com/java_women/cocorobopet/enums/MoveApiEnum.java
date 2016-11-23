package com.java_women.cocorobopet.enums;

/**
 * COCOROBOに移動命令を行うAPIのEnum.
 * Created by aya on 2016/11/23.
 */
public enum MoveApiEnum {
    FORWARD("forward"),
    LEFT("left"),
    RIGHT("right"),
    STOP("stop"),
    GOHOME("gohome");

    private final String value;

    MoveApiEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
