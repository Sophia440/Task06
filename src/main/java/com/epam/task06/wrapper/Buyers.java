package com.epam.task06.wrapper;

import com.epam.task06.entities.Buyer;

import java.util.List;

public class Buyers {
    private List<Buyer> buyers;

    public Buyers() {
    }

    public Buyers(List<Buyer> buyers) {
        this.buyers = buyers;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }
}
