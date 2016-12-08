package pl.com.bottega.parkingmeter.model.devices;

import pl.com.bottega.parkingmeter.model.Money;

import java.time.LocalDateTime;

public class Printer implements UserTicketStrategy {
    @Override
    public void giveTicket(LocalDateTime maxDateTime, Money price) {
        System.out.println("maxDateTime = [" + maxDateTime + "], price = [" + price + "]");
    }
}
