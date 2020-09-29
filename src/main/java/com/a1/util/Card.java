package com.a1.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

    private String name;

    /**
     * whether this card has already been used by a player, only for Sorceress Card
     * default: false (unused)
     */
    private boolean used;

    /**
     * only for treasure card
     */
    private List<Integer> treasureChest;

    public Card(String name) {
        this.name = name;
        this.used = false;
        if (this.name.equals(Const.CARD_TREASURE_CHEST)) {
            this.treasureChest = new ArrayList<Integer>();
        } else {
            this.treasureChest = null;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<Integer> getTreasureChest() {
        return treasureChest;
    }

    public void setTreasureChest(List<Integer> treasureChest) {
        this.treasureChest = treasureChest;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", used=" + used +
                ", treasureChest=" + treasureChest +
                '}';
    }
}

