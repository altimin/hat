package ru.altimin.hat.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private long currentTime = 0;

    private void setTimerValue(long milliseconds) {
        final int SECONDS_PER_MINUTE = 60;
        TextView timerTextView = (TextView) findViewById(R.id.timer);
        int seconds = (int)(milliseconds / 1000);
        int minutes = seconds / SECONDS_PER_MINUTE;
        seconds %= SECONDS_PER_MINUTE;
        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private class GameTimer extends CountDownTimer {
        private GameTimer(long millisInFuture) {
            super(millisInFuture, 1);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setTimerValue(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            setTimerValue(0);
            playSound();
            finishRound();
        }
    }

    private class AfterGameTimer extends CountDownTimer {
        private AfterGameTimer(long millisInFuture) {
            super(millisInFuture, 1);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            playSound();
        }
    }

    private class OneMoreTimer extends CountDownTimer {
        private static final long INF = (long) 1e9;

        private OneMoreTimer() {
            super(INF, 1);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            currentTime = INF - millisUntilFinished;
        }

        @Override
        public void onFinish() {
        }
    }

    GameTimer timer;
    AfterGameTimer afterGameTimer;
    OneMoreTimer oneMoreTimer = new OneMoreTimer();  // FIXME: what the hell?

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
        oneMoreTimer.start();
        setContentView(R.layout.roundinprocesslayout);
        // setting time
        final int MILLISECONDS_PER_SECOND = 1000;
        setTimerValue(round.getRoundTime() * MILLISECONDS_PER_SECOND);
        // starting timer
        timer = new GameTimer(round.getRoundTime() * MILLISECONDS_PER_SECOND);
        timer.start();
        // adding failbutton handler
        createFailButtonHandler();
        // TODO: add revertbutton handler
        findViewById(R.id.revertbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (round.canRevokeReport()) {
                    new AlertDialog.Builder(RoundActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Do you really want to put this word back into the hat?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    round.revokeReport();
                                    setActiveWord(round.getWord().getWord());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                }
            }
        });
        // setting okbutton handler
        setActiveWord(round.getWord().getWord());
        findViewById(R.id.okbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.reportAnswered(currentTime);
                if (round.hasEnded()) {
                    endRound();
                } else {
                    setActiveWord(round.getWord().getWord());
                }
            }
        });
    }

    private void finishRound() { // when time is over
        setTimerValue(0);
        setContentView(R.layout.roundendlayout);
        // TODO: start after-round guess timer
        // TODO: add failbutton handler
        // TODO: add revertbutton handler
        // adding failbutton handler
        createFailButtonHandler();
        // adding wabutton handler
        findViewById(R.id.wabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RoundActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Really not guessed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                round.reportNotAnswered(currentTime);
                                endRound();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });
        // adding okbutton handler
        findViewById(R.id.okbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RoundActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Really guessed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                round.reportAnsweredLast(currentTime);
                                endRound();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        // show the last word
        ((TextView)findViewById(R.id.word)).setText(round.getWord().getWord());
        afterGameTimer = new AfterGameTimer(round.getAfterRoundGuessTime() * 1000);
        afterGameTimer.start();
    }

    private void endRound() { // when round is over
        Intent resultIntent = new Intent();
        timer.cancel();
        if (afterGameTimer != null) {
            afterGameTimer.cancel();
        }
        oneMoreTimer.cancel();
        resultIntent.putExtra("roundresult", round.getRoundResult());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    private void createFailButtonHandler() { // TODO: timer must be stopped while this event?
        findViewById(R.id.failbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RoundActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Have you really failed this word?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                round.reportFatalFail(currentTime);
                                endRound();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        round = (Round) getIntent().getExtras().getSerializable("round");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        createRound();
    }

    private void playSound() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }
}