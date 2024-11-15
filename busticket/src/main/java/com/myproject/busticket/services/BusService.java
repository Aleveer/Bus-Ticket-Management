package com.myproject.busticket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.dto.BusDTO;
import com.myproject.busticket.mapper.BusMapper;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.repositories.BusRepository;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    BusMapper busMapper = Mappers.getMapper(BusMapper.class);

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Page<Bus> getAll(Pageable pageable) {
        return busRepository.findAll(pageable);
    }
}
