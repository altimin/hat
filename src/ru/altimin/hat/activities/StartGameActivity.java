package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private static String SERVER_ADDRESS = "http://192.168.0.87:8000";
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
        findViewById(R.id.quickstartgamebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickStartGame();
            }
        });

        gameIdInput = (EditText) findViewById(R.id.gameIdInput);
        gamePasswordInput = (EditText) findViewById(R.id.gamePasswordInput);
    }

    public void quickStartGame() {
        List<Player> users = Arrays.asList(new Player("Player A"), new Player("Player B"));
        List<Word> words = Arrays.asList(new Word("Abra"), new Word("Cadabra"), new Word("Extermination"), new Word("Безысходность"));
        GameSettings settings = new GameSettings(words, users);
        Intent startGameIntent = new Intent(StartGameActivity.this, GameActivity.class);
        startGameIntent.putExtra("settings", settings);
        StartGameActivity.this.startActivity(startGameIntent);
    }

    public void startGame() {
        NetworkingManager netw = new NetworkingManager(SERVER_ADDRESS + "/take_data/",
                SERVER_ADDRESS + "/send_result_game/");
        RequestGameTask task = new RequestGameTask(netw);

        // TODO: make error visible to user
        try {
            int gameId = Integer.parseInt(gameIdInput.getText().toString());
            int gamePassword = Integer.parseInt(gamePasswordInput.getText().toString());
            Log.d("StartGame", "id=" + gameId + " password=" + gamePassword);

            task.execute(gameId, gamePassword);
            task.throwErrors();
            GameSettings settings = task.get();
            Log.d("StartGame", "settings:\n" + settings.toString());

            Intent startGameIntent = new Intent(StartGameActivity.this, GameActivity.class);
            startGameIntent.putExtra("settings", settings);
            StartGameActivity.this.startActivity(startGameIntent);

        } catch (InterruptedException e) {
            Log.d(DEBUG_TAG, "InterruptedException was caught");
        } catch (ExecutionException e) {
            Log.d(DEBUG_TAG, "ExecutionException was caught.");
            Log.d(DEBUG_TAG, e.getMessage());
            Log.d(DEBUG_TAG, e.getCause().getMessage());
        } catch (ConnectionError connectionError) {
            Log.d(DEBUG_TAG, "ConnectionError was caught:\n" +
                    connectionError.getMessage());
            Log.d(DEBUG_TAG, connectionError.getCause().getMessage());
        } catch (InvalidResponseError invalidResponseError) {
            Log.d(DEBUG_TAG, "InvalidResponseError was caught:\n" +
                    invalidResponseError.getMessage());
            Log.d(DEBUG_TAG, invalidResponseError.getCause().getMessage());
        } catch (NumberFormatException formatError) {
            Log.d(DEBUG_TAG, "One of fields contains non-integer");
        }


    }
}