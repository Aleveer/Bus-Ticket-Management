package com.myproject.busticket.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.dto.BookingDTO;
import com.myproject.busticket.dto.BusDTO;
import com.myproject.busticket.dto.ControllerDTO;
import com.myproject.busticket.dto.CustomerDTO;
import com.myproject.busticket.dto.DriverDTO;
import com.myproject.busticket.dto.RouteCheckpointDTO;
import com.myproject.busticket.dto.RouteDTO;
import com.myproject.busticket.dto.StaffDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.enums.CheckpointType;
import com.myproject.busticket.enums.SeatReservationStatus;
import com.myproject.busticket.enums.SeatType;
import com.myproject.busticket.enums.TripStatus;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.models.Controller;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Route_Checkpoint;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.models.Staff;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.BillDetailService;
import com.myproject.busticket.services.BillService;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.Bus_SeatsService;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.ControllerService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.DriverService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.SeatReservationsService;
import com.myproject.busticket.services.StaffService;
import com.myproject.busticket.services.TripService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private DriverService driverService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ControllerService controllerService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private TripService tripService;

    @Autowired
    private BusService busService;

    @Autowired
    private Bus_SeatsService bus_SeatsService;

    @Autowired
    private SeatReservationsService seatReservationService;

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

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

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
                        trip.getStaff(), trip.getRoute()))
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
                .map(bus -> new Bus(bus.getPlate(), bus.getSeatType(), bus.getNumberOfSeat()))
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

    @GetMapping("/admin/api/drivers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDrivers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPages = driverService.getAllDrivers(pageable);

        List<DriverDTO> driverDTOs = driverPages.getContent().stream()
                .map(driver -> {
                    Account account = driver.getAccount();
                    AccountDTO accountDTO = new AccountDTO(
                            account.getId(),
                            account.getEmail(),
                            account.getPassword(),
                            account.getFullName(),
                            account.getPhone(),
                            roleService.getRoleById(account.getRole().getRoleId()),
                            account.getStatus(),
                            account.getVerificationCode(),
                            account.getVerificationExpiration(),
                            account.getLoginToken(),
                            account.getPasswordResetToken(),
                            account.getPasswordResetExpiration(),
                            account.isEnabled());
                    return new DriverDTO(driver.getDriverId(), accountDTO, driver.getStatus());
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("drivers", driverDTOs);
        response.put("currentPage", page);
        response.put("totalPages", driverPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/controllers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getControllers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Controller> controllerPages = controllerService.getAll(pageable);

        List<ControllerDTO> controllerDTOs = controllerPages.getContent().stream()
                .map(controller -> {
                    Account account = controller.getAccount();
                    AccountDTO accountDTO = new AccountDTO(
                            account.getId(),
                            account.getEmail(),
                            account.getPassword(),
                            account.getFullName(),
                            account.getPhone(),
                            roleService.getRoleById(account.getRole().getRoleId()),
                            account.getStatus(),
                            account.getVerificationCode(),
                            account.getVerificationExpiration(),
                            account.getLoginToken(),
                            account.getPasswordResetToken(),
                            account.getPasswordResetExpiration(),
                            account.isEnabled());
                    return new ControllerDTO(controller.getId(), accountDTO, controller.getStatus());
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("controllers", controllerDTOs);
        response.put("currentPage", page);
        response.put("totalPages", controllerPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/staffs")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getStaffs(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Staff> staffPages = staffService.getAllStaffs(pageable);

        List<StaffDTO> staffDTOs = staffPages.getContent().stream()
                .map(staff -> {
                    Account account = staff.getAccount();
                    AccountDTO accountDTO = new AccountDTO(
                            account.getId(),
                            account.getEmail(),
                            account.getPassword(),
                            account.getFullName(),
                            account.getPhone(),
                            roleService.getRoleById(account.getRole().getRoleId()),
                            account.getStatus(),
                            account.getVerificationCode(),
                            account.getVerificationExpiration(),
                            account.getLoginToken(),
                            account.getPasswordResetToken(),
                            account.getPasswordResetExpiration(),
                            account.isEnabled());
                    return new StaffDTO(staff.getStaffId(), accountDTO, staff.getStatus());
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("staffs", staffDTOs);
        response.put("currentPage", page);
        response.put("totalPages", staffPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/customers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCustomers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPages = accountService.getAll(pageable);
        // Filter by role to get only drivers:
        List<AccountDTO> accountDTOs = accountPages.getContent().stream()
                .filter(account -> account.getRole().getRoleId() == 4) // 4 = customer
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

    // @GetMapping("/admin/api/guests")
    // @ResponseBody
    // public ResponseEntity<Map<String, Object>>
    // getGuests(@RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "15") int size) {
    // Pageable pageable = PageRequest.of(page, size);
    // Page<Account> accountPages = accountService.getAll(pageable);
    // // Filter by role to get only drivers:
    // List<AccountDTO> accountDTOs = accountPages.getContent().stream()
    // .filter(account -> account.getRole().getRoleId() == 1) // 1 = driver
    // .map(account -> new AccountDTO(account.getId(), account.getEmail(),
    // account.getPassword(),
    // account.getFullName(), account.getPhone(),
    // roleService.getRoleById(account.getRole().getRoleId()),
    // account.getStatus(),
    // account.getVerificationCode(), account.getVerificationExpiration(),
    // account.getLoginToken(),
    // account.getPasswordResetToken(), account.getPasswordResetExpiration(),
    // account.isEnabled()))
    // .collect(Collectors.toList());

    // Map<String, Object> response = new HashMap<>();
    // response.put("accounts", accountDTOs);
    // response.put("currentPage", page);
    // response.put("totalPages", accountPages.getTotalPages());

    // return ResponseEntity.ok(response);
    // }
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
                time == null || time.isEmpty() ||
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

        // Check for changes in checkpoint type:
        for (int i = 0; i < newCheckpoints.size(); i++) {
            Map<String, Object> checkpointData = newCheckpoints.get(i);
            String type = (String) checkpointData.get("checkpointType");

            Route_Checkpoint routeCheckpoint = oldRouteCheckpoints.get(i);

            if (!routeCheckpoint.getType().name().equals(type)) {
                return true;
            }
        }

        // Check if same checkpoints but different order by checkpointOrder:
        for (int i = 0; i < newCheckpoints.size(); i++) {
            Map<String, Object> checkpointData = newCheckpoints.get(i);
            int checkpointId = (int) checkpointData.get("checkpointId");
            String type = (String) checkpointData.get("checkpointType");
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

            String type = (String) checkpointData.get("checkpointType");
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("Checkpoint type is missing.");
            }

            // TODO: Check if type is valid
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

    @PostMapping("/admin/api/delete-route")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteRoute(@RequestBody Map<String, Object> routeRequest) {
        Map<String, Object> response = new HashMap<>();
        String code = (String) routeRequest.get("code");
        Route existingRoute = routeService.getRouteByCode(code);
        if (existingRoute == null) {
            response.put("message", "Route not found.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!tripService.findTripByRouteCode(existingRoute).isEmpty()) {
            response.put("message", "Route has trips and cannot be deleted.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Route_Checkpoint> routeCheckpoints = routeCheckpointService.findByRoute(existingRoute);
        for (Route_Checkpoint routeCheckpoint : routeCheckpoints) {
            routeCheckpointService.delete(routeCheckpoint);
        }

        routeService.deleteByCode(code);
        response.put("message", "Route deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/checkpoint/{checkpointId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCheckpointById(@PathVariable int checkpointId) {
        Checkpoint checkpoint = checkpointService.getById(checkpointId);
        if (checkpoint == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Checkpoint not found.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Route_Checkpoint> routeCheckpoints = routeCheckpointService.findByCheckpoint(checkpoint);
        List<Map<String, Object>> assignedRoutes = routeCheckpoints.stream()
                .map(rc -> {
                    Map<String, Object> routeCheckpointData = new HashMap<>();
                    routeCheckpointData.put("routeCode", rc.getRoute().getCode());
                    routeCheckpointData.put("routeName", rc.getRoute().getName());
                    routeCheckpointData.put("checkpointOrder", rc.getCheckpointOrder());
                    routeCheckpointData.put("type", rc.getType().name());
                    return routeCheckpointData;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("checkpointId", checkpoint.getCheckpointId());
        response.put("placeName", checkpoint.getPlaceName());
        response.put("address", checkpoint.getAddress());
        response.put("province", checkpoint.getProvince());
        response.put("city", checkpoint.getCity());
        response.put("phone", checkpoint.getPhone());
        response.put("region", checkpoint.getRegion());
        response.put("assignedRoutes", assignedRoutes);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/update-checkpoint")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCheckpoint(@RequestBody Map<String, Object> checkpointRequest) {
        Map<String, Object> response = new HashMap<>();
        String checkpointIdStr = (String) checkpointRequest.get("checkpointId");
        int checkpointId;
        try {
            checkpointId = Integer.parseInt(checkpointIdStr);
        } catch (NumberFormatException e) {
            response.put("message", "Invalid checkpoint ID format.");
            return ResponseEntity.badRequest().body(response);
        }

        Checkpoint checkpoint = checkpointService.getById(checkpointId);
        if (checkpoint == null) {
            response.put("message", "Checkpoint not found.");
            return ResponseEntity.badRequest().body(response);
        }

        String placeName = (String) checkpointRequest.get("placeName");
        String address = (String) checkpointRequest.get("address");
        String province = (String) checkpointRequest.get("province");
        String city = (String) checkpointRequest.get("city");
        String phone = (String) checkpointRequest.get("phone");
        String region = (String) checkpointRequest.get("region");

        String validationMessage = validateCheckpointFields(placeName, address, province, city, phone, region);
        if (validationMessage != null) {
            response.put("message", validationMessage);
            return ResponseEntity.badRequest().body(response);
        }

        checkpoint.setPlaceName(placeName);
        checkpoint.setAddress(address);
        checkpoint.setProvince(province);
        checkpoint.setCity(city);
        checkpoint.setPhone(phone);
        checkpoint.setRegion(region);

        checkpointService.save(checkpoint);
        response.put("message", "Checkpoint updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/new-checkpoint")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> newCheckpoint(@RequestBody Map<String, Object> checkpointRequest) {
        Map<String, Object> response = new HashMap<>();

        String placeName = (String) checkpointRequest.get("placeName");
        String address = (String) checkpointRequest.get("address");
        String province = (String) checkpointRequest.get("province");
        String city = (String) checkpointRequest.get("city");
        String phone = (String) checkpointRequest.get("phone");
        String region = (String) checkpointRequest.get("region");

        String validationMessage = validateCheckpointFields(placeName, address, province, city, phone, region);
        if (validationMessage != null) {
            response.put("message", validationMessage);
            return ResponseEntity.badRequest().body(response);
        }

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setCheckpointId(0);
        checkpoint.setPlaceName(placeName);
        checkpoint.setAddress(address);
        checkpoint.setProvince(province);
        checkpoint.setCity(city);
        checkpoint.setPhone(phone);
        checkpoint.setRegion(region);

        checkpointService.save(checkpoint);
        response.put("message", "Checkpoint created successfully.");
        return ResponseEntity.ok(response);
    }

    private String validateCheckpointFields(String placeName, String address, String province, String city,
            String phone, String region) {
        if (placeName == null || placeName.isEmpty() ||
                address == null || address.isEmpty() ||
                province == null || province.isEmpty() ||
                city == null || city.isEmpty() ||
                phone == null || phone.isEmpty() ||
                region == null || region.isEmpty()) {
            return "All fields are required.";
        }

        String regex = "^[A-Za-z0-9\\s\\*\\,\\.\\-\\_\\(\\)\\[\\]\\{\\}\\@\\#\\$\\%\\^\\&\\!\\?\\+\\=\\:\\;\\'\\\"ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸỳỵỷỹ]*$";
        if (!placeName.matches(regex) || !address.matches(regex) || !province.matches(regex) ||
                !city.matches(regex) || !phone.matches(regex) || !region.matches(regex)) {
            return "Invalid characters in input fields.";
        }

        // Check for valid phone number:
        if (!phone.matches("^[0-9\\.]*$")) {
            return "Invalid phone number.";
        }

        return null;
    }

    @PostMapping("/admin/api/delete-checkpoint")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteCheckpoint(@RequestBody Map<String, Object> checkpointRequest) {
        Map<String, Object> response = new HashMap<>();
        String checkpointIdStr = (String) checkpointRequest.get("checkpointId");
        int checkpointId;
        try {
            checkpointId = Integer.parseInt(checkpointIdStr);
        } catch (NumberFormatException e) {
            response.put("message", "Invalid checkpoint ID format.");
            return ResponseEntity.badRequest().body(response);
        }

        Checkpoint checkpoint = checkpointService.getById(checkpointId);
        if (checkpoint == null) {
            response.put("message", "Checkpoint not found.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Route_Checkpoint> routeCheckpoints = routeCheckpointService.findByCheckpoint(checkpoint);
        if (!routeCheckpoints.isEmpty()) {
            response.put("message", "Checkpoint is used in a route and cannot be deleted.");
            return ResponseEntity.badRequest().body(response);
        }

        checkpointService.deleteById(checkpointId);
        response.put("message", "Checkpoint deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/new-bus")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveBus(@RequestBody Map<String, Object> busRequest) {
        Map<String, Object> response = new HashMap<>();
        String plate = (String) busRequest.get("plate");
        String seatTypeStr = (String) busRequest.get("seatType");
        String numberOfSeatStr = (String) busRequest.get("numberOfSeat");
        List<String> seatNames = (List<String>) busRequest.get("seatNames");
        int numberOfSeat;
        try {
            numberOfSeat = Integer.parseInt(numberOfSeatStr);
        } catch (NumberFormatException e) {
            response.put("message", "Invalid number of seats format.");
            return ResponseEntity.badRequest().body(response);
        }
        if (plate == null || plate.isEmpty() || seatTypeStr == null || seatTypeStr.isEmpty() || numberOfSeat <= 0
                || seatNames == null || seatNames.size() != numberOfSeat) {
            response.put("message", "Invalid input data.");
            return ResponseEntity.badRequest().body(response);
        }
        Bus existingBus = busService.getByBusPlate(plate);
        if (existingBus != null) {
            response.put("message", "Bus with this plate already exists.");
            return ResponseEntity.badRequest().body(response);
        }
        SeatType seatType;
        try {
            seatType = parseSeatType(seatTypeStr);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid seat type.");
            return ResponseEntity.badRequest().body(response);
        }
        Bus newBus = new Bus();
        newBus.setPlate(plate);
        newBus.setSeatType(seatType);
        newBus.setNumberOfSeat(numberOfSeat);
        busService.save(newBus);

        for (String seatName : seatNames) {
            Bus_Seats seat = new Bus_Seats();
            seat.setBus(newBus);
            seat.setSeatName(seatName);
            bus_SeatsService.save(seat);
        }

        response.put("message", "Bus saved successfully.");
        return ResponseEntity.ok(response);
    }

    private SeatType parseSeatType(String seatType) {
        try {
            return SeatType.valueOf(seatType.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid seat type.");
        }
    }

    @GetMapping("/bus-detail/{plate}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBusDetails(@PathVariable String plate) {
        Bus bus = busService.getByBusPlate(plate);
        if (bus == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bus not found."));
        }

        List<Map<String, Object>> seatDetails = bus_SeatsService.getByBusPlate(bus).stream().map(seat -> {
            Map<String, Object> seatMap = new HashMap<>();
            seatMap.put("seatName", seat.getSeatName());
            List<Map<String, Object>> reservationList = seatReservationService.getBySeat(seat).stream()
                    .map(reservation -> {
                        Map<String, Object> reservationMap = new HashMap<>();
                        reservationMap.put("customerName",
                                reservation.getBooking() != null ? reservation.getBooking().getCustomer().getName()
                                        : "");
                        reservationMap.put("customerEmail",
                                reservation.getBooking() != null ? reservation.getBooking().getCustomer().getEmail()
                                        : "");
                        reservationMap.put("trip", reservation.getTrip().getTripId());
                        reservationMap.put("status", reservation.getStatus().name());
                        return reservationMap;
                    }).collect(Collectors.toList());
            seatMap.put("reservations", reservationList);
            seatMap.put("status",
                    reservationList.stream().anyMatch(r -> r.get("status").equals(SeatReservationStatus.booked.name()))
                            ? "booked"
                            : "available");
            return seatMap;
        }).collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "busPlate", bus.getPlate(),
                "seatType", bus.getSeatType().name(),
                "numberOfSeats", bus.getNumberOfSeat(),
                "seats", seatDetails);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/seats/{seatId}/reservations")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSeatReservations(@PathVariable Integer seatId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Bus_Seats seat = bus_SeatsService.getById(seatId);

        List<Map<String, Object>> reservationList = seatReservationService.getBySeat(seat).stream()
                .filter(reservation -> reservation.getStatus() != SeatReservationStatus.open)
                .map(reservation -> {
                    Map<String, Object> reservationMap = new HashMap<>();
                    reservationMap.put("customerName",
                            reservation.getBooking() != null ? reservation.getBooking().getCustomer().getName() : "");
                    reservationMap.put("customerEmail",
                            reservation.getBooking() != null ? reservation.getBooking().getCustomer().getEmail() : "");
                    reservationMap.put("trip", reservation.getTrip().getTripId());
                    reservationMap.put("status", reservation.getStatus().name());
                    return reservationMap;
                }).collect(Collectors.toList());

        int start = Math.min(page * size, reservationList.size());
        int end = Math.min((page + 1) * size, reservationList.size());
        List<Map<String, Object>> paginatedList = reservationList.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("reservations", paginatedList);
        response.put("totalPages", (int) Math.ceil((double) reservationList.size() / size));
        response.put("currentPage", page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/bus/assigned-trips/{plate}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBusTrips(
            @PathVariable String plate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Bus bus = busService.getByBusPlate(plate);
        if (bus == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Bus not found."));
        }

        List<Trip> trips = tripService.findByBus(bus);
        if (trips == null || trips.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No trips found for this bus.", "trips", List.of()));
        }

        int totalElements = trips.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = Math.min(page * size, totalElements);
        int end = Math.min((page + 1) * size, totalElements);

        List<Map<String, Object>> tripDetails = trips.subList(start, end).stream()
                .map(trip -> {
                    Map<String, Object> tripMap = new HashMap<>();
                    tripMap.put("tripId", trip.getTripId());
                    tripMap.put("departureTime", trip.getDepartureTime());
                    tripMap.put("arrivalTime", trip.getArrivalTime());
                    tripMap.put("price", trip.getPrice());
                    tripMap.put("status", trip.getStatus() != null ? trip.getStatus().name() : "Unknown");
                    tripMap.put("routeCode", trip.getRoute() != null ? trip.getRoute().getCode() : "Unknown");
                    tripMap.put("driverName", trip.getDriver() != null && trip.getDriver().getAccount() != null
                            ? trip.getDriver().getAccount().getFullName()
                            : "Unknown");
                    tripMap.put("controllerName",
                            trip.getController() != null && trip.getController().getAccount() != null
                                    ? trip.getController().getAccount().getFullName()
                                    : "Unknown");
                    return tripMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "trips", tripDetails,
                "totalPages", totalPages,
                "totalElements", totalElements));
    }

    @PostMapping("/admin/api/delete-bus")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteBus(@RequestBody Map<String, Object> busRequest) {
        Map<String, Object> response = new HashMap<>();
        String plate = (String) busRequest.get("plate");
        Bus existingBus = busService.getByBusPlate(plate);
        if (existingBus == null) {
            response.put("message", "Bus not found.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!tripService.findByBus(existingBus).isEmpty()) {
            response.put("message", "Bus has been assigned to a trip(s) and cannot be deleted.");
            return ResponseEntity.badRequest().body(response);
        }

        // proceed deleting if no trips are assigned
        List<Bus_Seats> seats = bus_SeatsService.getByBusPlate(existingBus);
        if (!seats.isEmpty()) {
            bus_SeatsService.deleteAll(seats);
        }

        // Delete bus
        busService.delete(existingBus);
        response.put("message", "Bus deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/trip-detail/{tripId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTripDetails(@PathVariable int tripId) {
        Trip trip = tripService.findTripById(tripId);
        if (trip == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "Trip not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Bus existingBus = busService.getByBusPlate(trip.getBus().getPlate());
        if (existingBus == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "Bus not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<Bus_Seats> seats = bus_SeatsService.getByBusPlate(existingBus);
        if (seats.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "No seats found for this bus.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<Map<String, Object>> seatDetails = seats.stream()
                .map(seat -> {
                    Map<String, Object> seatMap = new HashMap<>();
                    SeatReservations reservation = seatReservationService.getReservationBySeatAndTrip(seat, trip);
                    seatMap.put("seatName", seat.getSeatName());
                    seatMap.put("status", reservation.getStatus().toString().toLowerCase());
                    if (reservation != null && reservation.getBooking() != null) {
                        seatMap.put("customerName", reservation.getBooking().getCustomer().getName());
                        seatMap.put("customerEmail", reservation.getBooking().getCustomer().getEmail());
                        seatMap.put("customerPhone", reservation.getBooking().getCustomer().getPhone());
                    } else {
                        seatMap.put("customerName", "");
                        seatMap.put("customerEmail", "");
                        seatMap.put("customerPhone", "");
                    }
                    return seatMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("trip", trip);
        response.put("seatDetails", seatDetails);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/upcoming-trips")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUpcomingTrips(@RequestParam String entityType,
            @RequestParam String entityId) {
        LocalDateTime now = LocalDateTime.now();
        List<Trip> upcomingTrips = tripService.findUpcomingTrips(now);
        List<Trip> ongoingTrips = tripService.findOnGoingTrips(now);

        // Combine upcoming and ongoing trips
        List<Trip> allTrips = new ArrayList<>();
        allTrips.addAll(upcomingTrips);
        allTrips.addAll(ongoingTrips);

        if (allTrips.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", "No upcoming or ongoing trips found.");
            return ResponseEntity.ok(response);
        }

        List<Trip> filteredTrips = allTrips.stream()
                .filter(trip -> {
                    switch (entityType) {
                        case "bus":
                            return trip.getBus().getPlate().equals(entityId);
                        case "driver":
                            return trip.getDriver().getDriverId() == Integer.parseInt(entityId);
                        case "controller":
                            return trip.getController().getId() == Integer.parseInt(entityId);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> tripDetails = filteredTrips.stream()
                .map(trip -> {
                    Map<String, Object> tripMap = new HashMap<>();
                    tripMap.put("tripId", trip.getTripId());
                    tripMap.put("departureTime", trip.getDepartureTime());
                    tripMap.put("arrivalTime", trip.getArrivalTime());
                    tripMap.put("price", trip.getPrice());
                    tripMap.put("status", trip.getStatus().toString().toLowerCase());
                    tripMap.put("busPlate", trip.getBus().getPlate());
                    tripMap.put("driverName", trip.getDriver().getAccount().getFullName());
                    tripMap.put("controllerName", trip.getController().getAccount().getFullName());
                    tripMap.put("staffName", trip.getStaff().getAccount().getFullName());
                    tripMap.put("routeCode", trip.getRoute().getCode());
                    return tripMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("trips", tripDetails);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/new-trip")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveTrip(@RequestBody Map<String, Object> tripRequest) {
        Map<String, Object> response = new HashMap<>();
        String departureTimeStr = (String) tripRequest.get("departureTime");
        if (departureTimeStr == null || departureTimeStr.isEmpty()) {
            response.put("message", "Departure time is required.");
            return ResponseEntity.badRequest().body(response);
        }

        String arrivalTimeStr = (String) tripRequest.get("arrivalTime");
        if (arrivalTimeStr == null || arrivalTimeStr.isEmpty()) {
            response.put("message", "Arrival time is required.");
            return ResponseEntity.badRequest().body(response);
        }

        if (arrivalTimeStr.equals(departureTimeStr)) {
            response.put("message", "Arrival time can't be the same as departure time.");
            return ResponseEntity.badRequest().body(response);
        } else if (LocalDateTime.parse(arrivalTimeStr).isBefore(LocalDateTime.parse(departureTimeStr))) {
            response.put("message", "Arrival time can't be before departure time.");
            return ResponseEntity.badRequest().body(response);
        }

        float price = Float.parseFloat(tripRequest.get("price").toString());
        if (price < 0) {
            response.put("message", "Price can't be negative.");
            return ResponseEntity.badRequest().body(response);
        }
        String busPlate = (String) tripRequest.get("busPlate");
        if (busPlate == null || busPlate.isEmpty()) {
            response.put("message", "Bus plate is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int driverId = Integer.parseInt(tripRequest.get("driverId").toString());
        if (driverId <= 0) {
            response.put("message", "Driver ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int controllerId = Integer.parseInt(tripRequest.get("controllerId").toString());
        if (controllerId <= 0) {
            response.put("message", "Controller ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int staffId = Integer.parseInt(tripRequest.get("staffId").toString());
        if (staffId <= 0) {
            response.put("message", "Staff ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        String routeCode = (String) tripRequest.get("routeCode");
        if (routeCode == null || routeCode.isEmpty()) {
            response.put("message", "Route code is required.");
            return ResponseEntity.badRequest().body(response);
        }

        LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr);
        LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr);

        List<Trip> conflictingTripsByBus = tripService.findConflictingTripsByBus(busPlate, departureTime,
                arrivalTime);
        if (!conflictingTripsByBus.isEmpty()) {
            response.put("message", "Bus is already assigned to another trip at the same time.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Trip> conflictingTripsByDriver = tripService.findConflictingTripsByDriver(driverId, departureTime,
                arrivalTime);
        if (!conflictingTripsByDriver.isEmpty()) {
            response.put("message", "Driver is already assigned to another trip at the same time.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Trip> conflictingTripsByController = tripService.findConflictingTripsByController(controllerId,
                departureTime, arrivalTime);
        if (!conflictingTripsByController.isEmpty()) {
            response.put("message", "Controller is already assigned to another trip at the same time.");
            return ResponseEntity.badRequest().body(response);
        }

        Bus existingBus = busService.getByBusPlate(busPlate);
        if (existingBus == null) {
            response.put("message", "Bus not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Driver driver = driverService.getDriverById(driverId);
        if (driver == null) {
            response.put("message", "Driver not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Controller controller = controllerService.getControllerById(controllerId);
        if (controller == null) {
            response.put("message", "Controller not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Staff staff = staffService.getStaffById(staffId);
        if (staff == null) {
            response.put("message", "Staff not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Route route = routeService.getRouteByCode(routeCode);
        if (route == null) {
            response.put("message", "Route not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Trip newTrip = new Trip();
        newTrip.setDepartureTime(departureTime);
        newTrip.setArrivalTime(arrivalTime);
        newTrip.setPrice(price);
        newTrip.setStatus(TripStatus.waiting);
        newTrip.setBus(existingBus);
        newTrip.setDriver(driver);
        newTrip.setController(controller);
        newTrip.setStaff(staff);
        newTrip.setRoute(route);
        tripService.save(newTrip);

        // for loop to add all the seats to the trip:
        List<Bus_Seats> seats = bus_SeatsService.getByBusPlate(existingBus);
        for (Bus_Seats seat : seats) {
            SeatReservations reservation = new SeatReservations();
            reservation.setTrip(newTrip);
            reservation.setSeat(seat);
            reservation.setStatus(SeatReservationStatus.open);
            seatReservationService.save(reservation);
        }
        response.put("message", "Trip saved successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/update-trip/{tripId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateTrip(@PathVariable int tripId,
            @RequestBody Map<String, Object> tripRequest) {
        Map<String, Object> response = new HashMap<>();
        Trip existingTrip = tripService.findTripById(tripId);
        if (existingTrip == null) {
            response.put("message", "Trip not found.");
            return ResponseEntity.badRequest().body(response);
        }

        String departureTimeStr = (String) tripRequest.get("departureTime");
        if (departureTimeStr == null || departureTimeStr.isEmpty()) {
            response.put("message", "Departure time is required.");
            return ResponseEntity.badRequest().body(response);
        }

        String arrivalTimeStr = (String) tripRequest.get("arrivalTime");
        if (arrivalTimeStr == null || arrivalTimeStr.isEmpty()) {
            response.put("message", "Arrival time is required.");
            return ResponseEntity.badRequest().body(response);
        }

        if (arrivalTimeStr.equals(departureTimeStr)) {
            response.put("message", "Arrival time can't be the same as departure time.");
            return ResponseEntity.badRequest().body(response);
        } else if (LocalDateTime.parse(arrivalTimeStr).isBefore(LocalDateTime.parse(departureTimeStr))) {
            response.put("message", "Arrival time can't be before departure time.");
            return ResponseEntity.badRequest().body(response);
        }

        float price = Float.parseFloat(tripRequest.get("price").toString());
        if (price < 0) {
            response.put("message", "Price can't be negative.");
            return ResponseEntity.badRequest().body(response);
        }

        String busPlate = (String) tripRequest.get("busPlate");
        if (busPlate == null || busPlate.isEmpty()) {
            response.put("message", "Bus plate is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int driverId = Integer.parseInt(tripRequest.get("driverId").toString());
        if (driverId <= 0) {
            response.put("message", "Driver ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int controllerId = Integer.parseInt(tripRequest.get("controllerId").toString());
        if (controllerId <= 0) {
            response.put("message", "Controller ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        int staffId = Integer.parseInt(tripRequest.get("staffId").toString());
        if (staffId <= 0) {
            response.put("message", "Staff ID is required.");
            return ResponseEntity.badRequest().body(response);
        }

        String routeCode = (String) tripRequest.get("routeCode");
        if (routeCode == null || routeCode.isEmpty()) {
            response.put("message", "Route code is required.");
            return ResponseEntity.badRequest().body(response);
        }

        String statusStr = (String) tripRequest.get("status");
        if (statusStr == null || statusStr.isEmpty()) {
            response.put("message", "Status is required.");
            return ResponseEntity.badRequest().body(response);
        }

        LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr);
        LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr);

        Bus existingBus = busService.getByBusPlate(busPlate);
        if (existingBus == null) {
            response.put("message", "Bus not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Driver driver = driverService.getDriverById(driverId);
        if (driver == null) {
            response.put("message", "Driver not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Controller controller = controllerService.getControllerById(controllerId);
        if (controller == null) {
            response.put("message", "Controller not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Staff staff = staffService.getStaffById(staffId);
        if (staff == null) {
            response.put("message", "Staff not found.");
            return ResponseEntity.badRequest().body(response);
        }

        Route route = routeService.getRouteByCode(routeCode);
        if (route == null) {
            response.put("message", "Route not found.");
            return ResponseEntity.badRequest().body(response);
        }

        TripStatus status = TripStatus.valueOf(statusStr.toLowerCase());

        boolean isStatusChanged = !existingTrip.getStatus().equals(status);
        boolean isBusChanged = !existingTrip.getBus().equals(existingBus);
        boolean isDriverChanged = !existingTrip.getDriver().equals(driver);
        boolean isControllerChanged = !existingTrip.getController().equals(controller);
        boolean isDepartureTimeChanged = !existingTrip.getDepartureTime().equals(departureTime);
        boolean isArrivalTimeChanged = !existingTrip.getArrivalTime().equals(arrivalTime);

        if (!isStatusChanged && !isBusChanged && !isDriverChanged && !isControllerChanged && !isDepartureTimeChanged &&
                !isArrivalTimeChanged && existingTrip.getPrice() == price && existingTrip.getRoute().equals(route)) {
            response.put("message", "Nothing has been changed.");
            return ResponseEntity.badRequest().body(response);
        }

        if (isBusChanged) {
            List<Trip> conflictingTripsByBus = tripService.findConflictingTripsByBus(busPlate,
                    existingTrip.getDepartureTime(),
                    existingTrip.getArrivalTime());
            if (!conflictingTripsByBus.isEmpty()) {
                response.put("message", "New bus is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (isDriverChanged) {
            List<Trip> conflictingTripsByDriver = tripService.findConflictingTripsByDriver(driverId,
                    existingTrip.getDepartureTime(),
                    existingTrip.getArrivalTime());
            if (!conflictingTripsByDriver.isEmpty()) {
                response.put("message", "Driver is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (isControllerChanged) {
            List<Trip> conflictingTripsByController = tripService.findConflictingTripsByController(controllerId,
                    existingTrip.getDepartureTime(), existingTrip.getArrivalTime());
            if (!conflictingTripsByController.isEmpty()) {
                response.put("message", "Controller is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (isDepartureTimeChanged || isArrivalTimeChanged) {
            List<Trip> conflictingTripsByBus = tripService.findConflictingTripsByBus(busPlate, departureTime,
                    arrivalTime);
            if (!conflictingTripsByBus.isEmpty()) {
                response.put("message", "Bus is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }

            List<Trip> conflictingTripsByDriver = tripService.findConflictingTripsByDriver(driverId, departureTime,
                    arrivalTime);
            if (!conflictingTripsByDriver.isEmpty()) {
                response.put("message", "Driver is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }

            List<Trip> conflictingTripsByController = tripService.findConflictingTripsByController(controllerId,
                    departureTime, arrivalTime);
            if (!conflictingTripsByController.isEmpty()) {
                response.put("message", "Controller is already assigned to another trip at the same time.");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (isBusChanged) {
            List<Bus_Seats> oldSeats = bus_SeatsService.getByBusPlate(existingTrip.getBus());
            for (Bus_Seats oldSeat : oldSeats) {
                SeatReservations reservations = seatReservationService.getReservationBySeatAndTrip(oldSeat,
                        existingTrip);
                seatReservationService.delete(reservations);
            }

            Bus existingBus1 = busService.getByBusPlate(busPlate);
            List<Bus_Seats> newSeats = bus_SeatsService.getByBusPlate(existingBus1);
            for (Bus_Seats newSeat : newSeats) {
                SeatReservations reservation = new SeatReservations();
                reservation.setTrip(existingTrip);
                reservation.setSeat(newSeat);
                reservation.setStatus(SeatReservationStatus.open);
                seatReservationService.save(reservation);
            }
        }

        existingTrip.setDepartureTime(departureTime);
        existingTrip.setArrivalTime(arrivalTime);
        existingTrip.setPrice(price);
        existingTrip.setStatus(status);
        existingTrip.setBus(existingBus);
        existingTrip.setDriver(driver);
        existingTrip.setController(controller);
        existingTrip.setStaff(staff);
        existingTrip.setRoute(route);

        tripService.save(existingTrip);

        response.put("message", "Trip updated successfully.");
        return ResponseEntity.ok(response);
    }

    // Load selected items into update-trip form
    @GetMapping("/admin/api/selected-trip/{tripId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSelectedTrip(@PathVariable int tripId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Trip trip = tripService.findTripById(tripId);
            if (trip == null) {
                response.put("errorMessage", "Trip not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Bus existingBus = busService.getByBusPlate(trip.getBus().getPlate());
            if (existingBus == null) {
                response.put("errorMessage", "Bus not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Driver driver = driverService.getDriverById(trip.getDriver().getDriverId());
            if (driver == null) {
                response.put("errorMessage", "Driver not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Controller controller = controllerService.getControllerById(trip.getController().getId());
            if (controller == null) {
                response.put("errorMessage", "Controller not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Staff staff = staffService.getStaffById(trip.getStaff().getStaffId());
            if (staff == null) {
                response.put("errorMessage", "Staff not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Route route = routeService.getRouteByCode(trip.getRoute().getCode());
            if (route == null) {
                response.put("errorMessage", "Route not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Map<String, Object> tripData = new HashMap<>();
            tripData.put("tripId", trip.getTripId());
            tripData.put("price", trip.getPrice());
            tripData.put("status", trip.getStatus().name());
            tripData.put("departureTime", trip.getDepartureTime());
            tripData.put("arrivalTime", trip.getArrivalTime());
            tripData.put("busPlate", trip.getBus().getPlate());
            tripData.put("driverId", trip.getDriver().getDriverId());
            tripData.put("controllerId", trip.getController().getId());
            tripData.put("staffId", trip.getStaff().getStaffId());
            tripData.put("routeCode", trip.getRoute().getCode());
            response.put("trip", tripData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/admin/api/delete-trip/{tripId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteTrip(@PathVariable int tripId) {
        Map<String, Object> response = new HashMap<>();
        Trip existingTrip = tripService.findTripById(tripId);
        if (existingTrip == null) {
            response.put("message", "Trip not found.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
        // Check for booking :
        List<Booking> bookings = bookingService.findByTrip(existingTrip);
        if (!bookings.isEmpty()) {
            response.put("message", "Trip has bookings and cannot be deleted.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }

        // Check for bill :
        List<Bill_Detail> bills = billDetailService.findByTrip(existingTrip);
        if (!bills.isEmpty()) {
            response.put("message", "Trip has billing and cannot be deleted.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }

        // if booking and bill is empty, proceed delete trip:
        List<SeatReservations> reservations = seatReservationService.getByTrip(existingTrip);
        for (SeatReservations reservation : reservations) {
            seatReservationService.delete(reservation);
        }

        tripService.deleteTripById(tripId);
        response.put("message", "Trip deleted successfully.");
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/api/force-delete-trip/{tripId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> forceDeleteTrip(@PathVariable int tripId) {
        Map<String, Object> response = new HashMap<>();
        Trip existingTrip = tripService.findTripById(tripId);
        if (existingTrip == null) {
            response.put("message", "Trip not found.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }

        // Delete all seat reservations associated with the trip
        List<SeatReservations> reservations = seatReservationService.getByTrip(existingTrip);
        if (!reservations.isEmpty()) {
            seatReservationService.deleteAll(reservations);
        }

        List<Booking> bookings = bookingService.findByTrip(existingTrip);
        if (!bookings.isEmpty()) {
            bookingService.deleteAll(bookings);
        }

        List<Bill_Detail> billDetails = billDetailService.findByTrip(existingTrip);
        for (Bill_Detail billDetail : billDetails) {
            Optional<Bill> bill = billService.findByBillId(billDetail.getBill());
            if (bill.isPresent()) {
                billService.delete(bill.get());
            }
        }

        tripService.deleteTripById(tripId);
        response.put("message", "Trip deleted successfully.");
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/api/bookings")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBookings(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPages = bookingService.getAll(pageable);

        List<BookingDTO> bookings = bookingPages.getContent().stream()
                .map(this::convertToBookingDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("bookings", bookings);
        response.put("totalPages", bookingPages.getTotalPages());
        response.put("currentPage", page);

        return ResponseEntity.ok(response);
    }

    private BookingDTO convertToBookingDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                new CustomerDTO(
                        booking.getCustomer().getCustomerId(),
                        booking.getCustomer().getEmail(),
                        booking.getCustomer().getName(),
                        booking.getCustomer().getPhone()),
                new TripDTO(
                        booking.getTrip().getTripId(),
                        booking.getTrip().getDepartureTime(),
                        booking.getTrip().getArrivalTime(),
                        booking.getTrip().getPrice(),
                        booking.getTrip().getStatus(),
                        new BusDTO(
                                booking.getTrip().getBus().getPlate(),
                                booking.getTrip().getBus().getNumberOfSeat(),
                                booking.getTrip().getBus().getSeatType()),
                        new DriverDTO(
                                booking.getTrip().getDriver().getDriverId(),
                                new AccountDTO(
                                        booking.getTrip().getDriver().getAccount().getId(),
                                        booking.getTrip().getDriver().getAccount().getEmail(),
                                        booking.getTrip().getDriver().getAccount().getPassword(),
                                        booking.getTrip().getDriver().getAccount().getFullName(),
                                        booking.getTrip().getDriver().getAccount().getPhone(),
                                        booking.getTrip().getDriver().getAccount().getRole(),
                                        booking.getTrip().getDriver().getAccount().getStatus(),
                                        booking.getTrip().getDriver().getAccount().getVerificationCode(),
                                        booking.getTrip().getDriver().getAccount().getVerificationExpiration(),
                                        booking.getTrip().getDriver().getAccount().getLoginToken(),
                                        booking.getTrip().getDriver().getAccount().getPasswordResetToken(),
                                        booking.getTrip().getDriver().getAccount().getPasswordResetExpiration(),
                                        booking.getTrip().getDriver().getAccount().isEnabled()),
                                booking.getTrip().getDriver().getStatus()),
                        new ControllerDTO(
                                booking.getTrip().getController().getId(),
                                new AccountDTO(
                                        booking.getTrip().getController().getAccount().getId(),
                                        booking.getTrip().getController().getAccount().getEmail(),
                                        booking.getTrip().getController().getAccount().getPassword(),
                                        booking.getTrip().getController().getAccount().getFullName(),
                                        booking.getTrip().getController().getAccount().getPhone(),
                                        booking.getTrip().getController().getAccount().getRole(),
                                        booking.getTrip().getController().getAccount().getStatus(),
                                        booking.getTrip().getController().getAccount().getVerificationCode(),
                                        booking.getTrip().getController().getAccount().getVerificationExpiration(),
                                        booking.getTrip().getController().getAccount().getLoginToken(),
                                        booking.getTrip().getController().getAccount().getPasswordResetToken(),
                                        booking.getTrip().getController().getAccount().getPasswordResetExpiration(),
                                        booking.getTrip().getController().getAccount().isEnabled()),
                                booking.getTrip().getController().getStatus()),
                        new StaffDTO(
                                booking.getTrip().getStaff().getStaffId(),
                                new AccountDTO(
                                        booking.getTrip().getStaff().getAccount().getId(),
                                        booking.getTrip().getStaff().getAccount().getEmail(),
                                        booking.getTrip().getStaff().getAccount().getPassword(),
                                        booking.getTrip().getStaff().getAccount().getFullName(),
                                        booking.getTrip().getStaff().getAccount().getPhone(),
                                        booking.getTrip().getStaff().getAccount().getRole(),
                                        booking.getTrip().getStaff().getAccount().getStatus(),
                                        booking.getTrip().getStaff().getAccount().getVerificationCode(),
                                        booking.getTrip().getStaff().getAccount().getVerificationExpiration(),
                                        booking.getTrip().getStaff().getAccount().getLoginToken(),
                                        booking.getTrip().getStaff().getAccount().getPasswordResetToken(),
                                        booking.getTrip().getStaff().getAccount().getPasswordResetExpiration(),
                                        booking.getTrip().getStaff().getAccount().isEnabled()),
                                booking.getTrip().getStaff().getStatus()),
                        new RouteDTO(
                                booking.getTrip().getRoute().getRouteId(),
                                booking.getTrip().getRoute().getCode(),
                                booking.getTrip().getRoute().getName(),
                                booking.getTrip().getRoute().getTime(),
                                booking.getTrip().getRoute().getDistance())),
                booking.getNumberOfSeat(),
                booking.isRoundTrip(),
                booking.getRoundTripId());
    }
}