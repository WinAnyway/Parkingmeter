package pl.com.bottega.parkingmeter.model.devices;

import pl.com.bottega.parkingmeter.model.Money;

import java.time.LocalDateTime;

public interface UserTicketStrategy {
    void giveTicket(LocalDateTime maxDateTime, Money price);
}
