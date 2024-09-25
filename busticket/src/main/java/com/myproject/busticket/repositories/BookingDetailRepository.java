package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.BookingDetail;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {

    List<BookingDetail> findByBooking_id(int id);

    List<BookingDetail> findByUser_id(int id);

    List<BookingDetail> findBySeat_id(int id);

    // TODO: Implements more custom query if needed.
}
