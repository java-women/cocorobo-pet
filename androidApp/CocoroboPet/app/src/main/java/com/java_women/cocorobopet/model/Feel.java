package com.java_women.cocorobopet.model;

/**
 * Feel.
 */
public class Feel {

    /** 実行結果 */
    private boolean result;

    /** 感情(目玉の種類) */
    private String feel;

    public Feel(boolean result, String feel) {
        this.result = result;
        this.feel = feel;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    @Override
    public String toString() {
        return "result: " + this.result + ", feel: " + this.feel;
    }

}
