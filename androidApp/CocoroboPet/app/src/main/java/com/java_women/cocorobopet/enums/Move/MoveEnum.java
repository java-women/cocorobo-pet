package com.java_women.cocorobopet.enums.Move;

/**
 * Move APIの処理で使用するEnum.
 * Created by aya on 2016/11/22.
 */
public enum MoveEnum {
    TAG("MoveAPI"),
    STOP("stop"),
    API_URL("http://javajo-api.azurewebsites.net/cocorobo-pet/api/moves/toko");

    private final String value;

    MoveEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
