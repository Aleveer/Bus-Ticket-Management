package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Feedback;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.FeedbackRepository;
import com.myproject.busticket.mapper.TripMapper;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TripMapper tripMapper;

    public Page<Feedback> findByTrip(Trip trip, Pageable pageable) {
        return feedbackRepository.findByTrip(trip, pageable);
    }

    public Page<Feedback> findByTripDTO(TripDTO tripDTO, Pageable pageable) {
        Trip trip = tripMapper.dtoToEntity(tripDTO);
        return feedbackRepository.findByTrip(trip, pageable);
    }
}