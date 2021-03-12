package com.epam.task06.entities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Lot implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Lot.class);
    private static final BigDecimal PERCENTAGE_FOR_BIDDING = BigDecimal.valueOf(0.2);
    private static final int TRADING_TIME = 2000;
    private int id;
    private String name;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal bid;
    private int currentBuyerId;

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

    public void setCurrentBuyerId(int currentBuyerId) {
        this.currentBuyerId = currentBuyerId;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public int getCurrentBuyerId() {
        return currentBuyerId;
    }

    public void raisePrice() {
        this.currentPrice.add(this.bid);
    }

    @Override
    public String toString() {
        return "Lot #" + id + ", '" + name + '\'' + ", starting price: " + startingPrice;
    }

    @Override
    public void run() {
        this.currentPrice = startingPrice;
        this.bid = startingPrice.multiply(PERCENTAGE_FOR_BIDDING);
        System.out.println(this + "\nBid amount: " + this.bid);
        Auction auction = Auction.getInstance();
        auction.setCurrentLot(this);
        auction.startBiddingProcess();
        try {
            TimeUnit.MILLISECONDS.sleep(TRADING_TIME);
        } catch (InterruptedException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }
        System.out.println(name + " goes to buyer #" + currentBuyerId + " with the final price of " + currentPrice + "\n");
    }
}
