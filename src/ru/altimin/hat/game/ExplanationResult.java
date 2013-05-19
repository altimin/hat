package ru.altimin.hat.game;

import java.io.Serializable;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 10:46 AM
 */
public class ExplanationResult implements Serializable {

    enum Result {
        OK,
        FAIL,
        GUESSED,
        NOT_GUESSED,
    }

    private Word word;
    private Result result;
    private long time; // in seconds

    /*
     * Explanation time is received in milliseconds, but stored in seconds
     */
    public ExplanationResult(Word word, Result result, long time) {
        this.word = word;
        this.result = result;
        if (time > 0) {
            this.time = time / 1000;
        } else {
            this.time = time;
        }
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

    public boolean wordExpired() {
        return result != Result.NOT_GUESSED;
    }
}
