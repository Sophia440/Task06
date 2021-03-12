package com.epam.task06.entities;

import java.math.BigDecimal;

public class Buyer implements Runnable {
    private int id;
    private String name;
    private BigDecimal startingBudget;
    private BigDecimal currentBudget;
    private BigDecimal minimalBudget;

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

    public void setMinimalBudget(BigDecimal minimalBudget) {
        this.minimalBudget = minimalBudget;
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

    @Override
    public String toString() {
        return "Buyer{" + "id=" + id + ", name='" + name + '}';
    }

    @Override
    public void run() {
        Auction auction = Auction.getInstance();
        auction.makeDecision(this);
    }

//    public boolean makeDecision() {
//
//    }
}
