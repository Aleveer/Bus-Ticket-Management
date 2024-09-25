package com.myproject.busticket.components;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.myproject.busticket.models.Role;
import com.myproject.busticket.services.BookingDetailService;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.DiscountService;
import com.myproject.busticket.services.DriverBusAssignmentService;
import com.myproject.busticket.services.FeedbackService;
import com.myproject.busticket.services.LocationService;
import com.myproject.busticket.services.NotificationService;
import com.myproject.busticket.services.PaymentService;
import com.myproject.busticket.services.PermissionService;
import com.myproject.busticket.services.RolePermissionService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.ScheduleCheckpointService;
import com.myproject.busticket.services.ScheduleService;
import com.myproject.busticket.services.SeatService;
import com.myproject.busticket.services.TransactionHistoryService;
import com.myproject.busticket.services.TripHistoryService;
import com.myproject.busticket.services.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private BusService busService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DriverBusAssignmentService driverBusService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleCheckpointService scheduleCheckpointService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private TripHistoryService tripHistoryService;

    @Autowired
    private UserService userService;

    private static final int SEED_COUNT = 10;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final String[] roles = { "Admin", "Customer", "Driver", "Employee" };

    public void seed() {

        // Seed roles table using Faker:
        for (String role : roles) {
            Role newRole = new Role();
            newRole.setName(role);
            roleService.save(newRole);
        }

    }
}