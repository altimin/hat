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
    private final RoundResult roundResult;

    public Round(Player explainingPlayer, Player guessingPlayer, List<Word> words) {
        this.explainingPlayer = explainingPlayer;
        this.guessingPlayer = guessingPlayer;
        roundResult = new RoundResult(explainingPlayer, guessingPlayer);
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
        return 20; // FIXME: must get rid of this?
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

    ExplanationResult createStatEntry(ExplanationResult.Result result, long time) {
        return new ExplanationResult(getWord(),
                result,
                time);
    }

    public void reportAnswered(long time) {
        ExplanationResult statEntry = createStatEntry(ExplanationResult.Result.OK, time);
        roundResult.addExplanationResult(statEntry);
        ++currentWord;
    }

    public void reportAnsweredLast(long time) {   // TODO: time matters here too
        ExplanationResult explanationResult = createStatEntry(ExplanationResult.Result.GUESSED, time);
        roundResult.addExplanationResult(explanationResult);
        currentWord ++;
    }

    public void reportNotAnswered(long time) {
        ExplanationResult statEntry = createStatEntry(ExplanationResult.Result.NOT_GUESSED, time);
        roundResult.addExplanationResult(statEntry);
        currentWord ++;
    }

    public void reportFatalFail(long time) {
        ExplanationResult statEntry = createStatEntry(ExplanationResult.Result.FAIL, time);
        roundResult.addExplanationResult(statEntry);
        currentWord ++;
    }

    public void reportNonFatalFail(long time) {
        // TODO: fill me!
    }

    public boolean canRevokeReport() {
        return currentWord > 0;
    }

    public void revokeReport() {
        currentWord --;
    }

    public RoundResult getRoundResult() {
        return roundResult;
    }
}
