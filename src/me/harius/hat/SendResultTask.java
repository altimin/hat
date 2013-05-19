package me.harius.hat;

import ru.altimin.hat.game.GameResult;

/**
 * User: harius
 * Date: 5/9/13
 * Time: 10:11 PM
 */

public class SendResultTask extends ThrowingTask<GameResult, Void, Void> {

    private final NetworkingManager networkingManager;

    public SendResultTask(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
    }

    @Override
    protected Void doInBackground(GameResult... params) {
        if (params.length != 1) {
            // TODO: throw normal exception
            saveException(new RuntimeException("Expected 1 argument"));
            return null;
        }

        try {
            networkingManager.submitGame(params[0]);
        } catch (ConnectionError connectionError) {
            saveException(connectionError);
            return null;
        } catch (InvalidResponseError connectionError) {
            saveException(connectionError);
            return null;
        }

        return null;
    }
}
