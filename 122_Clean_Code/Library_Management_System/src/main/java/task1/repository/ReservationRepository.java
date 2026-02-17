package task1.repository;

import task1.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    void save(Reservation reservation);

    Optional<Reservation> pollNextReservation(String bookId);

    void remove(Reservation reservation);


}