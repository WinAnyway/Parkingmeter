package pl.com.bottega.parkingmeter.application;

import pl.com.bottega.parkingmeter.model.*;
import pl.com.bottega.parkingmeter.model.devices.Printer;
import pl.com.bottega.parkingmeter.model.devices.UserOutputInterface;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TestApplication {
    public static void main(String[] args) {
        Map<Money, Integer> cartridge = new HashMap<>();
        cartridge.put(new Money("1", "PLN"), 10);
        cartridge.put(new Money("2", "PLN"), 10);
        cartridge.put(new Money("5", "PLN"), 10);
        cartridge.put(new Money("10", "PLN"), 10);
        cartridge.put(new Money("20", "PLN"), 10);
        cartridge.put(new Money("50", "PLN"), 10);

        Parkingmeter parkingmeter = new Parkingmeter(new UserOutputInterface() {
            @Override
            public void display(Time desiredTime, LocalDateTime dueDateTime, Money cost) {
                System.out.println("desiredTime = [" + desiredTime + "], dueDateTime = [" + dueDateTime + "], cost = [" + cost + "]");
            }
        }, new Cartridge(cartridge), new Printer(), new Bufor(cartridge.keySet()));

        //symulacja zachowania usera 1
        parkingmeter.add(new Time(2, Time.TimeUnit.MIN));
        parkingmeter.add(new Time(3, Time.TimeUnit.HOUR));

        parkingmeter.confirm();//brak środków

        parkingmeter.topUp(new Money(2, "PLN"));
        parkingmeter.topUp(new Money(50, "PLN"));

        //jeszcze raz
        parkingmeter.confirm();

        //symulacja zachowania usera 2
        parkingmeter.add(new Time(2, Time.TimeUnit.MIN));
        parkingmeter.topUp(new Money(50, "PLN"));

        //rozmyslił się
        parkingmeter.cancel();
    }
}
