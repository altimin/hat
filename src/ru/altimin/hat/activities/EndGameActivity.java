package ru.altimin.hat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import me.harius.hat.ConnectionError;
import me.harius.hat.InvalidResponseError;
import me.harius.hat.NetworkingManager;
import me.harius.hat.SendResultTask;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameResult;

/**
 * User: harius
 * Date: 4/19/13
 * Time: 9:42 AM
 */
public class EndGameActivity extends Activity {     // TODO: more functionality!
    private static String DEBUG_TAG = "EndGame";
    private static String SERVER_ADDRESS = "http://192.168.0.87:8000";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.endgamelayout);


        final TextView goodbyeText = (TextView) findViewById(R.id.goodbyeText);

        GameResult result = (GameResult) getIntent().getSerializableExtra("statistics");

        // TODO: show statistics

        NetworkingManager networkingManager = NetworkingManager.getDefault();

        SendResultTask sendResultTask = new SendResultTask(networkingManager);

        try {
            sendResultTask.execute(result);
            sendResultTask.get();
            sendResultTask.throwException();
            goodbyeText.setText("The results were sent to the server successfully");
        } catch(ConnectionError ex) {
            Log.d(DEBUG_TAG, "Connection error while submitting");
            goodbyeText.setText("No internet connection! We're sorry, but all your data is lost. Forever. Better luck next time!");
        } catch(InvalidResponseError ex) {
            Log.d(DEBUG_TAG, "Invalid server response while submitting");
            goodbyeText.setText("We're sorry, but the server has gone crazy.");
        } catch(Exception ex) {
            Log.d(DEBUG_TAG, "Some error while submitting");
            goodbyeText.setText("We're sorry, but we've got unknown error.");
        }
    }
}