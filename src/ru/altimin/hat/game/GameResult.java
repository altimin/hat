package ru.altimin.hat.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:19
 */
public class GameResult implements Serializable {
    @SerializedName("gameId")
    private int id;
    private List<StatEntry> statistics;

    public GameResult(int id, List<StatEntry> statistics) {
        this.id = id;
        this.statistics = statistics;
    }

    public int getId() {
        return id;
    }

    public List<StatEntry> getStatistics() {
        return statistics;
    }
}
