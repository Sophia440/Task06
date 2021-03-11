package com.epam.task06.entities;

import com.epam.task06.data.DataException;
import com.epam.task06.data.JsonReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auction {
    private static final String LOTS_FILE = "./src/main/resources/lots.json";

    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final Lock PROCESS_LOCK = new ReentrantLock();
    private static final AtomicReference<Auction> INSTANCE = new AtomicReference<>();

    private List<Lot> lots;
    private static final int LOTS_NUMBER = 3;

    private final Semaphore semaphore = new Semaphore(LOTS_NUMBER, true);

    private Auction() {
        JsonReader reader = new JsonReader();
        try {
            this.lots = reader.readLots(LOTS_FILE);
            lots.forEach(lot -> {
                BigDecimal startingPrice = lot.getStartingPrice();
                lot.setCurrentPrice(startingPrice);
            });
        } catch (DataException e) {
            e.printStackTrace();
            //logger
        }
    }

    public static Auction getInstance() {
        if (INSTANCE.get() == null) {
            try {
                INSTANCE_LOCK.lock();
                if (INSTANCE.get() == null) {
                    Auction auction = new Auction();
                    INSTANCE.getAndSet(auction);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return INSTANCE.get();
    }

    public void process(Buyer buyer) {
        PROCESS_LOCK.lock();
        try {
            semaphore.acquire();
            System.out.println("Buyer #" + buyer.getId() + " is being processed in Auction");
            lots.forEach(lot -> lot.process(buyer));
        } catch (InterruptedException e) {
            e.printStackTrace();
            //logger
        } finally {
            semaphore.release();
            PROCESS_LOCK.unlock();
        }
    }
}
