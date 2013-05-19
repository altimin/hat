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

    private List<Player> players;

    public Game(GameSettings gameSettings, PlayersOrder order) {
        this.gameSettings = gameSettings;
        this.players = order.playersOrder;
        gameResult = new GameResult(gameSettings.getId());
        words = gameSettings.getWords();
        playerOrder = new ArrayList<Pair>();
        int players = getPlayers().size();
        for (int step = 1; step < players; step ++) {
            for (int i = 0; i < players; i ++) {
                playerOrder.add(new Pair(i, (i + step) % players));
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
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
        Player firstPlayer  = getPlayers().get(playerOrder.get(currentPlayingPair).first);
        Player secondPlayer = getPlayers().get(playerOrder.get(currentPlayingPair).second);
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
        for (ExplanationResult explanationResult: result.getStats()) {
            if (explanationResult.wordExpired()) {
                words.remove(explanationResult.getWord());
                Log.d(DEBUG_TAG, "Throwing out word " + explanationResult.getWord().getWord());
            }
        }
        Log.d(DEBUG_TAG, "New words: " + getString(words));
        gameResult.processRoundResult(result);
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
