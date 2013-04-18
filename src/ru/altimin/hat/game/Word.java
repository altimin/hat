package ru.altimin.hat.game;

import java.io.Serializable;

/**
 * User: harius
 * Date: 4/5/13
 * Time: 1:10 AM
 */
public class Word implements Serializable {
    private String word;
    private int id;

    public String getWord() {
        return word;
    }

    public int getId() {
        return id;
    }
}
