package com.assignment.bookingService.service;

import com.assignment.bookingService.dto.PaymentDTO;
import com.assignment.bookingService.entity.BookingInfoEntity;

public interface BookingService {
    BookingInfoEntity saveBookingDetails(BookingInfoEntity booking);
    boolean isValidPaymentService(String paymentMode);
    boolean isValidBooking(int bookingID);
    BookingInfoEntity updateTransactionID(PaymentDTO paymentDTO);
}
