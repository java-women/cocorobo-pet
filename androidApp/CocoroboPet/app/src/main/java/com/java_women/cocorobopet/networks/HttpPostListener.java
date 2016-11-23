package com.java_women.cocorobopet.networks;

/**
 * Http送信クラスからの結果をActivityに通知するためのリスナー
 * Activity側でimplementsを行う
 */
public interface HttpPostListener {
    abstract public void postCompletion(byte[] response);
    abstract public void postFailure();
}
