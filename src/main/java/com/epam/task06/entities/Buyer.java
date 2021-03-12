package com.epam.task06.entities;

import java.math.BigDecimal;

public class Buyer implements Runnable {
    private int id;
    private String name;
    private BigDecimal startingBudget;
    private BigDecimal currentBudget;
    private BigDecimal activeBudget;

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

    public void setCurrentBudget(BigDecimal currentBudget) {
        this.currentBudget = currentBudget;
    }

    public void setActiveBudget(BigDecimal activeBudget) {
        this.activeBudget = activeBudget;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getStartingBudget() {
        return startingBudget;
    }

    public BigDecimal getCurrentBudget() {
        return currentBudget;
    }

    public BigDecimal getActiveBudget() {
        return activeBudget;
    }

    @Override
    public String toString() {
        return "Buyer{" + "id=" + id + ", name='" + name + '}';
    }

    @Override
    public void run() {
        Auction auction = Auction.getInstance();
        boolean canAfford = auction.makeDecision(this);
    }
}
