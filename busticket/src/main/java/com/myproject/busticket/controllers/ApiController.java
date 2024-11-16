package com.myproject.busticket.controllers;

import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.DriverService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.TripService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    DriverService driverService;
    CustomerService customerService;

    @Autowired
    private TripService tripService;

    @Autowired
    private BusService busService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private RouteCheckpointService routeCheckpointService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    public ApiController(DriverService driverService, CustomerService customerService) {
        this.driverService = driverService;
        this.customerService = customerService;
    }

    @GetMapping("/api/driver/{driverId}")
    public Driver getDriverById(@PathVariable int driverId) {
        return driverService.getDriverById(driverId);
    }

    @GetMapping("/api/customer/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/admin/api/trips")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTrips(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> tripsPage = tripService.getAll(pageable);

        List<Trip> trips = tripsPage.getContent().stream()
                .map(trip -> new Trip(trip.getTripId(), trip.getDepartureTime(), trip.getArrivalTime(),
                        trip.getPrice(), trip.getStatus(), trip.getBus(), trip.getDriver(),
                        trip.getController(),
                        trip.getStaff(), trip.getRoute(), trip.getNumberOfSeatAvailable()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("trips", trips);
        response.put("currentPage", page);
        response.put("totalPages", tripsPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/buses")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBuses(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busPages = busService.getAll(pageable);

        List<Bus> buses = busPages.getContent().stream()
                .map(bus -> new Bus(bus.getPlate(), bus.getType(), bus.getNumberOfSeat()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("buses", buses);
        response.put("currentPage", page);
        response.put("totalPages", busPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/routes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRoutes(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Route> routePages = routeService.getAll(pageable);

        List<Route> routes = routePages.getContent().stream()
                .map(route -> new Route(route.getRouteId(), route.getCode(), route.getName(),
                        route.getTime(),
                        route.getDistance()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("routes", routes);
        response.put("currentPage", page);
        response.put("totalPages", routePages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/checkpoints")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCheckpoints(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Checkpoint> checkpointPages = checkpointService.getAll(pageable);

        List<Checkpoint> checkpoints = checkpointPages.getContent().stream()
                .map(checkpoint -> new Checkpoint(checkpoint.getCheckpointId(),
                        checkpoint.getPlaceName(), checkpoint.getAddress(),
                        checkpoint.getPhone(), checkpoint.getRegion()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("checkpoints", checkpoints);
        response.put("currentPage", page);
        response.put("totalPages", checkpointPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/accounts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAccounts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPages = accountService.getAll(pageable);

        List<AccountDTO> accountDTOs = accountPages.getContent().stream()
                .map(account -> new AccountDTO(account.getId(), account.getEmail(),
                        account.getPassword(),
                        account.getFullName(), account.getPhone(),
                        roleService.getRoleById(account.getRole().getRoleId()),
                        account.getStatus(),
                        account.getVerificationCode(), account.getVerificationExpiration(),
                        account.getLoginToken(),
                        account.getPasswordResetToken(), account.getPasswordResetExpiration(),
                        account.isEnabled()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("accounts", accountDTOs);
        response.put("currentPage", page);
        response.put("totalPages", accountPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/route-detail/{routeCode}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRouteDetails(@PathVariable String routeCode) {
        Route route = routeService.getRouteByCode(routeCode);
        List<Route_Checkpoint> routeCheckpoints = routeCheckpointService.findByRoute(route);

        if (routeCheckpoints.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "No checkpoints found for this route.");
            return ResponseEntity.ok(response);
        }

        List<Map<String, Object>> routeCheckpointsDTO = routeCheckpoints.stream()
                .map(routeCheckpoint -> {
                    Map<String, Object> checkpointDTO = new HashMap<>();
                    checkpointDTO.put("id", routeCheckpoint.getId());
                    checkpointDTO.put("routeCode", routeCheckpoint.getRoute().getCode());
                    checkpointDTO.put("checkpointId", routeCheckpoint.getCheckpoint().getCheckpointId());
                    checkpointDTO.put("placeName", routeCheckpoint.getCheckpoint().getPlaceName());
                    checkpointDTO.put("address", routeCheckpoint.getCheckpoint().getAddress());
                    checkpointDTO.put("checkpointOrder", routeCheckpoint.getCheckpointOrder());
                    checkpointDTO.put("checkpointCity", routeCheckpoint.getCheckpointCity());
                    checkpointDTO.put("checkpointProvince", routeCheckpoint.getCheckpointProvince());
                    checkpointDTO.put("type", routeCheckpoint.getType().name());
                    return checkpointDTO;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("route", route);
        response.put("checkpoints", routeCheckpointsDTO);

        return ResponseEntity.ok(response);
    }
}
