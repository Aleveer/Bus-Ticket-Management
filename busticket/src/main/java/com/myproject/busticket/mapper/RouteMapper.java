package com.myproject.busticket.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.myproject.busticket.dto.RouteDTO;
import com.myproject.busticket.models.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteDTO entityToDTO(Route route);

    List<RouteDTO> map(List<Route> routes);
}
