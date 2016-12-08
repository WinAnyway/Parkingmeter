package pl.com.bottega.parkingmeter.model;

import java.util.HashMap;
import java.util.Map;

public class PriceList {
    Map<Time, Money> priceList = new HashMap<>();
    private Money costPerMinute = new Money("0.1", "PLN");

    public PriceList(){}

    public PriceList(Map<Time, Money> priceList){
        this.priceList = priceList;
    }

    public Money giveMeTheCost(Time parkingTime){
        return costPerMinute.multiply(parkingTime.getInMinutes());

    }
}
