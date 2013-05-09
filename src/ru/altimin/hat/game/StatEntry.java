package ru.altimin.hat.game;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 10:46 AM
 */
public class StatEntry {

    enum Result {
        OK,
        FAIL
    }

    private int wordId;
    private int playerFrom;
    private int playerTo;
    private Result result;

    public StatEntry(int wordId, int playerFrom, int userTo, Result result) {
        this.wordId = wordId;
        this.playerFrom = playerFrom;
        this.playerTo = userTo;
        this.result = result;
    }

    public int getWordId() {
        return wordId;
    }

    public int getPlayerFrom() {
        return playerFrom;
    }

    public int getPlayerTo() {
        return playerTo;
    }
}
