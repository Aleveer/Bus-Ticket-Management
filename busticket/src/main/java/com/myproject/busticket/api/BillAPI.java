package com.myproject.busticket.api;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.dto.BillDTO;
import com.myproject.busticket.dto.BillDetailDTO;
import com.myproject.busticket.mapper.BillMapper;
import com.myproject.busticket.mapper.TripMapper;
import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Trip;
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

    @Autowired
    private TripMapper tripMapper;

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
