package ru.altimin.hat.game;

import java.io.Serializable;
import java.util.List;

/**
 * User: altimin
 * Date: 19/05/13
 * Time: 18:01
 */
public class PlayersOrder implements Serializable {
    public List<Player> playersOrder;

    public PlayersOrder(List<Player> playersOrder) {
        this.playersOrder = playersOrder;
    }
}
