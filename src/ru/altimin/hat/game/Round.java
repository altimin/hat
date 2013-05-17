package ru.altimin.hat.game;

import java.io.Serializable;
import java.util.Collections;
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
        Collections.shuffle(words);
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

    public int getAfterRoundGuessTime() {
        return 3;
    }

    public boolean hasEnded() {
        return currentWord >= words.size();
    }

    // getWord() call should not change the word.
    // Word should be changed with reportAnswered() call
    public Word getWord() {
        return words.get(currentWord);
    }

    StatEntry createStatEntry(StatEntry.Result result, long time) {
        return new StatEntry(getWord(),
                getExplainingPlayer(),
                getGuessingPlayer(),
                result,
                time);
    }

    public void reportAnswered(long time) {
        StatEntry statEntry = createStatEntry(StatEntry.Result.OK, time);
        roundResult.addStatEntry(statEntry);
        ++currentWord;
    }

    private void reportOtherWordsAsUnused() {
        for (; currentWord < words.size(); currentWord ++) {
            StatEntry statEntry = createStatEntry(StatEntry.Result.UNUSED, -1);
            roundResult.addStatEntry(statEntry);
        }
    }

    public void reportNotAnswered(long time) {
        StatEntry statEntry = createStatEntry(StatEntry.Result.NOT_GUESSED, time);
        roundResult.addStatEntry(statEntry);
        currentWord ++;
        reportOtherWordsAsUnused();
    }

    public void reportFatalFail(long time) {
        StatEntry statEntry = createStatEntry(StatEntry.Result.FAIL, time);
        roundResult.addStatEntry(statEntry);
        currentWord ++;
        reportOtherWordsAsUnused();
    }

    public void reportNonFatalFail(long time) {
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
