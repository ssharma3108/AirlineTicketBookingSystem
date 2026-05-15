package com.example.bookingService.service;

import com.example.bookingService.client.*;
import com.example.bookingService.dto.*;
import com.example.bookingService.entity.*;
import com.example.bookingService.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock BookingRepository bookingRepository;
    @Mock PaymentClient paymentClient;
    @Mock SeatClient seatClient;
    @Mock FlightClient flightClient;
    @InjectMocks BookingServiceImpl service;

    @Test
    void createBooking_success() {
        BookingRequestDTO req = new BookingRequestDTO();
        req.setUserId(1L); req.setFlightId(10L); req.setSeatNumber("1A");
        req.setSeatClass("NORMAL"); req.setTripType("ONE_WAY"); req.setMealPreference("NO_MEAL");

        Booking saved = new Booking();
        saved.setBookingId(UUID.randomUUID()); saved.setPnrCode("ABC123");
        saved.setStatus(BookingStatus.PENDING); saved.setTotalFare(5000.0); saved.setSeatNumber("1A");
        when(bookingRepository.save(any())).thenReturn(saved);

        BookingResponseDTO res = service.createBooking(req);

        assertEquals("PENDING", res.getStatus());
        verify(seatClient).holdSeat(10L, "1A", "1");
        verify(flightClient).decrementSeat(10L, 1);
    }

    @Test
    void completePayment_paid_setsConfirmed() {
        UUID paymentId = UUID.randomUUID();
        UUID bookingId = UUID.randomUUID();

        PaymentResponseDTO pay = new PaymentResponseDTO();
        pay.setBookingId(bookingId); pay.setStatus("PAID");
        when(paymentClient.process(paymentId, "txn-1", "PAID")).thenReturn(pay);

        Booking b = new Booking();
        b.setBookingId(bookingId); b.setFlightId(10L); b.setSeatNumber("1A");
        b.setStatus(BookingStatus.PENDING);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(b));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        BookingResponseDTO res = service.completePayment(paymentId, "txn-1", "PAID");
        assertEquals("CONFIRMED", res.getStatus());
        verify(seatClient).lockSeat(10L, "1A");
    }
}
