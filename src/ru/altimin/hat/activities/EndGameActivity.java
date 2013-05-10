package ru.altimin.hat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import me.harius.hat.NetworkingManager;
import me.harius.hat.SendResultTask;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameResult;

/**
 * User: harius
 * Date: 4/19/13
 * Time: 9:42 AM
 */
public class EndGameActivity extends Activity {
    private static String DEBUG_TAG = "EndGame";
    private static String SERVER_ADDRESS = "http://192.168.0.87:8000";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.endgamelayout);

        GameResult result = (GameResult) getIntent().getSerializableExtra("statistics");

        // TODO: show statistics

        NetworkingManager networkingManager = NetworkingManager.getDefault();

        SendResultTask sendResultTask = new SendResultTask(networkingManager);

        try {
            sendResultTask.execute(result);
            sendResultTask.throwException();
        } catch(Exception ex) {
            Log.d(DEBUG_TAG, ex.getMessage());
        }
    }
}