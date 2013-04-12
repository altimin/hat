package me.harius.hat.test;

import me.harius.hat.*;

import java.util.Arrays;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 12:36 AM
 */
public class NetworkingTest {
    public static void main(String[] args) {
        Networking netw = new Networking("http://localhost:8000/take_data/",
                                           "http://localhost:8000/send_result_game/");

        GameSettings game = netw.requestGame(3, 30);
        System.out.println(game.toString());

        GameResults results = new GameResults();
        results.gameId = game.gameId;
        results.statistics = Arrays.asList(new StatEntry(1, 2, 3), new StatEntry(3, 1, 4));

        netw.submitGame(results);
    }
}
