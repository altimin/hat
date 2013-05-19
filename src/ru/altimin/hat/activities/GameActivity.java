package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import ru.altimin.hat.game.*;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 22:23
 */
public class GameActivity extends Activity {
    private Game game;

    private final String DEBUG_TAG = "GameActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSettings settings = (GameSettings) getIntent().getSerializableExtra("settings");
        PlayersOrder order = (PlayersOrder) getIntent().getSerializableExtra("order");
        game = new Game(settings, order);
        play();
    }

    private static final int NEW_ROUND_REQUEST_CODE = 0;

    private void play() {
        Log.d(DEBUG_TAG, "Running play()");
        if (game.hasEnded()) {
            Intent sendResultsIntent = new Intent(this, EndGameActivity.class);
            sendResultsIntent.putExtra("statistics", game.getGameResult());
            startActivity(sendResultsIntent);
        } else {
            Round round = game.getRound();
            Intent newRoundIntent = new Intent(GameActivity.this, RoundActivity.class);
            newRoundIntent.putExtra("round", round);
            Log.d(DEBUG_TAG, "New round starting: " + round.getExplainingPlayer().getName() + " -> " + round.getGuessingPlayer().getName());
            GameActivity.this.startActivityForResult(newRoundIntent, NEW_ROUND_REQUEST_CODE);
            Log.d(DEBUG_TAG, "Started");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (NEW_ROUND_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(DEBUG_TAG, "RoundResult!");
                    RoundResult roundResult = (RoundResult) data.getExtras().getSerializable("roundresult");
                    game.processRoundResult(roundResult);
                    game.nextRound();
                    play();
                }
            }
        }
    }
}