package me.harius.hat;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import ru.altimin.hat.game.GameResult;
import ru.altimin.hat.game.GameSettings;

/**
 * User: harius
 * Date: 4/11/13
 * Time: 10:08 PM
 */
public class Networking {
    private final String requestAddress;
    private final String submitAddress;
    private final Gson gson;

    public Networking(String requestAddress, String submitAddress) {
        this.requestAddress = requestAddress;
        this.submitAddress = submitAddress;
        this.gson = new Gson();
    }

    public GameSettings requestGame(int gameId, int password) {
        HttpRequest request = HttpRequest.post(requestAddress)
                .form("gameId", gameId)
                .form("password", password);

        String json = request.body();
//        System.out.println(json);
        GameSettings game = gson.fromJson(json, GameSettings.class);

        return game;
    }

    public void submitGame(GameResult game) {
        String json = gson.toJson(game);

        HttpRequest post = HttpRequest.post(submitAddress)
                .form("data_game", json);
        post.code();
    }
}
