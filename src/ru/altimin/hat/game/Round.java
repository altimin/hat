package ru.altimin.hat.game;

import java.io.Serializable;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class Round implements Serializable {

    private final Player explainingPlayer;
    private final Player guessingPlayer;
    private final List<Word> words;

    public Round(Player explainingPlayer, Player guessingPlayer, List<Word> words) {
        this.explainingPlayer = explainingPlayer;
        this.guessingPlayer = guessingPlayer;
        this.words = words;
    }

    public Player getExplainingPlayer() {
        return explainingPlayer;
    }

    public Player getGuessingPlayer() {
        return guessingPlayer;
    }

    public List<Word> getWords() {
        return words;
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

    public int getRoundTime() {
        return 20;
    }

    private int currentRound = 0;

    // getWord() call should not change the word. Word should be changed with reportAnswered() call
    public String getWord() throws RunOutOfWordsException {
        return "Слово " + Integer.toString(currentRound);
        //if (currentRound >= getWords().size()) {
        //    throw new RunOutOfWordsException();
        //}
        //return getWords().get(currentRound).getWord();
    }

    public void reportAnswered() {
        currentRound ++;
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
