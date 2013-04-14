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
        return 2*roundNumber>gameSettings.users.size();
    }

    private static int roundNumber = 0;

    public Round nextRound() {
        ++ roundNumber;
        Player firstPlayer = gameSettings.users.get(2*roundNumber - 1);
        Player secondPlayer = gameSettings.users.get(2*roundNumber);
        // TODO: correct order?
        if (roundNumber % 2 == 0) {
            // TODO: choose words correctly
            return new Round(firstPlayer, secondPlayer, gameSettings.words);
        } else {
            return new Round(secondPlayer, firstPlayer, gameSettings.words);
        }
    }

    // TODO: fill the gaps here!
    public void processRoundResult(RoundResult result) {
    }

    public GameResult getGameResult() {
        return new GameResult();
    }
}
