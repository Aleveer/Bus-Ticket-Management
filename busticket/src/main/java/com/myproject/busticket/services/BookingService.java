package com.myproject.busticket.services;

import com.myproject.busticket.dto.BookingInfoDTO;
import com.myproject.busticket.enums.PaymentMethod;
import com.myproject.busticket.enums.TicketType;
import com.myproject.busticket.models.*;
import com.myproject.busticket.repositories.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.myproject.busticket.utilities.SecurityUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private TripService tripService;
    private AccountService accountService;
    private CustomerService customerService;
    private SeatReservationsService seatReservationsService;
    private BillService billService;
    private BillDetailService billDetailService;

    private EmailService emailService;


    public BookingService(BookingRepository bookingRepository,
                          TripService tripService,
                          AccountService accountService,
                          CustomerService customerService,
                          SeatReservationsService seatReservationsService,
                          BillService billService,
                          BillDetailService billDetailService,
                          EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.tripService = tripService;
        this.accountService = accountService;
        this.customerService = customerService;
        this.seatReservationsService = seatReservationsService;
        this.billService = billService;
        this.billDetailService = billDetailService;
        this.emailService = emailService;
    }

    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }


    public boolean checkLogin(){
        Account account = SecurityUtil.getCurrentAccount();
        return account != null;
    }
    public void createTicketOneWay(BookingInfoDTO booking, LocalDateTime paymentDate) {
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
        Bill bill = new Bill();
        bill.setCustomer(newCustomer);
        bill.setPaymentMethod(PaymentMethod.vnpay);
        bill.setPaymentDate(paymentDate);
        billService.save(bill);

        // lưu chi tiết hóa đơn
        Bill_Detail billDetail = new Bill_Detail();
        billDetail.setBill(bill);
        billDetail.setTrip(newBooking.getTrip());
        billDetail.setNumberOfTicket(numberOfSeat);
        billDetail.setFee(newBooking.getTrip().getPrice());
        billDetail.setTicketType(TicketType.one_way_ticket);
        billDetailService.save(billDetail);

        //send Email
        Context context = new Context();
        String subject = "[EASYBUS] HÓA ĐƠN ĐIỆN TỬ CỦA VÉ SỐ" + newBooking.getBookingId();
        context.setVariable("routeName", newBooking.getTrip().getRoute().getName());
        context.setVariable("departureTime", newBooking.getTrip().getDepartureTime());
        context.setVariable("departureTime", newBooking.getTrip().getDepartureTime());
        context.setVariable("numberOfSeat", numberOfSeat);
        context.setVariable("listSeat", booking.getTicketInfoDTO().getSeatNumbers());
        context.setVariable("totalFee", billDetail.getFee());
        try {
            emailService.sendBookingEmail(email, subject, "email-template", context);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

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
