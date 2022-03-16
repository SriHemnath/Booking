package com.assignment.bookingService.mapper;

import com.assignment.bookingService.dto.BookingDTO;
import com.assignment.bookingService.dto.BookingDetailsDTO;
import com.assignment.bookingService.entity.BookingInfoEntity;

public class CustomMapper {

    public static BookingInfoEntity convertDTOToEntity(BookingDTO bookingDTO) {
        BookingInfoEntity bookingEntity = new BookingInfoEntity();
        bookingEntity.setFromDate(bookingDTO.getFromDate());
        bookingEntity.setToDate(bookingDTO.getToDate());
        bookingEntity.setAadharNumber(bookingDTO.getAadharNumber());
        bookingEntity.setNumOfRooms(bookingDTO.getNumOfRooms());
        bookingEntity.setRoomPrice(bookingDTO.getRoomPrice());
        bookingEntity.setTransactionId(bookingDTO.getTransactionId());
        bookingEntity.setBookedOn(bookingDTO.getBookedOn());
        return bookingEntity;
    }

    public static BookingDetailsDTO convertEntityToDTO(BookingInfoEntity bookingEntity) {
        BookingDetailsDTO bookingDTO = new BookingDetailsDTO();
        bookingDTO.setId(bookingEntity.getBookingId());
        bookingDTO.setTransactionId(bookingEntity.getTransactionId());
        bookingDTO.setFromDate(bookingEntity.getFromDate());
        bookingDTO.setToDate(bookingEntity.getToDate());
        bookingDTO.setAadharNumber(bookingEntity.getAadharNumber());
        bookingDTO.setRoomNumbers(bookingEntity.getRoomNumbers());
        bookingDTO.setRoomPrice(bookingEntity.getRoomPrice());
        bookingDTO.setBookedOn(bookingEntity.getBookedOn());
        return bookingDTO;
    }
}
