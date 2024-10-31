package com.myproject.busticket.mapper;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper( TripMapper.class );

    TripDTO toTripDTO(Trip trip);
}
