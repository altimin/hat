package ru.altimin.hat.game;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:15
 */

public class Game {
    public Game(GameSettings gameSettings) {
    }

    public boolean hasEnded() {
        return false;
    }

    private static int roundNumber = 0;

    public Round nextRound() {
        ++ roundNumber;
        String FIRST_PLAYER_NAME = "Гарри Поттер";
        String SECOND_PLAYER_NAME = "Лорд Волан-де-морт";
        if (roundNumber % 2 == 0) {
            return new Round(new Player(FIRST_PLAYER_NAME), new Player(SECOND_PLAYER_NAME));
        } else {
            return new Round(new Player(SECOND_PLAYER_NAME), new Player(FIRST_PLAYER_NAME));
        }
    }

    public void processRoundResult(RoundResult result) {

    }

    public GameResult getGameResult() {
        return new GameResult();
    }
}
