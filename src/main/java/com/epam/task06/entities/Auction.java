package com.epam.task06.entities;

import com.epam.task06.data.DataException;
import com.epam.task06.data.JsonReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auction {
    private static final AtomicReference<Auction> INSTANCE = new AtomicReference<>();
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final JsonReader READER = new JsonReader();
    private static final String BUYERS_FILE = "./src/main/resources/buyers.json";
    private static final Logger LOGGER = LogManager.getLogger(Auction.class);

    private List<Buyer> buyers;

    private Auction() {

    }

    public static Auction getInstance() {
        if (INSTANCE.get() == null) {
            try {
                INSTANCE_LOCK.lock();
                if (INSTANCE.get() == null) {
                    Auction auction = new Auction();
                    auction.readBuyers();
                    INSTANCE.getAndSet(auction);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return INSTANCE.get();
    }

    private void readBuyers() {
        try {
            this.buyers = READER.readBuyers(BUYERS_FILE);
        } catch (DataException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }
    }

    public void method() {
        System.out.println("auction works!!!");
        System.out.println(buyers.get(0));
    }
}
