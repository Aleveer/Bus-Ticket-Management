package com.myproject.busticket.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.enums.UserRole;
import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Schedule;
import com.myproject.busticket.models.User;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.ScheduleService;
import com.myproject.busticket.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @PostMapping("/createNewSchedule")
    public Map<String, Object> addSchedule(@RequestBody Map<String, Object> scheduleMap) {
        Map<String, Object> response = new HashMap<>();

        try {
            Schedule schedule = new Schedule();
            // Check for bus:
            Bus bus = busService.getById(Integer.parseInt(scheduleMap.get("bus_id").toString()));

            if (bus == null) {
                response.put("success", false);
                response.put("message", "Bus not found");
                return response;
            }

            // Check for driver:
            User driver = userService.getById(Integer.parseInt(scheduleMap.get("user_id").toString()));

            if (driver == null) {
                response.put("success", false);
                response.put("message", "Driver not found");
                return response;
            }

            if (driver.getRole().toString() != UserRole.driver.toString()) {
                response.put("success", false);
                response.put("message", "User is not a driver");
                return response;
            }

            // Check for route:
            Route route = routeService.getById(Integer.parseInt(scheduleMap.get("route_id").toString()));
            if (route == null) {
                response.put("success", false);
                response.put("message", "Route not found");
                return response;
            }

            scheduleService.save(schedule);
            response.put("message", "Schedule added successfully");
            response.put("schedule", schedule);
            return response;
        } catch (ValidationException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred");
            return response;
        }
    }

}
