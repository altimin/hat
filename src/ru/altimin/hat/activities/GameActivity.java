package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import ru.altimin.hat.game.Game;
import ru.altimin.hat.game.GameSettings;
import ru.altimin.hat.game.Round;
import ru.altimin.hat.game.RoundResult;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 22:23
 */
public class GameActivity extends Activity {
    private static final String DEBUG_RAG = "GameActivity";
    private Game game;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSettings settings = (GameSettings) getIntent().getSerializableExtra("settings");
        game = new Game(settings);
        play();
    }

    private static final int NEW_ROUND_REQUEST_CODE = 0;

    private void play() {
        game.nextRound();
        if (game.hasEnded()) {
            Intent sendResultsIntent = new Intent(this, EndGameActivity.class);
            sendResultsIntent.putExtra("statistics", game.getGameResult());
            startActivity(sendResultsIntent);
        } else {
            Round round = game.getRound();
            Intent newRoundIntent = new Intent(GameActivity.this, RoundActivity.class);
            newRoundIntent.putExtra("round", round);
            GameActivity.this.startActivityForResult(newRoundIntent, NEW_ROUND_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(DEBUG_RAG, "Round ended, on ActivityResult invoked");
        switch (requestCode) {
            case (NEW_ROUND_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    RoundResult roundResult = (RoundResult) data.getExtras().getSerializable("roundresult");
                    game.processRoundResult(roundResult);
                    play();
                }
            }
        }
    }
}