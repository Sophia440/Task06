package com.epam.task06.entities;

import java.math.BigDecimal;

public class Lot {
    private int id;
    private String name;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal bid;
    private static final BigDecimal PERCENTAGE_FOR_BIDDING = BigDecimal.valueOf(0.2);

    public Lot() {

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

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingPrice=" + startingPrice +
                '}';
    }

    public void process(Buyer buyer) {
        System.out.println("Buyer #" + buyer.getId() + " is being processed in Lot #" + this.id);
        this.bid = startingPrice.multiply(PERCENTAGE_FOR_BIDDING);
//        if (buyer.isBidding(this.currentPrice)) {
//            this.currentPrice.add(this.bid);
//        }
    }
}
