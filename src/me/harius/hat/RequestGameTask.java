package me.harius.hat;

import android.os.AsyncTask;
import ru.altimin.hat.game.GameSettings;

/**
 * User: harius
 * Date: 4/14/13
 * Time: 9:07 PM
 */
public class RequestGameTask extends AsyncTask<Integer, Void, GameSettings> {

    private NetworkingManager networkingManager;

    public RequestGameTask(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
    }

    @Override
    protected GameSettings doInBackground(Integer... credentials) {
        // TODO: handle errors
        int gameId = credentials[0];
        int password = credentials[1];
        // TODO: check argument validity
        return networkingManager.requestGame(gameId, password);
    }
}
