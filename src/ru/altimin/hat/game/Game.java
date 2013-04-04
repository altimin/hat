package ru.altimin.hat.game;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:15
 */

public class Game {
    public Game(GameSettings gameSettings) {
    }

    public boolean hasEnded();

    public Round nextRound();

    public void processRoundResult(RoundResult result);

    public GameResult getGameResult();
}
