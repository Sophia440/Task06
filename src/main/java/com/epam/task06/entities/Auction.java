package com.epam.task06.entities;

import com.epam.task06.data.DataException;
import com.epam.task06.data.JsonReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Auction {
    private static final Logger LOGGER = LogManager.getLogger(Auction.class);

    private static final AtomicReference<Auction> INSTANCE = new AtomicReference<>();
    private static final Lock INSTANCE_LOCK = new ReentrantLock();

    private static final JsonReader READER = new JsonReader();
    private static final String BUYERS_FILE = "./src/main/resources/buyers.json";
    private static final BigDecimal PERCENTAGE_FOR_MINIMAL_BUDGET = BigDecimal.valueOf(0.2);

    private List<Buyer> buyers;
    private Lot currentLot;

    private Auction() {

    }

    public static Auction getInstance() {
        if (INSTANCE.get() == null) {
            try {
                INSTANCE_LOCK.lock();
                if (INSTANCE.get() == null) {
                    Auction auction = new Auction();
                    auction.readBuyers();
                    auction.buyers.forEach(buyer -> {
                        BigDecimal startingBudget = buyer.getStartingBudget();
                        buyer.setCurrentBudget(startingBudget);
                        buyer.setMinimalBudget(startingBudget.multiply(PERCENTAGE_FOR_MINIMAL_BUDGET));
                    });
                    INSTANCE.getAndSet(auction);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return INSTANCE.get();
    }

    public void setCurrentLot(Lot currentLot) {
        this.currentLot = currentLot;
    }

    private void readBuyers() {
        try {
            this.buyers = READER.readBuyers(BUYERS_FILE);
        } catch (DataException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }
    }

    public void startBiddingProcess() {
        ExecutorService executor = Executors.newFixedThreadPool(buyers.size());

        List<Future<?>> futures = buyers.stream()
                .map(executor::submit)
                .collect(Collectors.toList());
        executor.shutdown();

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException exception) {
                LOGGER.warn(exception.getMessage(), exception);
            }
        });
    }

    public void makeDecision(Buyer buyer) {
        System.out.println("Buyer #" + buyer.getId());
        BigDecimal currentBudget = buyer.getCurrentBudget();
        BigDecimal currentLotPrice = currentLot.getCurrentPrice();
        System.out.println("Current budget = " + currentBudget);
        System.out.println("Current lot price = " + currentLotPrice);
        int comparisonResult = currentBudget.compareTo(currentLotPrice);
        System.out.println("Can afford: " + canAfford(comparisonResult));
    }

    public boolean canAfford(int comparisonResult) {
        if (comparisonResult > 0) {
            return true;
        }
        return false;
    }

}
