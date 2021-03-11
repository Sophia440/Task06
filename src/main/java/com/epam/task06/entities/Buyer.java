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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(BigDecimal currentBudget) {
        this.currentBudget = currentBudget;
    }

    public BigDecimal getStartingBudget() {
        return startingBudget;
    }

    public void setStartingBudget(BigDecimal startingBudget) {
        this.startingBudget = startingBudget;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingBudget=" + startingBudget +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Buyer #" + this.id + " called run");
        this.minimalBudget = startingBudget.multiply(PERCENTAGE_FOR_MINIMAL_BUDGET);
        Auction auction = Auction.getInstance();
        auction.process(this);
    }


    public boolean isBidding(BigDecimal currentPrice) {
        BigDecimal budgetIfBuys = this.currentBudget.subtract(currentPrice);
        int comparisonResult = budgetIfBuys.compareTo(minimalBudget);
        if (comparisonResult >= 0) {
            this.currentBudget = budgetIfBuys;
            return true;
        } else {
            return false;
        }
    }
}
