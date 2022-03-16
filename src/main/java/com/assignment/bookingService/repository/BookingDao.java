package com.assignment.bookingService.repository;

import com.assignment.bookingService.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDao extends JpaRepository<BookingInfoEntity,Integer> {
}
