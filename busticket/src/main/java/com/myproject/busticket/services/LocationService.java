package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Location;
import com.myproject.busticket.repositories.LocationRepository;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location getById(int id) {
        return locationRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Location deleteById(int id) {
        Location location = getById(id);
        if (location != null) {
            locationRepository.deleteById(id);
        }
        return location;
    }

    public List<Location> findByName(String city) {
        return locationRepository.findByName(city);
    }

    public List<Location> findByAddress(String address) {
        return locationRepository.findByAddress(address);
    }

}
