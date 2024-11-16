package com.myproject.busticket.controllers;

import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Checkpoint;
import com.myproject.busticket.models.Route;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.CheckpointService;
import com.myproject.busticket.services.ControllerService;
import com.myproject.busticket.services.DriverService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.RouteService;
import com.myproject.busticket.services.StaffService;
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
	DriverService driverService;
	ControllerService controllerService;
	StaffService staffService;
	RouteService routeService;
	CheckpointService checkpointService;
	AccountService accountService;
	RoleService roleService;
	RouteCheckpointService routeCheckpointService;

	public AdminPageController(TripService tripService, BusService busService, RouteService routeService,
			CheckpointService checkpointService, AccountService accountService, RoleService roleService,
			RouteCheckpointService routeCheckpointService) {
		this.busService = busService;
		this.tripService = tripService;
		this.routeService = routeService;
		this.checkpointService = checkpointService;
		this.accountService = accountService;
		this.roleService = roleService;
		this.routeCheckpointService = routeCheckpointService;
	}

	@GetMapping("/dashboard")
	public String dashBoardPage() {
		return "admin";
	}

	@GetMapping("/trip-management")
	public String adminTripPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Trip> tripsPage = tripService.getAll(pageable);

		int totalPages = tripsPage.getTotalPages();
		int startPage = Math.max(0, page - 2);
		int endPage = Math.min(totalPages - 1, page + 2);

		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
				.boxed()
				.collect(Collectors.toList());

		model.addAttribute("trips", tripsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", tripsPage.getTotalPages());
		model.addAttribute("pageNumbers", pageNumbers);

		return "trip-management";
	}

	@GetMapping("/bus-management")
	public String adminBusPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Bus> busesPage = busService.getAll(pageable);

		int totalPages = busesPage.getTotalPages();
		int startPage = Math.max(0, page - 2);
		int endPage = Math.min(totalPages - 1, page + 2);

		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
				.boxed()
				.collect(Collectors.toList());

		model.addAttribute("buses", busesPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", busesPage.getTotalPages());
		model.addAttribute("pageNumbers", pageNumbers);

		return "bus-management";
	}

	@GetMapping("/route-management")
	public String adminRoutePage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, Model model) {
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
			@RequestParam(defaultValue = "15") int size, Model model) {
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

	@GetMapping("/account-management")
	public String adminUserPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size, Model model) {
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

		int totalPages = accountPages.getTotalPages();
		int startPage = Math.max(0, page - 2);
		int endPage = Math.min(totalPages - 1, page + 2);

		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
				.boxed()
				.collect(Collectors.toList());

		model.addAttribute("accounts", accountDTOs);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNumbers", pageNumbers);

		return "account-management";
	}

}
