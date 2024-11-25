package com.myproject.busticket.services;

import com.myproject.busticket.dto.BookingInfoDTO;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

import com.myproject.busticket.utilities.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private TripService tripService;
    private AccountService accountService;
    private CustomerService customerService;
    private SeatReservationsService seatReservationsService;


    public BookingService(BookingRepository bookingRepository, TripService tripService, AccountService accountService, CustomerService customerService, SeatReservationsService seatReservationsService) {
        this.bookingRepository = bookingRepository;
        this.tripService = tripService;
        this.accountService = accountService;
        this.customerService = customerService;
        this.seatReservationsService = seatReservationsService;
    }

    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }


    public boolean checkLogin(){
        Account account = SecurityUtil.getCurrentAccount();
        return account != null;
    }
    public void createTicketOneWay(BookingInfoDTO booking) {
        Booking newBooking = new Booking();

        // xử lý thông tin khách hàng
        String email = booking.getCustomer().getEmail();
//        if (checkLogin()) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Account currentAccount = (Account) authentication.getPrincipal();
//            newBooking.setCustomer(customerService.getCustomerByEmail(currentAccount.getEmail()));
//        } else {
//            if (accountService.existsByEmail(email)){
//                newBooking.setCustomer(customerService.getCustomerByEmail(email));
//            } else if (customerService.existsByEmail(email)) {
//                newBooking.setCustomer(customerService.getCustomerByEmail(email));
//            } else {
//                Customer newCustomer = new Customer();
//                newCustomer.setEmail(email);
//                newCustomer.setName(booking.getCustomer().getName());
//                newCustomer.setPhone(booking.getCustomer().getPhone());
//                customerService.create(newCustomer);
//                newBooking.setCustomer(newCustomer);
//            }
//        }
        Customer newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setName(booking.getCustomer().getName());
        newCustomer.setPhone(booking.getCustomer().getPhone());
        customerService.create(newCustomer);
        newBooking.setCustomer(newCustomer);

        // lưu vé
        int tripId = booking.getTicketInfoDTO().getTripId();
        newBooking.setTrip(tripService.findTripById(tripId));

        byte numberOfSeat = booking.getTicketInfoDTO().getNumberOfSeat();
        newBooking.setNumberOfSeat(numberOfSeat);
        newBooking.setRoundTrip(false);
        newBooking.setRoundTripId(null);
        bookingRepository.save(newBooking);

        // đổi trạng thái số ghế
        List<Integer> listSeatId = booking.getTicketInfoDTO().getSeatNumbers().stream()
                .map(Integer::valueOf)
                .toList();
        int bookingID = newBooking.getBookingId();
        seatReservationsService.updateStatusWithBooking(listSeatId, bookingID);

        // lưu hóa đơn

        // lưu chi tiết hóa đơn
    }

    public List<Booking> findByTrip(Trip trip) {
        return bookingRepository.findByTrip(trip);
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public void deleteAll(List<Booking> bookings) {
        bookingRepository.deleteAll(bookings);
    }

    public Page<Booking> getAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByRoundTripId(String roundTripId) {
        return bookingRepository.findByRoundTripId(roundTripId);
    }

    public List<Booking> findBookingDetailsByCustomerId(int customerId) {
        return bookingRepository.findBookingDetailsByCustomerId(customerId);
    }

    public List<Booking> findByCustomer(Customer customer) {
        return bookingRepository.findByCustomer(customer);
    }

}
