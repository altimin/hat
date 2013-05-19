package me.harius.hat.test;

import me.harius.hat.*;
import ru.altimin.hat.game.GameResult;
import ru.altimin.hat.game.GameSettings;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 12:36 AM
 */
public class NetworkingTest {
    public static void main(String[] args) throws ConnectionError, InvalidResponseError {
        NetworkingManager networkingManager = NetworkingManager.getDefault();

        GameSettings gameSettings = networkingManager.requestGame(3, 30);
        System.out.println(gameSettings.toString());

//        List<ExplanationResult> fakestats = Arrays.asList(new ExplanationResult(1, 2, 3), new ExplanationResult(3, 1, 4));
        GameResult results = new GameResult(gameSettings.getId());
        // obsolete

        networkingManager.submitGame(results);
    }
}
