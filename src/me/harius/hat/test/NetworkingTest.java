package me.harius.hat.test;

import me.harius.hat.*;
import ru.altimin.hat.game.GameResult;
import ru.altimin.hat.game.GameSettings;
import ru.altimin.hat.game.StatEntry;

import java.util.Arrays;
import java.util.List;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 12:36 AM
 */
public class NetworkingTest {
    public static void main(String[] args) throws ConnectionError, InvalidResponseError {
        NetworkingManager networkingManager = NetworkingManager.getDefault();

        GameSettings game = networkingManager.requestGame(3, 30);
        System.out.println(game.toString());

//        List<StatEntry> fakestats = Arrays.asList(new StatEntry(1, 2, 3), new StatEntry(3, 1, 4));
        GameResult results = new GameResult(game);
        // obsolete

        networkingManager.submitGame(results);
    }
}
