package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ru.altimin.hat.R;
import ru.altimin.hat.game.Round;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 23:13
 */
public class RoundActivity extends Activity {
    private static final String TAG = "RoundActivity";
    private Round round;

    private void setTimerValue(long milliseconds) {

    }

    private class Timer extends CountDownTimer {
        private Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setTimerValue(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            setTimerValue(0);
            finishRound();
        }
    }

    private void setPlayersNames() {
        TextView player1 = (TextView) findViewById(R.id.explainingplayer);
        TextView player2 = (TextView) findViewById(R.id.guessingplayer);
        player1.setText(round.explainingPlayer.getId()); // TODO: replace id with name
        player2.setText(round.guessingPlayer.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        round = (Round) getIntent().getExtras().getSerializable("round");
        setContentView(R.layout.roundstartlayout);
        setPlayersNames();
        final Button startRoundButton = (Button) findViewById(R.id.startroundbutton);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRound();
            }
        });
    }

    private void setActiveWord(String word) {
        TextView wordTextView = (TextView) findViewById(R.id.explainedword);
        wordTextView.setText(word);
    }

    private void startRound() {
        setContentView(R.layout.roundinprocesslayout);
        setPlayersNames();
        try {
            setActiveWord(round.getNextWord());
        } catch (Round.RunOutOfWordsException e) {
            endRound();
        }
        final Button failedButton = (Button) findViewById(R.id.failbutton);
        failedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "FailButton pressed");
                // TODO: process fail
            }
        });
        final Button okButton = (Button) findViewById(R.id.okbutton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OkButton pressed");
                round.reportAnswered();
                try {
                    setActiveWord(round.getNextWord());
                } catch (Round.RunOutOfWordsException e) {
                    endRound();
                }
            }
        });
        Timer timer = new Timer(5000, 1);
        timer.start();
    }


    private void finishRound() {
        setContentView(R.layout.roundendlayout);
        setPlayersNames();
        try {
            setActiveWord(round.getNextWord());
        } catch (Round.RunOutOfWordsException e) {
            endRound();
        }
        final Button okButton = (Button) findViewById(R.id.correctanswerbutton);
        final Button wrongButton = (Button) findViewById(R.id.wronganswerbutton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "FinalOkReport");
                endRound();
                // TODO: process final ok report
            }
        });
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "FinalWAReport");
                endRound();
                // TODO: process final wa report
            }
        });

    }

    private void endRound() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("roundresult", round.getRoundResult());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }

}