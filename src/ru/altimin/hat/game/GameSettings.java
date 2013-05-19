package ru.altimin.hat.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class GameSettings implements Serializable {
    public enum GameType {
        SINGLE, PAIR
    }

    private GameType gameType;

    @SerializedName("gameId")
    private int id;

    private List<Word> words;

    // if it's pair game, number of players should be even, and i-th player forms a team with i+1-th player
    @SerializedName("users")
    private List<Player> players;

    public GameSettings(List<Word> words, List<Player> players) {
        if(players.size() == 0 || players.size() % 2 != 0) {
            //TODO: throw nice exception
            throw new RuntimeException("Invalid user count");
        }

        this.words = words;
        this.players = players;
        this.id = 777;
    }

    private int roundLength; // in seconds;
    private int afterRoundGuessTime; // in seconds

    private int turns = -1;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<game> id: " + getId());

        builder.append(" words: ");
        for(Word word : getWords()) {
            builder.append(word.getWord() + " ");
        }

        builder.append("players: ");
        for(Player player : getPlayers()) {
            builder.append(player.getId() + " ");
        }

        return builder.toString();
    }

    public int getId() {
        return id;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getAfterRoundGuessTime() {
        return afterRoundGuessTime;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getRoundLength() {
        return roundLength;
    }

    public int getTurns() {
        return turns;
    }
}
