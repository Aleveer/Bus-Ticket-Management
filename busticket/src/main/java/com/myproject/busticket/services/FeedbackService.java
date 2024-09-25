package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Feedback;
import com.myproject.busticket.repositories.FeedbackRepository;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    public Feedback getById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback deleteById(int id) {
        Feedback feedback = getById(id);
        if (feedback != null) {
            feedbackRepository.deleteById(id);
        }
        return feedback;
    }

    public List<Feedback> findByUserId(int id) {
        return feedbackRepository.findByUser_id(id);
    }

    public List<Feedback> findByTripId(int id) {
        return feedbackRepository.findByTrip_id(id);
    }

}
