package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import me.harius.hat.ConnectionError;
import me.harius.hat.InvalidResponseError;
import me.harius.hat.NetworkingManager;
import me.harius.hat.RequestGameTask;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameSettings;
import ru.altimin.hat.game.Player;
import ru.altimin.hat.game.Word;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:44
 */
public class StartGameActivity extends Activity {
    private static String DEBUG_TAG = "StartGame";
    EditText gameIdInput, gamePasswordInput;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgamelayout);

        final Button startGameButton = (Button) findViewById(R.id.startgamebutton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        gameIdInput = (EditText) findViewById(R.id.gameIdInput);
        gamePasswordInput = (EditText) findViewById(R.id.gamePasswordInput);
    }

    public void startGame() {
        NetworkingManager networkingManager = NetworkingManager.getDefault();
        RequestGameTask task = new RequestGameTask(networkingManager);

        try {
            int gameId = Integer.parseInt(gameIdInput.getText().toString());
            int gamePassword = Integer.parseInt(gamePasswordInput.getText().toString());
            Log.d(DEBUG_TAG, "id=" + gameId + " password=" + gamePassword);

            task.execute(gameId, gamePassword);
            GameSettings settings = task.get();
            task.throwErrors();
            Log.d(DEBUG_TAG, "settings:\n" + settings.toString());

            Intent startGameIntent = new Intent(StartGameActivity.this, PlayersOrderingActivity.class);
            startGameIntent.putExtra("settings", settings);
            StartGameActivity.this.startActivity(startGameIntent);
        } catch (InterruptedException e) {
            Log.d(DEBUG_TAG, "InterruptedException was caught");
            showMessage("Internal application error :'(");
        } catch (ExecutionException e) {
            Log.d(DEBUG_TAG, "ExecutionException was caught.");
            Log.d(DEBUG_TAG, e.getMessage());
            Log.d(DEBUG_TAG, e.getCause().getMessage());
            showMessage("Internal application error :'(");
        } catch (ConnectionError connectionError) {
            Log.d(DEBUG_TAG, "ConnectionError was caught:\n" +
                    connectionError.getMessage());
            Log.d(DEBUG_TAG, connectionError.getCause().getMessage());
            showMessage("Internet connection problems");
        } catch (InvalidResponseError invalidResponseError) {
            Log.d(DEBUG_TAG, "InvalidResponseError was caught:\n" +
                    invalidResponseError.getMessage());
            Log.d(DEBUG_TAG, invalidResponseError.getCause().getMessage());
            showMessage("Wrong game id or password");
        } catch (NumberFormatException formatError) {
            Log.d(DEBUG_TAG, "One of fields contains non-integer");
            showMessage("One of fields contains non-integer");
        }


    }

    private void showMessage(String text) {
        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }
}