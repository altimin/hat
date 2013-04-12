package me.harius.hat;

import java.util.ArrayList;
import java.util.List;

/**
 * User: harius
 * Date: 4/11/13
 * Time: 10:10 PM
 */
public class GameSettings {
    public int gameId;
    public List<Word> words = new ArrayList<Word>();
    public List<User> users = new ArrayList<User>();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<game> id: " + gameId);

        builder.append(" words: ");
        for(Word word : words) {
            builder.append(word.word + " ");
        }

        builder.append("users: ");
        for(User user : users) {
            builder.append(user.userId + " ");
        }

        return builder.toString();
    }
}
