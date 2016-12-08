package pl.com.bottega.parkingmeter.model;

import java.util.*;

public class Cartridge {

    Map<Money, Integer> moneyMap = new HashMap<>();
    Money totalMoney = Money.ZERO;

    public Cartridge(Map<Money, Integer> cartridge){
        this.moneyMap = cartridge;
    }

    public void checkValue(Money money) {
        if(!moneyMap.containsKey(money))
            throw new IllegalArgumentException("I'm sorry, I can't accept this coin. Valid coins: " + moneyMap.keySet().toString());
    }

    public void getMoney(Money money){
        checkValue(money);
        moneyMap.put(money, moneyMap.get(money) + 1);
        calculateTotalMoney();
    }

    public Money giveMoney(Money money) throws NullPointerException{
        checkValue(money);
        if(!isInCartridge(money))
            return null;

        Money given = money;
        moneyMap.put(money, moneyMap.get(money) - 1);
        return given;
    }

    public boolean isInCartridge(Money money){
        return moneyMap.get(money) > 0;
    }

    private void calculateTotalMoney(){
        for(Money money : moneyMap.keySet()){
             totalMoney = totalMoney.add(money.multiply(moneyMap.get(money)));
        }
    }

    /*private boolean hasChange(Money totalCost){
        List<Money> moneys = new ArrayList(moneyMap.keySet());
        Money change = Money.ZERO;
        while (!totalCost.equals(change)){
            if(moneyMap.get(moneys.get(0)) > 0 && moneys.get(0).lessEquals(totalCost.substract(change))){
                System.out.println(moneys.get(0).toString());
                change.add(moneys.get(0));
                moneyMap.put(moneys.get(0), moneyMap.get(moneys.get(0)) - 1);
            }
        }
        return false;
    }*/
}
