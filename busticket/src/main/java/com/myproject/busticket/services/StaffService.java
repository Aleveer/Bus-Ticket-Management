package com.myproject.busticket.services;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.mapper.StaffMapper;
import com.myproject.busticket.models.Staff;
import com.myproject.busticket.repositories.StaffRepository;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    StaffMapper staffMapper = Mappers.getMapper(StaffMapper.class);

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(int id) {
        return staffRepository.findByStaffId(id);
    }

}
