package pl.com.bottega.parkingmeter.model.devices;

import pl.com.bottega.parkingmeter.model.Money;
import pl.com.bottega.parkingmeter.model.Time;

import java.time.LocalDateTime;

public interface UserOutputInterface {
    void display(Time desiredTime, LocalDateTime dueDateTime, Money cost);
}
