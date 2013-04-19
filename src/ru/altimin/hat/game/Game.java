package ru.altimin.hat.game;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:15
 */

public class Game {
    private GameSettings gameSettings;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public boolean hasEnded() {
//        return false;
        return (2*roundNumber - 1) >= gameSettings.getUsers().size();
    }

    private static int roundNumber = 0;

    public void nextRound() {
        ++roundNumber;
    }

    public Round getRound() {
        Player firstPlayer = gameSettings.getUsers().get(2 * roundNumber - 2);
        Player secondPlayer = gameSettings.getUsers().get(2 * roundNumber - 1);
        // TODO: correct order?
        if (roundNumber % 2 == 0) {
            // TODO: choose words correctly
            return new Round(firstPlayer, secondPlayer, gameSettings.getWords());
        } else {
            return new Round(secondPlayer, firstPlayer, gameSettings.getWords());
        }
    }

    // TODO: fill the gaps here!
    public void processRoundResult(RoundResult result) {
    }

    public GameResult getGameResult() {
        return new GameResult(0, null);
    }
}
