package com.java_women.cocorobopet.models;

/**
 * Move.
 */
public class Move {

    /** 実行結果 */
    private boolean result;

    /** 動き */
    private String moveCommand;

    public Move(boolean result, String moveCommand) {
        this.result = result;
        this.moveCommand = moveCommand;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMoveCommand() {
        return moveCommand;
    }

    public void setMoveCommand(String move) {
        this.moveCommand = moveCommand;
    }

    @Override
    public String toString() {
        return "result: " + this.result + ", moveCommand: " + this.moveCommand;
    }

}
