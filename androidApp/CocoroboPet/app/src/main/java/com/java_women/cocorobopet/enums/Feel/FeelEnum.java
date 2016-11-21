package com.java_women.cocorobopet.enums.Feel;

/**
 * Feel APIの処理で使用するEnum.
 * Created by aya on 2016/11/22.
 */
public enum FeelEnum {
    TAG("FeelAPI"),
    API_PREF_KEY("apiKey"),
    API_URL("http://javajo-api.azurewebsites.net/cocorobo-pet/api/feels/toko");

    private final String value;

    FeelEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
