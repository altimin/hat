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
    private Player playerFrom;
    private Player playerTo;
    private Result result;

    public StatEntry(Word word, Player playerFrom, Player userTo, Result result) {
        this.word = word;
        this.playerFrom = playerFrom;
        this.playerTo = userTo;
        this.result = result;
    }

    public Word getWord() {
        return word;
    }

    public Player getPlayerTo() {
        return playerTo;
    }

    public Player getPlayerFrom() {
        return playerFrom;
    }

    public int getWordId() {
        return word.getId();
    }

    public int getPlayerFromId() {
        return playerFrom.getId();
    }

    public int getPlayerToId() {
        return playerTo.getId();
    }

    public Result getResult() {
        return result;
    }
}
