package ru.altimin.hat.game;

import java.io.Serializable;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:19
 */
public class Player implements Serializable {
    private final String name;
    private final String id;

    public Player(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Player(String name) {
        this.name = name;
        this.id = null;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

