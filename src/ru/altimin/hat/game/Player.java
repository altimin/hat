package ru.altimin.hat.game;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: altimin
 * Date: 04/04/13
 * Time: 21:19
 */
public class Player implements Serializable {
    @SerializedName("userName")
    private String name;

    @SerializedName("userId")
    private int id;

//    public Player(String name, int id) {
//        this.name = name;
//        this.id = id;
//    }

    public Player(String name) {
        this.name = name;
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

