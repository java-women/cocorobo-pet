package com.java_women.cocorobopet.constants;

/**
 * Move APIの処理で使用する定数.
 *
 * Androidでstaticとかアレだけど…
 * 今回は落ちても別に害はないと思うので可読性をとってstaticで指定します。
 *
 * Created by aya on 2016/10/01.
 */
public final class MoveConst {

    public static final String MOVE_TAG = "MoveAPI";

    public static final String STOP = "stop";

    public static final int MOVE_START_MS = 2000;
    public static final int MOVE_INTERVAL_MS = 5000;

    private MoveConst() {
    }

}
