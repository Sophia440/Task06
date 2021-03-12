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
    private static final Lock DECISION_LOCK = new ReentrantLock();

    private static final JsonReader READER = new JsonReader();
    private static final String BUYERS_FILE = "./src/main/resources/buyers.json";
    private static final BigDecimal PERCENTAGE_FOR_ACTIVE_BUDGET = BigDecimal.valueOf(0.8);

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
                        buyer.setActiveBudget(startingBudget.multiply(PERCENTAGE_FOR_ACTIVE_BUDGET));
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

    public boolean makeDecision(Buyer buyer) {
        DECISION_LOCK.lock();
        BigDecimal currentLotPrice = currentLot.getCurrentPrice();
        BigDecimal bid = currentLot.getBid();
        BigDecimal newLotPrice = currentLotPrice.add(bid);
        BigDecimal activeBudget = buyer.getActiveBudget();
        System.out.println("Buyer #" + buyer.getId() + " has " + activeBudget + " active money");
        System.out.println("Current price of the lot: " + currentLotPrice);
        System.out.println("Raised price: " + newLotPrice);
        int comparisonResult = activeBudget.compareTo(newLotPrice);
        if (canAfford(comparisonResult)) {
            System.out.println("Can afford, changing buyers");
            BigDecimal newActiveBudget = activeBudget.subtract(newLotPrice);
            changeCurrentBuyer(buyer, newActiveBudget, currentLotPrice, newLotPrice);
            currentLot.raisePrice();
        } else {
            System.out.println("Can't afford :(");
        }
        System.out.println();
        DECISION_LOCK.unlock();
        return canAfford(comparisonResult);
    }

    private void changeCurrentBuyer(Buyer buyer, BigDecimal newActiveBudget, BigDecimal currentLotPrice,
                                    BigDecimal newLotPrice) {
        int oldBuyerId = currentLot.getCurrentBuyerId();
        if (oldBuyerId != 0) {
            System.out.println("Buyer #" + oldBuyerId + " wanted to buy");
            buyers.forEach(buyerInList -> {
                if (buyerInList.getId() == oldBuyerId) {
                    BigDecimal oldBuyerActiveBudget = buyerInList.getActiveBudget();
                    buyerInList.setActiveBudget(oldBuyerActiveBudget.add(currentLotPrice));
                }
            });
        }
        int newBuyerId = buyer.getId();
        currentLot.setCurrentBuyerId(newBuyerId);
        currentLot.setCurrentPrice(newLotPrice);
        buyer.setActiveBudget(newActiveBudget);
        System.out.println("Changing buyers complete! Buyer #" + newBuyerId + " is the new buyer");
    }

    private boolean canAfford(int comparisonResult) {
        if (comparisonResult >= 0) {
            return true;
        }
        return false;
    }

}
