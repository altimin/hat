package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    // TODO: handle pause, rotation events etc.

    private static final String TAG = "RoundActivity";
    private Round round;

    private void setTimerValue(long milliseconds) {
        final int SECONDS_PER_MINUTE = 60;
        TextView timerTextView = (TextView) findViewById(R.id.timer);
        int seconds = (int)(milliseconds / 1000);
        int minutes = seconds / SECONDS_PER_MINUTE;
        seconds %= SECONDS_PER_MINUTE;
        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
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

    private void createRound() {
        setContentView(R.layout.roundstartlayout);
        // setting player names
        ((TextView) findViewById(R.id.explainingplayername)).setText(round.getExplainingPlayer().getName());
        ((TextView) findViewById(R.id.guessingplayername)).setText(round.getGuessingPlayer().getName());
        // setting startroundbutton handler
        ((Button) findViewById(R.id.startroundbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRound();
            }
        });
        // setting correct time
        final int MILLISECONDS_PER_SECOND = 1000;
        setTimerValue(round.getRoundTime() * MILLISECONDS_PER_SECOND);
    }

    private void setActiveWord(String word) {
        ((TextView) findViewById(R.id.word)).setText(word);
    }

    private void startRound() {
        setContentView(R.layout.roundinprocesslayout);
        // setting time
        final int MILLISECONDS_PER_SECOND = 1000;
        setTimerValue(round.getRoundTime() * MILLISECONDS_PER_SECOND);
        // starting timer
        Timer timer = new Timer(round.getRoundTime() * MILLISECONDS_PER_SECOND, 1);
        timer.start();
        // TODO: add failbutton handler
        // TODO: add revertbutton handler
        // setting okbutton handler
        setActiveWord(round.getWord().getWord());
        findViewById(R.id.okbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.reportAnswered();
                if (round.hasEnded()) {
                    endRound();
                } else {
                    setActiveWord(round.getWord().getWord());
                }
            }
        });
    }

    private void finishRound() { // when time is over
        setContentView(R.layout.roundendlayout);
        // TODO: start after-round guess timer
        // TODO: add failbutton handler
        // TODO: add revertbutton handler
        // adding wabutton handler
        findViewById(R.id.wabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.reportNotAnswered();
                endRound();
            }
        });
        // adding okbutton handler
        findViewById(R.id.okbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.reportAnswered();
                endRound();
            }
        });
        // show the last word
        ((TextView)findViewById(R.id.word)).setText(round.getWord().getWord());
    }

    private void endRound() { // when round is over
        Intent resultIntent = new Intent();
        resultIntent.putExtra("roundresult", round.getRoundResult());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

//    @Override
//    public void onBackPressed() {
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        round = (Round) getIntent().getExtras().getSerializable("round");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createRound();
    }
}