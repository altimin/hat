package ru.altimin.hat.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class RoundResult implements Serializable {
    private final List<ExplanationResult> stats = new ArrayList<ExplanationResult>();

    private Player playerFrom;
    private Player playerTo;

    public RoundResult(Player playerFrom, Player playerTo) {
        this.playerFrom = playerFrom;
        this.playerTo = playerTo;
    }


    public void addExplanationResult(ExplanationResult statEntry) {
        stats.add(statEntry);
    }

    public void removeStatEntry(ExplanationResult statEntry) {
        stats.remove(stats.size() - 1);
    }

    public List<ExplanationResult> getStats() {
        return stats;
    }

    public Player getPlayerTo() {
        return playerTo;
    }

    public Player getPlayerFrom() {
        return playerFrom;
    }

    public int getPlayerFromId() {
        return playerFrom.getId();
    }

    public int getPlayerToId() {
        return playerTo.getId();
    }
}
