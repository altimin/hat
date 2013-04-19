package ru.altimin.hat.activities;

import android.app.Activity;
import android.os.Bundle;
import ru.altimin.hat.game.GameResult;

/**
 * User: harius
 * Date: 4/19/13
 * Time: 9:42 AM
 */
public class EndGameActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameResult result = (GameResult) getIntent().getSerializableExtra("statistics");
        // TODO: show and send statistics
    }
}