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
    private Exception error = null;

    public RequestGameTask(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
    }

    @Override
    protected GameSettings doInBackground(Integer... credentials) {
        int gameId = credentials[0];
        int password = credentials[1];
        // TODO: check argument validity


        try {
            return networkingManager.requestGame(gameId, password);
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
