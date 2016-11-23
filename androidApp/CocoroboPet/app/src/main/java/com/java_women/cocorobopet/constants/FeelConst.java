package com.java_women.cocorobopet.constants;

/**
 * Feel APIの処理で使用する定数.
 *
 * Androidでstaticとかアレだけど…
 * 今回は落ちても別に害はないと思うので可読性をとってstaticで指定します。
 *
 * Created by aya on 2016/10/01.
 */
public final class FeelConst {

    public static final String FEEL_TAG = "FeelAPI";

    public static final int FEEL_START_MS = 0;
    public static final int FEEL_INTERVAL_MS = 10000;

    public static final String API_PREF_KEY = "apiKey";

    private FeelConst() {
    }

}
