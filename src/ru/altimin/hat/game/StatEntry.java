package ru.altimin.hat.game;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 10:46 AM
 */
public class StatEntry {

    private int wordId;
    private int userFrom;
    private int userTo;
    // TODO: Result, Timestamps, etc.

    public StatEntry(int wordId, int userFrom, int userTo) {
        this.wordId = wordId;
        this.userFrom = userFrom;
        this.userTo = userTo;
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
