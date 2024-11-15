package com.myproject.busticket.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.myproject.busticket.dto.BusDTO;
import com.myproject.busticket.models.Bus;

@Mapper(componentModel = "spring")
public interface BusMapper {

    @Mapping(source = "plate", target = "busId")
    @Mapping(source = "numberOfSeat", target = "numberOfSeats")
    BusDTO entityToDTO(Bus bus);

    @Mapping(source = "busId", target = "plate")
    @Mapping(source = "numberOfSeats", target = "numberOfSeat")
    Bus dtoToEntity(BusDTO busDTO);

    List<BusDTO> map(List<Bus> buses);
}