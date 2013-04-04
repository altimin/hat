package ru.altimin.hat.game;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:18
 */
public class Round {
    public String getNextWord();

    public void reportAnswered();
    public void reportNotAnswered();
    public void reportFail();
    public void revokeReport();

    public boolean hasEnded();

    public RoundResult getRoundResult();
}
