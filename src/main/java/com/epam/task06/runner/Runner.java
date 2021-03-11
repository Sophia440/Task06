package com.epam.task06.runner;

import com.epam.task06.data.DataException;
import com.epam.task06.data.JsonReader;
import com.epam.task06.entities.Buyer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    private static final String BUYERS_FILE = "./src/main/resources/buyers.json";

    public static void main(String[] args) throws DataException {
        JsonReader reader = new JsonReader();
        List<Buyer> buyers = reader.readBuyers(BUYERS_FILE);

        ExecutorService executor = Executors.newFixedThreadPool(buyers.size());
        buyers.forEach(buyer -> executor.submit(buyer));
        executor.shutdown();
    }
}
