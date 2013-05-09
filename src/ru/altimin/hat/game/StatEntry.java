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
    private int userFrom;
    private int userTo;
    private Result result;

    public StatEntry(int wordId, int userFrom, int userTo, Result result) {
        this.wordId = wordId;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.result = result;
    }

    public int getWordId() {
        return wordId;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public int getUserTo() {
        return userTo;
    }
}
