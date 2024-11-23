package com.myproject.busticket.api;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.dto.BillDTO;
import com.myproject.busticket.enums.PaymentMethod;
import com.myproject.busticket.enums.TicketType;
import com.myproject.busticket.mapper.BillMapper;
import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.services.BillDetailService;
import com.myproject.busticket.services.BillService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.TripService;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {
    @Autowired
    private BillService billService;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private TripService tripService;

    // @Autowired
    // private TripMapper tripMapper;

    @GetMapping("/bills")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBills(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bill> billPages = billService.getAll(pageable);

        List<BillDTO> bills = billMapper.map(billPages.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("bills", bills);
        response.put("currentPage", page);
        response.put("totalPages", billPages.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new-bill")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveBill(@RequestBody Map<String, Object> billRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            int customerId = Integer.parseInt(billRequest.get("customerId").toString());
            int tripId = Integer.parseInt(billRequest.get("tripId").toString());
            int numberOfTickets = Integer.parseInt(billRequest.get("numberOfTickets").toString());
            String ticketType = billRequest.get("ticketType").toString();
            String paymentMethod = billRequest.get("paymentMethod").toString();
            float fee = Float.parseFloat(billRequest.get("fee").toString());
            Integer roundTripId = billRequest.containsKey("roundTripId")
                    ? Integer.parseInt(billRequest.get("roundTripId").toString())
                    : null;

            validateRequest(customerId, tripId, numberOfTickets, ticketType, paymentMethod, fee, roundTripId, response);

            Bill bill = createBill(customerId, paymentMethod);
            createBillDetails(bill, tripId, roundTripId, numberOfTickets, fee, ticketType);

            response.put("message", "Bill created successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("errorMessage", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void validateRequest(int customerId, int tripId, int numberOfTickets, String ticketType,
            String paymentMethod, float fee, Integer roundTripId, Map<String, Object> response) {
        if (customerService.getCustomerById(customerId) == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        if (tripService.findTripById(tripId) == null) {
            throw new IllegalArgumentException("Trip not found.");
        }
        if (numberOfTickets <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than 0.");
        }
        if (!ticketType.equals("one_way_ticket") && !ticketType.equals("round_trip_ticket")) {
            throw new IllegalArgumentException("Ticket type must be one-way or round-trip.");
        }
        if (!paymentMethod.equals("cash") && !paymentMethod.equals("vnpay")) {
            throw new IllegalArgumentException("Payment method must be cash or credit card.");
        }
        if (fee <= 0) {
            throw new IllegalArgumentException("Fee must be greater than 0.");
        }
        if (ticketType.equals("round_trip_ticket") && roundTripId == null) {
            throw new IllegalArgumentException("Round trip ID must be provided for round-trip tickets.");
        }

        if (roundTripId != null && roundTripId == tripId) {
            throw new IllegalArgumentException("Round trip ID must be different from trip ID.");
        }
        if (roundTripId != null) {
            if (tripService.findTripById(tripId).getDepartureTime()
                    .isAfter(tripService.findTripById(roundTripId).getDepartureTime())) {
                throw new IllegalArgumentException(
                        "Departure time of round trip must be after departure time of trip.");
            }
            if (tripService.findTripById(tripId).getArrivalTime()
                    .isAfter(tripService.findTripById(roundTripId).getArrivalTime())) {
                throw new IllegalArgumentException("Arrival time of round trip must be after arrival time of trip.");
            }
        }
    }

    private Bill createBill(int customerId, String paymentMethod) {
        Bill bill = new Bill();
        bill.setCustomer(customerService.getCustomerById(customerId));
        bill.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        bill.setPaymentDate(LocalDateTime.now());
        billService.save(bill);
        return bill;
    }

    private void createBillDetails(Bill bill, int tripId, Integer roundTripId, int numberOfTickets, float fee,
            String ticketType) {
        Bill_Detail billDetail = new Bill_Detail();
        billDetail.setBill(bill);
        billDetail.setTrip(tripService.findTripById(tripId));
        billDetail.setNumberOfTicket(numberOfTickets);
        billDetail.setFee(fee);
        billDetail.setTicketType(TicketType.one_way_ticket);
        billDetailService.save(billDetail);

        if (ticketType.equals("round_trip_ticket") && roundTripId != null) {
            Bill_Detail roundTripBillDetail = new Bill_Detail();
            roundTripBillDetail.setBill(bill);
            roundTripBillDetail.setTrip(tripService.findTripById(roundTripId));
            roundTripBillDetail.setNumberOfTicket(numberOfTickets);
            roundTripBillDetail.setFee(fee);
            roundTripBillDetail.setTicketType(TicketType.round_trip_ticket);
            billDetailService.save(roundTripBillDetail);
        }
    }
    // @GetMapping("/bill-detail/{billId}")
    // @ResponseBody
    // public ResponseEntity<Map<String, Object>> getBillDetails(@PathVariable int
    // billId) {
    // Map<String, Object> response = new HashMap<>();

    // Bill bill = billService.findById(billId);
    // if (bill == null) {
    // response.put("errorMessage", "Bill not found.");
    // return ResponseEntity.badRequest().body(response);
    // }

    // Customer customer =
    // customerService.getCustomerById(bill.getCustomer().getCustomerId());
    // if (customer == null) {
    // response.put("errorMessage", "Customer not found.");
    // return ResponseEntity.badRequest().body(response);
    // }

    // List<Bill_Detail> billDetails = billDetailService.findByBillId(bill);
    // if (billDetails == null || billDetails.isEmpty()) {
    // response.put("errorMessage", "Bill details not found.");
    // return ResponseEntity.badRequest().body(response);
    // }

    // Trip trip =
    // tripService.findTripById(billDetails.get(0).getTrip().getTripId());
    // if (trip == null) {
    // response.put("errorMessage", "Trip not found.");
    // return ResponseEntity.badRequest().body(response);
    // }

    // List<BillDetailDTO> billDetailsDTO = billDetails.stream()
    // .map(billDetail -> new BillDetailDTO(
    // billDetail.getId(),
    // billMapper.entityToDTO(billDetail.getBill()),
    // tripMapper.entityToDTO(billDetail.getTrip()),
    // billDetail.getNumberOfTicket(),
    // billDetail.getFee(),
    // billDetail.getTicketType()))
    // .collect(Collectors.toList());

    // response.put("bill", billDetailsDTO);
    // response.put("customer", customer);

    // return ResponseEntity.ok(response);
    // }

}
