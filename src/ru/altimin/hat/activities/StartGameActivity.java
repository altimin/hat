package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import me.harius.hat.NetworkingManager;
import me.harius.hat.RequestGameTask;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameSettings;

import java.util.concurrent.ExecutionException;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:44
 */
public class StartGameActivity extends Activity {
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
        NetworkingManager netw = new NetworkingManager("http://10.0.2.2:8000/take_data/",
                "http://10.0.2.2:8000/send_result_game/");
        RequestGameTask task = new RequestGameTask(netw);
        // TODO: find out why it's not working
        int gameId = Integer.parseInt(gameIdInput.getText().toString());
        int gamePassword = Integer.parseInt(gamePasswordInput.getText().toString());
        Log.d("StartGame", "id=" + gameId + " password=" + gamePassword);
//        gameId = 3;
//        gamePassword = 30;

        task.execute(gameId, gamePassword);

        // TODO: make error visible to user
        try {
            GameSettings settings = task.get();
            //Log.d("StartGame", "printing GameSettings:\n"+settings.toString()+"\n----\n");

            Intent startGameIntent = new Intent(StartGameActivity.this, GameActivity.class);
            startGameIntent.putExtra("settings", settings);
            StartGameActivity.this.startActivity(startGameIntent);

        } catch (InterruptedException e) {
            Log.d("StartGame", "InterruptedException was caught");
        } catch (ExecutionException e) {
            Log.d("StartGame", "ExecutionException was caught");
        }

    }
}