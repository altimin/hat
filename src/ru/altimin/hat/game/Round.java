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
    private int currentWord = 0;
    private final RoundResult roundResult = new RoundResult();

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

    public int getRoundTime() {
        return 20;
    }

    public boolean hasEnded() {
        return currentWord >= words.size();
    }

    // getWord() call should not change the word.
    // Word should be changed with reportAnswered() call
    public Word getWord() {
        return words.get(currentWord);
    }

    StatEntry createStatEntry(StatEntry.Result result) {
        return new StatEntry(getWord(),
                getGuessingPlayer(),
                getExplainingPlayer(),
                result);
    }

    public void reportAnswered() {
        StatEntry statEntry = createStatEntry(StatEntry.Result.OK);
        roundResult.addStatEntry(statEntry);
        ++currentWord;
    }

    private void reportOtherWordsAsUnused() {
        for (; currentWord < words.size(); currentWord ++) {
            StatEntry statEntry = createStatEntry(StatEntry.Result.UNUSED);
            roundResult.addStatEntry(statEntry);
        }
    }

    public void reportNotAnswered() {
        StatEntry statEntry = createStatEntry(StatEntry.Result.NOT_GUESSED);
        roundResult.addStatEntry(statEntry);
        currentWord ++;
        reportOtherWordsAsUnused();
    }

    public void reportFatalFail() {
        StatEntry statEntry = createStatEntry(StatEntry.Result.FAIL);
        roundResult.addStatEntry(statEntry);
        currentWord ++;
        reportOtherWordsAsUnused();
    }

    public void reportNonFatalFail() {
        reportOtherWordsAsUnused();
    }

    public boolean canRevokeReport() {
        return currentWord > 0;
    }

    public void revokeReport() {
        currentWord --;
    }

    public RoundResult getRoundResult() {
        reportOtherWordsAsUnused();
        return roundResult;
    }
}
