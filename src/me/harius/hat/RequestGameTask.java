package me.harius.hat;

import android.os.AsyncTask;
import android.util.Log;
import ru.altimin.hat.game.GameSettings;

/**
 * User: harius
 * Date: 4/14/13
 * Time: 9:07 PM
 */

public class RequestGameTask extends AsyncTask<Integer, Void, GameSettings> {

    // TODO: rewrite using ThrowingTask

    private NetworkingManager networkingManager;
    private Exception error = null;

    private String DEBUG_TAG = "RequestGame";

    public RequestGameTask(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
    }

    @Override
    protected GameSettings doInBackground(Integer... credentials) {
        if (credentials.length != 2) {
            // TODO: throw normal exception
            throw new RuntimeException("Expected 2 arguments");
        }

        int gameId = credentials[0];
        int password = credentials[1];

        try {
            GameSettings settings = networkingManager.requestGame(gameId, password);
            Log.d(DEBUG_TAG, "From RequestGameTask: settings fetched");
            Log.d(DEBUG_TAG, "From RequestGameTask: " + settings);
            return settings;
        } catch (InvalidResponseError invalidResponseError) {
            error = invalidResponseError;
            return null;
        } catch (ConnectionError connectionError) {
            error = connectionError;
            return null;
        }
    }

    public void throwErrors() throws ConnectionError, InvalidResponseError {
        if(error != null) {
            if(error instanceof ConnectionError) {
                throw (ConnectionError) error;
            }
            else if(error instanceof  InvalidResponseError) {
                throw (InvalidResponseError) error;
            }
            else {
                throw new RuntimeException("Undefined type of error: " + error.getClass().getName());
            }
        }
    }
}
