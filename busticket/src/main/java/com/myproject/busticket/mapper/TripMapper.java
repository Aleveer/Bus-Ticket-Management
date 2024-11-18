package com.myproject.busticket.mapper;

import com.myproject.busticket.dto.BusDTO;
import com.myproject.busticket.dto.DriverDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.models.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {
    TripDTO entityToDTO(Trip trip);

    Trip dtoToEntity(TripDTO tripDTO);

    List<TripDTO> map(List<Trip> trips);

    DriverDTO entityToDTO(Driver driver);
}
