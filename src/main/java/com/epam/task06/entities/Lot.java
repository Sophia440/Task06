package com.epam.task06.entities;

import com.epam.task06.runner.Runner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;

public class Lot implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Lot.class);
    private int id;
    private String name;
    private BigDecimal startingPrice;
//    private BigDecimal currentPrice;
//    private BigDecimal bid;

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

    @Override
    public void run() {
        System.out.println("lot #" + this.id);
        try {
            sleep(2000);
        } catch (InterruptedException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }
        Auction auction = Auction.getInstance();
        auction.method();
    }
}
