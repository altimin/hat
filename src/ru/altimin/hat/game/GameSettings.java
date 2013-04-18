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
//    public enum GameType {
//        SINGLE, PAIR
//    }
//
//    public GameType gameType = GameType.SINGLE;

    @SerializedName("gameId")
    private int id;

    private List<Word> words;

    // if it's pair game, number of players should be even, and i-th player forms a team with i+1-th player
    private List<Player> users;

//    public int roundLength = 5; // in seconds;
//    public int afterRoundGuessTime = 3; // in seconds

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<game> id: " + getId());

        builder.append(" words: ");
        for(Word word : getWords()) {
            builder.append(word.getWord() + " ");
        }

        builder.append("users: ");
        for(Player user : getUsers()) {
            builder.append(user.getId() + " ");
        }

        return builder.toString();
    }

    public int getId() {
        return id;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Player> getUsers() {
        return users;
    }
}
