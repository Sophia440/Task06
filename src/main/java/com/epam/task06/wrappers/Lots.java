package com.epam.task06.wrappers;

import com.epam.task06.entities.Lot;

import java.util.List;

public class Lots {
    private List<Lot> lots;

    public Lots() {
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }
}
