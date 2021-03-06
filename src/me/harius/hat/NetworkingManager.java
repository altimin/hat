package me.harius.hat;

import android.util.Log;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import ru.altimin.hat.game.GameResult;
import ru.altimin.hat.game.GameSettings;

import java.net.ConnectException;

/**
 * User: harius
 * Date: 4/11/13
 * Time: 10:08 PM
 */
public class NetworkingManager {

    private static String SERVER_ADDRESS = "http://peaceful-mesa-4969.herokuapp.com";
    private final String DEBUG_TAG = "NetworkingManager";

    private final String requestAddress;
    private final String submitAddress;
    private final Gson gson;

    public static NetworkingManager getDefault() {
        return new NetworkingManager(SERVER_ADDRESS + "/take_data/",
                SERVER_ADDRESS + "/send_result_game/");
    }

    private NetworkingManager(String requestAddress, String submitAddress) {
        this.requestAddress = requestAddress;
        this.submitAddress = submitAddress;
        this.gson = new GsonBuilder()
                .setPrettyPrinting().create();
    }

    public GameSettings requestGame(int gameId, int password) throws InvalidResponseError, ConnectionError {
        HttpRequest request = null;
        try {
            request = HttpRequest.post(requestAddress)
                    .form("gameId", gameId)
                    .form("password", password);
            int code = request.code();
            Log.d(DEBUG_TAG, "returned code " + code);
            if (code != 200) {
                throw new InvalidResponseError(request, new Exception(("Wrong id or password")));
            }
            String json = request.body();
            if (json.equals("")) {
                throw new InvalidResponseError(request, new Exception("JSON is empty"));
            }
            Log.d(DEBUG_TAG, "from request game: received json:#"+json+"#");
            GameSettings game = gson.fromJson(json, GameSettings.class);
            return game;
        }
        catch(HttpRequest.HttpRequestException httpError) {
            throw new ConnectionError("Error while requesting game", httpError);
        }
        catch(JsonSyntaxException formatError) {
            throw new InvalidResponseError(request, formatError);
        }
    }

    public void submitGame(GameResult game) throws ConnectionError, InvalidResponseError {
        String json = gson.toJson(game);
        Log.d(DEBUG_TAG, "Formed JSON results:");
        Log.d(DEBUG_TAG, json);

        try {
            HttpRequest post = HttpRequest.post(submitAddress)
                    .form("data_game", json);
            int code = post.code();
            if (code != 200) {
                throw new InvalidResponseError(post, new Exception("Bad submission"));
            }
        }
        catch(HttpRequest.HttpRequestException httpError) {
            throw new ConnectionError("Error while submitting game", httpError);
        }
    }
}
