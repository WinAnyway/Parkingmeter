package pl.com.bottega.parkingmeter.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Bufor {
    private Set<Money> validMoneys = new HashSet<>();
    private ArrayList<Money> moneys = new ArrayList<>();

    public Bufor(Set<Money> validMoneys){
        this.validMoneys = validMoneys;
    }

    public void checkValue(Money money) {
        if(!validMoneys.contains(money))
            throw new IllegalArgumentException("I'm sorry, I can't accept this coin. Valid coins: " + validMoneys.toString());
    }

    public void holdMoney(Money money) {
        checkValue(money);
        moneys.add(money);
    }

    public void passMoneyToCartridge(Cartridge cartridge){
        if(moneys.isEmpty())
            throw new IllegalStateException("I have nothing to pass");

        for(Money money : moneys){
            cartridge.getMoney(money);
        }
        moneys.clear();
    }

    public ArrayList<Money> giveMoneyBack(){
        ArrayList<Money> change = new ArrayList<>(moneys);
        moneys.clear();
        return change;
    }
}
