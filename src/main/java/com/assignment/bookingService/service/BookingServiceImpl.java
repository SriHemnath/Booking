package com.assignment.bookingService.service;

import com.assignment.bookingService.dto.PaymentDTO;
import com.assignment.bookingService.entity.BookingInfoEntity;
import com.assignment.bookingService.repository.BookingDao;
import com.assignment.bookingService.utils.BookingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingDao bookingDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${paymentService.url}")
    private String paymentURL;

    @Override
    public BookingInfoEntity saveBookingDetails(BookingInfoEntity booking) {
        booking.setBookedOn(new java.util.Date());
        booking.setRoomNumbers(BookingUtils.getRandomNumbers(booking.getNumOfRooms()).toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        booking.setRoomPrice(BookingUtils.calculateRoomPrice(booking.getNumOfRooms(), BookingUtils.calculateDiff(booking.getToDate(), booking.getFromDate())));
        return bookingDao.save(booking);
    }

    @Override
    public boolean isValidPaymentService(String paymentMode) {

        if (!(paymentMode.equals("UPI") || paymentMode.equals("CARD"))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidBooking(int bookingID) {
        return bookingDao.findById(bookingID).isPresent();
    }

    @Override
    public BookingInfoEntity updateTransactionID(PaymentDTO paymentDTO) {
        BookingInfoEntity booking = bookingDao.findById(paymentDTO.getBookingId()).get();
        Map<String, String> paymentUriMap = new HashMap<>();
        paymentUriMap.put("paymentMode", String.valueOf(paymentDTO.getPaymentMode()));
        paymentUriMap.put("bookingId", String.valueOf(paymentDTO.getBookingId()));
        paymentUriMap.put("upiId", String.valueOf(paymentDTO.getUpiId()));
        paymentUriMap.put("cardNumber", String.valueOf(paymentDTO.getCardNumber()));
        Integer transactionID = restTemplate.postForObject(paymentURL, paymentDTO, Integer.class);
        booking.setTransactionId(transactionID);
        return bookingDao.save(booking);
    }

}
