package com.epam.task06.entities;

import java.math.BigDecimal;

public class Buyer implements Runnable {
    private int id;
    private String name;
    private BigDecimal startingBudget;
    private BigDecimal currentBudget;
    private BigDecimal minimalBudget;
    private static final BigDecimal PERCENTAGE_FOR_MINIMAL_BUDGET = BigDecimal.valueOf(0.2);

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

    @Override
    public String toString() {
        return "Buyer{" + "id=" + id + ", name='" + name + '}';
    }

    @Override
    public void run() {
        this.minimalBudget = startingBudget.multiply(PERCENTAGE_FOR_MINIMAL_BUDGET);
        this.currentBudget = startingBudget;
        System.out.println(this.id + " min = " + this.minimalBudget);
        Auction auction = Auction.getInstance();
        auction.method();
    }
}
