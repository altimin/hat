package ru.altimin.hat.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:19
 */
public class GameResult implements Serializable {

    @SerializedName("gameId")
    private int id;

    private List<RoundResult> roundResults;

    public GameResult(int id) {
        this.id = id;
        this.roundResults = new ArrayList<RoundResult>();
    }

    private int currentRoundId = 0;

    public void processRoundResult(RoundResult roundResult) {
        roundResults.add(roundResult);
    }
}
