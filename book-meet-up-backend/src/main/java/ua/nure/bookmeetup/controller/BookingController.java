package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.bookmeetup.dto.booking.BookingRequestDto;
import ua.nure.bookmeetup.service.BookingService;

import javax.validation.Valid;

import static ua.nure.bookmeetup.validation.BindingResultValidator.errorBody;

@CrossOrigin
@RestController
@RequestMapping("/bookings")
@Api(tags = "6. Booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds booking by id", nickname = "getBookingById")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @GetMapping("/employee/{id}")
    @ApiOperation(value = "Finds all bookings for provided employee", nickname = "getAllBookingsByEmployee")
    public ResponseEntity<?> getAllBookingsByEmployee(@PathVariable Long id,
                                                      @RequestParam(defaultValue = "true") Boolean isUpcoming) {
        return ResponseEntity.ok(bookingService.getAllBookingsByEmployee(id, isUpcoming));
    }

    @GetMapping("/meeting-room/{id}")
    @ApiOperation(value = "Finds all bookings for provided meeting room", nickname = "getAllBookingsByMeetingRoom")
    public ResponseEntity<?> getAllBookingsByMeetingRoom(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "true") Boolean isUpcoming) {
        return ResponseEntity.ok(bookingService.getAllBookingsByMeetingRoom(id, isUpcoming));
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "Finds booking info by id", nickname = "getBookingInfoById")
    public ResponseEntity<?> getBookingInfoById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingInfoById(id));
    }

    @PostMapping
    @ApiOperation(value = "Creates new single booking", nickname = "createSingleBooking")
    public ResponseEntity<?> createSingleBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(bookingService.createSingle(bookingRequestDto));
    }

    @PutMapping
    @ApiOperation(value = "Updates booking (Booking ID must be present)", nickname = "updateBooking")
    public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(bookingService.update(bookingRequestDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes booking by ID", nickname = "deleteBooking")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.findById(id);
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    @ApiOperation(value = "Cancels provided booking", nickname = "cancelBooking")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
