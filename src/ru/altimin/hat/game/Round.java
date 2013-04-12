package ru.altimin.hat.game;

import java.io.Serializable;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class Round implements Serializable {

    public final Player explainingPlayer;
    public final Player guessingPlayer;

    public Round(Player explainingPlayer, Player guessingPlayer) {
        this.explainingPlayer = explainingPlayer;
        this.guessingPlayer = guessingPlayer;
    }

    public class RunOutOfWordsException extends Exception {
        public RunOutOfWordsException() {
            super();
        }

        public RunOutOfWordsException(String detailMessage) {
            super(detailMessage);
        }

        public RunOutOfWordsException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public RunOutOfWordsException(Throwable throwable) {
            super(throwable);
        }
    }

    private int counter = 1;

    public String getNextWord() throws RunOutOfWordsException {
        return "слово " + Integer.toString(counter ++);
    }

    public void reportAnswered() {
    }

    public void reportNotAnswered() {
    }

    public void reportFatalFail() {
    }

    public void reportNonFatalFail() {

    }

    public void revokeReport() {
    }

    public RoundResult getRoundResult() {
        return new RoundResult();
    }
}
