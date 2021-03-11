package com.epam.task06.data;

import com.epam.task06.entities.Buyer;
import com.epam.task06.entities.Lot;
import com.epam.task06.wrapper.Buyers;
import com.epam.task06.wrapper.Lots;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    private ObjectMapper objectMapper;

    public JsonReader() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Lot> readLots(String filename) throws DataException {
        File file = new File(filename);
        Lots lotsWrapper = null;
        List<Lot> lots = null;
        try {
            lotsWrapper = objectMapper.readValue(file, Lots.class);
            lots = lotsWrapper.getLots();
            lots.forEach(lot -> System.out.println(lot));
        } catch (IOException exception) {
            throw new DataException(exception.getMessage(), exception);
        }
        return lots;
    }

    public List<Buyer> readBuyers(String filename) throws DataException {
        File file = new File(filename);
        Buyers buyersWrapper = null;
        List<Buyer> buyers = null;
        try {
            buyersWrapper = objectMapper.readValue(file, Buyers.class);
            buyers = buyersWrapper.getBuyers();
            buyers.forEach(buyer -> System.out.println(buyer));
        } catch (IOException exception) {
            throw new DataException(exception.getMessage(), exception);
        }
        return buyers;
    }
}
