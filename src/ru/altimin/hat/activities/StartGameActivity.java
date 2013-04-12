package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameSettings;
import me.harius.hat.Networking;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:44
 */
public class StartGameActivity extends Activity {
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
    }

    public void startGame() {
        Networking netw = new Networking("http://localhost:8000/take_data/",
                "http://localhost:8000/send_result_game/");


        GameSettings settings = netw.requestGame(3, 30);
        Intent startGameIntent = new Intent(StartGameActivity.this, GameActivity.class);
        startGameIntent.putExtra("settings", settings);
        StartGameActivity.this.startActivity(startGameIntent);
    }
}