package com.assignment.bookingService.controller;

import com.assignment.bookingService.dto.BookingDTO;
import com.assignment.bookingService.dto.BookingDetailsDTO;
import com.assignment.bookingService.dto.PaymentDTO;
import com.assignment.bookingService.entity.BookingInfoEntity;
import com.assignment.bookingService.exceptionHandler.BookingNotFoundException;
import com.assignment.bookingService.exceptionHandler.PaymentException;
import com.assignment.bookingService.mapper.CustomMapper;
import com.assignment.bookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * rest controller - handles incoming request to the application
 */

@RestController
@RequestMapping(value = "/hotel")
public class bookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * handles booking from user
     *
     * @return booking details with Price & Room numbers
     */
    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDetailsDTO> processBooking(@RequestBody BookingDTO bookingDTO) {

        BookingInfoEntity booking = CustomMapper.convertDTOToEntity(bookingDTO);
        BookingInfoEntity savedBooking = bookingService.saveBookingDetails(booking);

        BookingDetailsDTO savedBookingDto = CustomMapper.convertEntityToDTO(savedBooking);
        return new ResponseEntity<>(savedBookingDto, HttpStatus.CREATED);

    }

    /**
     * handles payment from user
     *
     * @return booking details with transaction id for successful payment
     * @throws PaymentException         if payment mode is invalid
     * @throws BookingNotFoundException if booking id is not found in DB
     */
    @PostMapping(value = "/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> processPayment(@PathVariable(name = "bookingId") int bookingId, @RequestBody PaymentDTO paymentDTO) {

        if (!bookingService.isValidPaymentService(paymentDTO.getPaymentMode())) {
            throw new PaymentException("Invalid mode of payment");
        }

        if (!bookingService.isValidBooking(bookingId)) {
            throw new BookingNotFoundException("Invalid Booking Id");
        }

        if (bookingId != paymentDTO.getBookingId())
            throw new BookingNotFoundException("Invalid Booking Id, bookingId mismatch between path variable and body");

        BookingInfoEntity updatedBookingDetails = bookingService.updateTransactionID(paymentDTO);
        if (updatedBookingDetails == null) {
            return new ResponseEntity<>("Error during payment", HttpStatus.SERVICE_UNAVAILABLE);
        }

        BookingDetailsDTO updatedBookingDto = CustomMapper.convertEntityToDTO(updatedBookingDetails);
        String message = "Booking confirmed for user with aadhaar number: "
                + updatedBookingDetails.getAadharNumber()
                + "    |    "
                + "Here are the booking details:    " + updatedBookingDetails;
        System.out.println(message);
        return new ResponseEntity<>(updatedBookingDto, HttpStatus.CREATED);
    }

}
