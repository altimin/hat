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
    private final List<StatEntry> stats = new ArrayList<StatEntry>();

    public void addStatEntry(StatEntry statEntry) {
        stats.add(statEntry);
    }

    public void removeStatEntry(StatEntry statEntry) {
        stats.remove(stats.size() - 1);
    }

    public List<StatEntry> getStats() {
        return stats;
    }
}
