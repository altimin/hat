package ru.altimin.hat.game;

import java.util.ArrayList;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:15
 */

public class  Game {
    private GameSettings gameSettings;
    private GameResult gameResult;
    private static int roundNumber = 0;

    private List<Word> words;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        gameResult = new GameResult(gameSettings.getId());
        words = gameSettings.getWords();
    }

    private int getFirstIndex() {
        return (roundNumber - 1) / 2;
    }

    private int getSecondIndex() {
        return getFirstIndex() + 1;
    }

    public boolean hasEnded() {
        return getSecondIndex() >= gameSettings.getPlayers().size();
    }

    // This must be called BEFORE the first round
    public void nextRound() {
        ++roundNumber;
    }

    // This method doesn't change round number
    // It's done by nextRound()
    public Round getRound() {
        Player firstPlayer = gameSettings.getPlayers().get(getFirstIndex());
        Player secondPlayer = gameSettings.getPlayers().get(getSecondIndex());

        if (roundNumber % 2 == 0) {
            return new Round(firstPlayer, secondPlayer, words);
        } else {
            return new Round(secondPlayer, firstPlayer, words);
        }
    }

    public void processRoundResult(RoundResult result) {
        List<Word> newWords = new ArrayList<Word>();
        for (StatEntry statEntry: result.getStats()) {
            if (statEntry.getResult() != StatEntry.Result.FAIL && statEntry.getResult() != StatEntry.Result.OK) {
                newWords.add(statEntry.getWord());
            }
        }
        words = newWords;
        gameResult.processRoundResult(result);
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
