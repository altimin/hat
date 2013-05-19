package ru.altimin.hat.game;

import java.io.Serializable;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 10:46 AM
 */
public class StatEntry implements Serializable {

    enum Result {
        OK,
        FAIL,
        NOT_GUESSED,
        UNUSED
    }

    private Word word;
    private Result result;
    private long time; // in milliseconds

    public StatEntry(Word word, Result result, long time) {
        this.word = word;
        this.result = result;
        this.time = time;
    }

    public Word getWord() {
        return word;
    }

    public int getWordId() {
        return word.getId();
    }

    public Result getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }
}
