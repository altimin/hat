package ru.altimin.hat.game;

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
    private GameSettings gameSettings;
    private List<StatEntry> statistics;

    public GameResult(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.statistics = new ArrayList<StatEntry>();
    }

    public List<StatEntry> getStatistics() {
        return statistics;
    }

    public void processRoundResult(RoundResult roundResult) {
        // TODO: process round result
    }
}
