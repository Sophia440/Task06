package com.epam.task06.entities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Lot implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Lot.class);
    private static final BigDecimal PERCENTAGE_FOR_BIDDING = BigDecimal.valueOf(0.2);
    private static final int TRADING_TIME = 3000;
    private int id;
    private String name;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal bid;

    public Lot() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingPrice=" + startingPrice +
                ", currentPrice=" + currentPrice +
                ", bid=" + bid +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Lot #" + this.id);
        this.currentPrice = startingPrice;
        this.bid = startingPrice.multiply(PERCENTAGE_FOR_BIDDING);
        Auction auction = Auction.getInstance();
        auction.setCurrentLot(this);
        auction.startBiddingProcess();
        try {
            TimeUnit.MILLISECONDS.sleep(TRADING_TIME);
        } catch (InterruptedException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }
    }
}
