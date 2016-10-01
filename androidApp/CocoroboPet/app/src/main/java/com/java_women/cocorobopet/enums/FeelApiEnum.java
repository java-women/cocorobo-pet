package com.java_women.cocorobopet.enums;

import com.java_women.cocorobopet.R;

/**
 * Feel APIから渡ってくる顔の種類.
 * Created by aya on 2016/10/01.
 */
public enum FeelApiEnum {
    NORMAL("normal", R.drawable.normal),
    WAAI("waai", R.drawable.waai),
    MATTEE("mattee", R.drawable.mattee),
    SHOBOON("shoboon", R.drawable.shoboon),
    KIRAAN("kiraan", R.drawable.kiraan),
    SUKII("sukii", R.drawable.sukii),
    MUKII("mukii", R.drawable.mukii);

    private final String value;
    private final int image;

    FeelApiEnum(final String value, final int image) {
        this.value = value;
        this.image = image;
    }

    public String getValue() {
        return this.value;
    }

    public int getImage() {
        return this.image;
    }

    public static int getImageFromValue(String value) {
        FeelApiEnum[] feelApiEnumArray = FeelApiEnum.values();
        for (FeelApiEnum feelApiEnum : feelApiEnumArray) {
            if (feelApiEnum.getValue().equals(value)) {
                return feelApiEnum.getImage();
            }
        }
        return 0;
    }
}
