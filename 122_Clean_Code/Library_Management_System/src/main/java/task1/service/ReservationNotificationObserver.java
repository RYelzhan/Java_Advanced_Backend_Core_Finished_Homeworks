package task1.service;

import task1.event.BookReturnedEvent;
import task1.event.BookReturnedObserver;
import task1.repository.ReservationRepository;

public class ReservationNotificationObserver implements BookReturnedObserver {

    private final ReservationRepository reservationRepository;

    public ReservationNotificationObserver(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onBookReturned(BookReturnedEvent event) {
        reservationRepository.pollNextReservation(event.bookId())
                .ifPresent(reservation ->
                        System.out.println("Notify user " + reservation.getUserId())
                );
    }
}