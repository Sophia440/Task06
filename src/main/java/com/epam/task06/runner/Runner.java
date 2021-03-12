package com.epam.task06.runner;

import com.epam.task06.data.DataException;
import com.epam.task06.data.JsonReader;
import com.epam.task06.entities.Lot;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger(Runner.class);
    private static final String LOTS_FILE = "./src/main/resources/lots.json";

    public static void main(String[] args) {
        JsonReader reader = new JsonReader();
        List<Lot> lots = null;
        try {
            lots = reader.readLots(LOTS_FILE);
        } catch (DataException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }

        ExecutorService pool = Executors.newSingleThreadExecutor();
        lots.forEach(lot -> pool.submit(lot));
        pool.shutdown();
    }
}
