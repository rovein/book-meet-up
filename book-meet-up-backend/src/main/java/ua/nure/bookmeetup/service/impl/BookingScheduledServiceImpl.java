package ua.nure.bookmeetup.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.service.BookingScheduledService;
import ua.nure.bookmeetup.service.BookingService;

@Service
@Log4j2
public class BookingScheduledServiceImpl implements BookingScheduledService {

    private final BookingService bookingService;

    @Autowired
    public BookingScheduledServiceImpl(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Async
    @Scheduled(cron = "* * * * *")
    public void checkAndUpdateBookingStatuses() {
        log.info("Starting scheduled job to check and change booking statuses...");
        bookingService.checkAndUpdateBookingStatuses();
        log.info("Scheduled job to check and change booking statuses is finished");
    }

}
