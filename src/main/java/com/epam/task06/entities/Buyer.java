package com.epam.task06.entities;

import java.math.BigDecimal;

public class Buyer implements Runnable {
    private int id;
    private String name;
    private BigDecimal startingBudget;
    private BigDecimal activeBudget;
    private boolean wantsToBid;

    public Buyer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingBudget(BigDecimal startingBudget) {
        this.startingBudget = startingBudget;
    }

    public void setActiveBudget(BigDecimal activeBudget) {
        this.activeBudget = activeBudget;
    }

    public void setWantsToBid(boolean wantsToBid) {
        this.wantsToBid = wantsToBid;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getStartingBudget() {
        return startingBudget;
    }

    public BigDecimal getActiveBudget() {
        return activeBudget;
    }

    public boolean isWantsToBid() {
        return wantsToBid;
    }

    @Override
    public String toString() {
        return "Buyer #" + id + ", " + name + ", budget: " + startingBudget + ", active: " + activeBudget;
    }

    @Override
    public void run() {
        Auction auction = Auction.getInstance();
        auction.makeDecision(this);
    }
}
