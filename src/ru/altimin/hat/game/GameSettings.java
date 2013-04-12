package ru.altimin.hat.game;

import java.io.Serializable;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class GameSettings implements Serializable {
    public List<String> words;
    // if it's pair game, number of players should be even, and i-th player forms a team with i+1-th player
    public List<Player> players;

    public enum GameType {
        SINGLE, PAIR
    }

    public GameType gameType = GameType.SINGLE;

    public int roundLength = 5; // in seconds;
    public int afterRoundGuessTime = 3; // in seconds
}
