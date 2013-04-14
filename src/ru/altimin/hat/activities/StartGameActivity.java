package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import me.harius.hat.NetworkingTask;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameSettings;
import me.harius.hat.Networking;

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
        Networking netw = new Networking("http://192.168.1.5:8000/take_data/",
                "http://192.168.1.5:8000/send_result_game/");
        NetworkingTask task = new NetworkingTask(netw);
        // TODO: find out why it's not working
        int gameId = 3; //Integer.parseInt(gameIdInput.getText().toString());
        int gamePassword = 30; //Integer.parseInt(gameIdInput.getText().toString());
        task.execute(gameId, gamePassword);

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