package com.myproject.busticket.controllers;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/admin")
@Controller
public class AdminPageController {
    TripService tripService;
    BusService busService;
    RouteService routeService;
    CheckpointService checkpointService;

    public AdminPageController(TripService tripService, BusService busService, RouteService routeService,
            CheckpointService checkpointService) {
        this.busService = busService;
        this.tripService = tripService;
        this.routeService = routeService;
        this.checkpointService = checkpointService;
    }

    @GetMapping("/dashboard")
    public String dashBoardPage() {
        return "admin";
    }

    @GetMapping("/trip-management")
    public String adminTripPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> tripsPage = tripService.getAll(pageable);

        int totalPages = tripsPage.getTotalPages();

        // Tính toán startPage và endPage để giới hạn chỉ 5 trang
        int startPage = Math.max(0, page - 2); // Hiển thị từ 2 trang trước
        int endPage = Math.min(totalPages - 1, page + 2); // Hiển thị đến 2 trang sau

        // Tạo danh sách các trang sẽ hiển thị
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("trips", tripsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tripsPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); // Danh sách các trang hiển thị

        return "trip-management";
    }

    @GetMapping("/bus-management")
    public String adminBusPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busesPage = busService.getAll(pageable);

        int totalPages = busesPage.getTotalPages();

        // Tính toán startPage và endPage để giới hạn chỉ 5 trang
        int startPage = Math.max(0, page - 2); // Hiển thị từ 2 trang trước
        int endPage = Math.min(totalPages - 1, page + 2); // Hiển thị đến 2 trang sau

        // Tạo danh sách các trang sẽ hiển thị
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("buses", busesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", busesPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); // Danh sách các trang hiển thị

        return "bus-management";
    }

    @GetMapping("/route-management")
    public String adminRoutePage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Route> routesPage = routeService.getAll(pageable);

        int totalPages = routesPage.getTotalPages();

        int startPage = Math.max(0, page - 2);
        int endPage = Math.min(totalPages - 1, page + 2);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("routes", routesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", routesPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);

        return "route-management";
    }

    @GetMapping("/checkpoint-management")
    public String adminCheckpointPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Checkpoint> checkpointPages = checkpointService.getAll(pageable);

        int totalPages = checkpointPages.getTotalPages();

        int startPage = Math.max(0, page - 2);
        int endPage = Math.min(totalPages - 1, page + 2);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("checkpoints", checkpointPages.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", checkpointPages.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);

        return "checkpoint-management";
    }
}
