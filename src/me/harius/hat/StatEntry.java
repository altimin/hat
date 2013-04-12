package me.harius.hat;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 10:46 AM
 */
public class StatEntry {

    public int wordId;
    public int userFrom;
    public int userTo;
    // TODO: Result, Timestamps, etc.

    public StatEntry(int wordId, int userFrom, int userTo) {
        this.wordId = wordId;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
}
