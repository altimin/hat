package me.harius.hat;

import android.os.AsyncTask;
import ru.altimin.hat.game.GameSettings;

/**
 * User: harius
 * Date: 4/14/13
 * Time: 9:07 PM
 */
public class NetworkingTask extends AsyncTask<Integer, Void, GameSettings> {

    private Networking networking;

    public NetworkingTask(Networking networking) {
        this.networking = networking;
    }

    @Override
    protected GameSettings doInBackground(Integer... credentials) {
        int gameId = credentials[0];
        int password = credentials[1];
        // TODO: check argument validity
        return networking.requestGame(gameId, password);
    }
}
