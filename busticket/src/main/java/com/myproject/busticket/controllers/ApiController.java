package com.myproject.busticket.controllers;

import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.dto.RouteCheckpointDTO;
import com.myproject.busticket.enums.CheckpointType;
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

import java.util.Comparator;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                .map(checkpoint -> new Checkpoint(
                        checkpoint.getCheckpointId(),
                        checkpoint.getPlaceName(),
                        checkpoint.getAddress(),
                        checkpoint.getProvince(),
                        checkpoint.getCity(),
                        checkpoint.getPhone(),
                        checkpoint.getRegion()))
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

        List<RouteCheckpointDTO> routeCheckpointsDTO = routeCheckpoints.stream()
                .map(rc -> new RouteCheckpointDTO(
                        rc.getCheckpoint().getCheckpointId(),
                        rc.getCheckpoint().getPlaceName(),
                        rc.getCheckpoint().getAddress(),
                        rc.getCheckpoint().getProvince(),
                        rc.getCheckpoint().getCity(),
                        rc.getCheckpointOrder(),
                        rc.getType()))
                .collect(Collectors.toList());

        routeCheckpointsDTO.sort((rc1, rc2) -> rc1.getCheckpointOrder() - rc2.getCheckpointOrder());

        Map<String, Object> response = new HashMap<>();
        response.put("route", route);
        response.put("checkpoints", routeCheckpointsDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/new-route")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveRoute(@RequestBody Map<String, Object> routeRequest) {
        Map<String, Object> response = new HashMap<>();

        // Extract route details from the request
        String code = (String) routeRequest.get("code");
        String name = (String) routeRequest.get("name");
        String time = (String) routeRequest.get("time");
        double distance = Double.parseDouble(routeRequest.get("distance").toString());

        // Validate route details
        if (code == null || code.isEmpty() ||
                name == null || name.isEmpty() ||
                time == null ||
                distance <= 0) {
            response.put("message", "Invalid input data.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if the route already exists
        Route existingRoute = routeService.getRouteByCode(code);
        if (existingRoute != null) {
            response.put("message", "Route with this code already exists.");
            return ResponseEntity.badRequest().body(response);
        }

        Route newRoute = new Route();
        newRoute.setCode(code);
        newRoute.setName(name);
        newRoute.setTime(time);
        newRoute.setDistance(distance);
        routeService.save(newRoute);

        int i = 1;

        List<Map<String, Object>> checkpoints = (List<Map<String, Object>>) routeRequest.get("checkpoints");
        if (checkpoints.isEmpty()) {
            response.put("message", "No checkpoints found for this route.");
            return ResponseEntity.badRequest().body(response);
        }

        if (checkpoints.size() < 2) {
            response.put("message", "A route must have at least 2 checkpoints.");
            routeService.deleteByCode(code);
            return ResponseEntity.badRequest().body(response);
        }

        for (Map<String, Object> checkpointData : checkpoints) {
            Route_Checkpoint routeCheckpoint = new Route_Checkpoint();
            routeCheckpoint.setRoute(newRoute);
            routeCheckpoint.setCheckpointOrder(i++);

            // Ensure checkpointId is correctly retrieved and cast to an integer
            Object checkpointIdObj = checkpointData.get("checkpointId");
            if (checkpointIdObj == null) {
                response.put("message", "Checkpoint ID is missing.");
                routeService.deleteByCode(code);
                return ResponseEntity.badRequest().body(response);
            }

            Integer checkpointId;
            try {
                checkpointId = Integer.parseInt(checkpointIdObj.toString());
            } catch (NumberFormatException e) {
                routeService.deleteByCode(code);
                response.put("message", "Invalid checkpoint ID format.");
                return ResponseEntity.badRequest().body(response);
            }

            routeCheckpoint.setCheckpoint(checkpointService.getById(checkpointId));
            routeCheckpoint.setType(CheckpointType.departure);
            routeCheckpointService.save(routeCheckpoint);
        }

        response.put("message", "Route saved successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/update-route/{routeCode}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateRoute(@PathVariable String routeCode,
            @RequestBody Map<String, Object> routeRequest) {
        Map<String, Object> response = new HashMap<>();

        String name = (String) routeRequest.get("name");
        String time = (String) routeRequest.get("time");
        double distance = Double.parseDouble(routeRequest.get("distance").toString());

        if (!isValidInput(name, time, distance)) {
            response.put("message", "Invalid input data.");
            return ResponseEntity.badRequest().body(response);
        }

        Route existingRoute = routeService.getRouteByCode(routeCode);
        if (existingRoute == null) {
            response.put("message", "Route not found.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Route_Checkpoint> oldRouteCheckpoints = routeCheckpointService.findByRoute(existingRoute);
        List<Map<String, Object>> newCheckpoints = (List<Map<String, Object>>) routeRequest.get("checkpoints");

        if (newCheckpoints.isEmpty()) {
            response.put("message", "No checkpoints found for this route.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!hasChanges(existingRoute, name, time, distance, oldRouteCheckpoints, newCheckpoints)) {
            response.put("message", "Nothing has been changed.");
            return ResponseEntity.badRequest().body(response);
        }

        // Proceed with the update
        updateRouteDetails(existingRoute, name, time, distance);
        updateRouteCheckpoints(existingRoute, oldRouteCheckpoints, newCheckpoints);

        response.put("message", "Route updated successfully.");
        return ResponseEntity.ok(response);
    }

    private boolean isValidInput(String name, String time, double distance) {
        return name != null && !name.isEmpty() && time != null && distance > 0;
    }

    private boolean hasChanges(Route existingRoute, String name, String time, double distance,
            List<Route_Checkpoint> oldRouteCheckpoints, List<Map<String, Object>> newCheckpoints) {
        if (!existingRoute.getName().equals(name) ||
                !existingRoute.getTime().equals(time) ||
                existingRoute.getDistance() != distance) {
            return true;
        }

        if (oldRouteCheckpoints.size() != newCheckpoints.size()) {
            return true;
        }

        oldRouteCheckpoints.sort(Comparator.comparingInt(Route_Checkpoint::getCheckpointOrder));
        newCheckpoints.sort(Comparator.comparingInt(c -> (int) c.get("checkpointOrder")));

        // Check if same checkpoints but different order by checkpointOrder:
        for (int i = 0; i < newCheckpoints.size(); i++) {
            Map<String, Object> checkpointData = newCheckpoints.get(i);
            int checkpointId = (int) checkpointData.get("checkpointId");
            String type = (String) checkpointData.get("type");
            int checkpointOrder = (int) checkpointData.get("checkpointOrder");

            Route_Checkpoint routeCheckpoint = oldRouteCheckpoints.get(i);

            if (routeCheckpoint.getCheckpoint().getCheckpointId() != checkpointId ||
                    !routeCheckpoint.getType().name().equals(type) ||
                    routeCheckpoint.getCheckpointOrder() != checkpointOrder) {
                return true;
            }
        }
        return false;
    }

    private void updateRouteDetails(Route existingRoute, String name, String time, double distance) {
        existingRoute.setName(name);
        existingRoute.setTime(time);
        existingRoute.setDistance(distance);
        routeService.save(existingRoute);
    }

    private void updateRouteCheckpoints(Route existingRoute, List<Route_Checkpoint> oldRouteCheckpoints,
            List<Map<String, Object>> newCheckpoints) {
        if (newCheckpoints.size() < 2) {
            throw new IllegalArgumentException("A route must have at least 2 checkpoints.");
        }

        // Delete old checkpoints
        for (Route_Checkpoint oldRouteCheckpoint : oldRouteCheckpoints) {
            routeCheckpointService.delete(oldRouteCheckpoint);
        }

        // Add new checkpoints
        int i = 1;
        for (Map<String, Object> checkpointData : newCheckpoints) {
            Route_Checkpoint routeCheckpoint = new Route_Checkpoint();
            routeCheckpoint.setRoute(existingRoute);

            Integer checkpointId = parseCheckpointId(checkpointData.get("checkpointId"));
            routeCheckpoint.setCheckpoint(checkpointService.getById(checkpointId));

            String type = (String) checkpointData.get("type");
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("Checkpoint type is missing.");
            }

            routeCheckpoint.setType(parseCheckpointType(type));
            routeCheckpoint.setCheckpointOrder(checkpointData.get("checkpointOrder") == null ? i++
                    : (int) checkpointData.get("checkpointOrder"));
            routeCheckpointService.save(routeCheckpoint);
        }
    }

    private Integer parseCheckpointId(Object checkpointIdObj) {
        try {
            return Integer.parseInt(checkpointIdObj.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid checkpoint ID format.");
        }
    }

    private CheckpointType parseCheckpointType(String type) {
        try {
            return CheckpointType.valueOf(type.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid checkpoint type.");
        }
    }

    @GetMapping("/admin/api/selected-checkpoints/{routeCode}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSelectedCheckpoints(@PathVariable String routeCode) {
        Route route = routeService.getRouteByCode(routeCode);
        List<Route_Checkpoint> routeCheckpoints = routeCheckpointService.findByRoute(route);

        if (routeCheckpoints.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "No checkpoints found for this route.");
            return ResponseEntity.ok(response);
        }

        List<RouteCheckpointDTO> selectedCheckpointsDTO = routeCheckpoints.stream()
                .map(rc -> new RouteCheckpointDTO(
                        rc.getCheckpoint().getCheckpointId(),
                        rc.getCheckpoint().getPlaceName(),
                        rc.getCheckpoint().getAddress(),
                        rc.getCheckpoint().getProvince(),
                        rc.getCheckpoint().getCity(),
                        rc.getCheckpointOrder(),
                        rc.getType()))
                .collect(Collectors.toList());

        selectedCheckpointsDTO.sort((rc1, rc2) -> rc1.getCheckpointOrder() - rc2.getCheckpointOrder());

        Map<String, Object> response = new HashMap<>();
        response.put("route", route);
        response.put("selectedCheckpoints", selectedCheckpointsDTO);

        return ResponseEntity.ok(response);
    }
}
