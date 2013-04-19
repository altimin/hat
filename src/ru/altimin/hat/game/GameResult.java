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
    // TODO: do more elegant serialization

    @SerializedName("gameId")
    private int id;

    private List<StatEntry> statistics;

    @Expose(serialize = false)
    private GameSettings gameSettings;

    public GameResult(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.id = gameSettings.getId();
        this.statistics = new ArrayList<StatEntry>();
    }

    public List<StatEntry> getStatistics() {
        return statistics;
    }

    public void processRoundResult(RoundResult roundResult) {
        // TODO: process round result
    }
}
