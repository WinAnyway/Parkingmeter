package pl.com.bottega.parkingmeter.model;

import pl.com.bottega.parkingmeter.model.devices.UserOutputInterface;
import pl.com.bottega.parkingmeter.model.devices.UserTicketStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Parkingmeter {
    //urządzenia współpracujące
    private UserOutputInterface userOutputInterface;
    private UserTicketStrategy userTicketStrategy;

    //stan sesji klienta
    private LocalDateTime maxDateTime = LocalDateTime.now();
    private Time userTime = Time.ZERO;
    private Money totalCost = Money.ZERO;
    private Money balance = Money.ZERO;
    private Cartridge cartridge;
    private Bufor bufor;
    private PriceList priceList = new PriceList();

    public Parkingmeter(UserOutputInterface userOutputInterface, Cartridge cartridge, UserTicketStrategy userTicketStrategy, Bufor bufor) {
        resetUserData();
        this.userOutputInterface = userOutputInterface;
        this.cartridge = cartridge;
        this.userTicketStrategy = userTicketStrategy;
        this.bufor = bufor;
    }

    public void add(Time time) {
        userTime = userTime.add(time);

        totalCost = recalculateTotalCost();
        maxDateTime = recalculateMaxDateTime();

        userOutputInterface.display(userTime, maxDateTime, totalCost);
    }

    public void topUp(Money money) {
        //sprawdzic czy wartosc jest do przyjęcia
        cartridge.checkValue(money);
        //zwiększyć ilość wpłaconych pieniędzy
        balance = balance.add(money);
        //pieniądze przekazać do "bufora" aby móc je oddać w razie cancel lub darmowego parkowania gdyby nie było jak wydać reszty
        bufor.holdMoney(money);
    }

    public void confirm() {
        //jeżeli ilosc wpłaconyc pieniedzy wystarczy na opłacenie totalCost - do Money dodać metody greaterThan(Money) greaterEquals(Money) i lessThan LessEquals aby móc porównać 2 wartosci money
        if(totalCost.greaterThan(balance)) {
            System.out.println("You have to give me more money. Your balance: " + balance.toString() + ". Total cost " + totalCost.toString());
            return;
        }
        bufor.passMoneyToCartridge(cartridge);
        //jeżeli należy się reszta do wydania
        if(totalCost.lessThan(balance)){
            //TODO
            //jeżeli są środki aby wydać restę to wydać jak nie to oddaćPieniądze()
            if(cartridge.totalMoney.greaterEquals(totalCost)){
                giveChange();
            }else{
                bufor.giveMoneyBack();
            }
        }

        //wydrukować bilet
        userTicketStrategy.giveTicket(maxDateTime, totalCost);
        //wydać resztę

        //ResetStanuMaszyny() - warto zrobić metodę prywatnę, która resetuje i ją wywołać w konstruktorze również
        resetUserData();
    }

    public void cancel() {
        //oddaćPieniądze()
        System.out.println("Here is your money: " + bufor.giveMoneyBack().toString());
        //ResetStanuMaszyny()
        resetUserData();
    }

    private void resetUserData(){
        maxDateTime = LocalDateTime.now();
        userTime = Time.ZERO;
        totalCost = Money.ZERO;
        balance = Money.ZERO;
    }

    private LocalDateTime recalculateMaxDateTime() {
        return LocalDateTime.now().plusMinutes(userTime.getInMinutes());
    }

    private Money recalculateTotalCost() {
        //TODO przeliczyć na podstawie stawek cennika
        return priceList.giveMeTheCost(userTime);
        //trzeba spawdzic ile mamy minut w userTime.getInMinutes() aby wiedzieć ile trzeba zapłacić za stawkę za określony czas
        //stawki mogą w cenniku być różne w zależności jak długo parkujemy...
        //gdyby ktoś miał problem z tym algorytmem to niech założy, że stawka jest stała - jedna pozycja w cenniku

    }

    private ArrayList<Money> giveChange(){
        Money amountToGive = balance.substract(totalCost);
        ArrayList<Money> change = new ArrayList<>();

        /*while (amountToGive.greaterThan(Money.ZERO)){
            if(cartridge)
        }*/

        return change;
    }
}
