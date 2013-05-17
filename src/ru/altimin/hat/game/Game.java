package ru.altimin.hat.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:15
 */

public class Game {
    private final static String DEBUG_TAG = "Game";

    private GameSettings gameSettings;
    private GameResult gameResult;

    private List<Word> words;

    private List<Pair> playerOrder;
    private int currentTurn = 0;
    private int currentPlayingPair = 0;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        gameResult = new GameResult(gameSettings.getId());
        words = gameSettings.getWords();
        playerOrder = new ArrayList<Pair>();
        for (int i = 0; i < gameSettings.getPlayers().size(); i ++) {
            for (int j = 0; j < gameSettings.getPlayers().size(); j ++) {
                if (i != j) {
                    playerOrder.add(new Pair(i, j));
                }
            }
        }
        Collections.shuffle(playerOrder);
    }

    private class Pair {
        public int first, second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public boolean hasEnded() {
        return (words.size() == 0) || (currentTurn >= gameSettings.getTurns() && gameSettings.getTurns() != -1);
    }

    public void nextRound() {
        currentPlayingPair ++;
        if (currentPlayingPair >= playerOrder.size()) {
            currentPlayingPair = 0;
            currentTurn ++;
        }
    }

    // This method doesn't change round number
    // It's done by nextRound()

    public Round getRound() {
        Player firstPlayer  = gameSettings.getPlayers().get(playerOrder.get(currentPlayingPair).first);
        Player secondPlayer = gameSettings.getPlayers().get(playerOrder.get(currentPlayingPair).second);
        return new Round(firstPlayer, secondPlayer, words);
    }

    private String getString(List<Word> l) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Word i: l) {
            stringBuilder.append(i.getWord());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public void processRoundResult(RoundResult result) {
        Log.d(DEBUG_TAG, "Old words: " + getString(words));
        List<Word> newWords = new ArrayList<Word>();
        for (StatEntry statEntry: result.getStats()) {
            if (statEntry.getResult() != StatEntry.Result.FAIL && statEntry.getResult() != StatEntry.Result.OK) {
                newWords.add(statEntry.getWord());
            }
        }
        words = newWords;
        Log.d(DEBUG_TAG, "New words: " + getString(words));
        gameResult.processRoundResult(result);
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
