package task1.repository;

import task1.domain.Reservation;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryReservationRepository implements ReservationRepository {

    private final ConcurrentHashMap<String, Queue<Reservation>> reservations =
            new ConcurrentHashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservations
                .computeIfAbsent(reservation.getBookId(),
                        k -> new ConcurrentLinkedQueue<>())
                .add(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
        Queue<Reservation> queue = reservations.get(reservation.getBookId());
        if (queue != null) {
            queue.remove(reservation);
        }
    }

    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    public Optional<Reservation> pollNextReservation(String bookId) {
        Queue<Reservation> queue = reservations.get(bookId);
        return queue == null
                ? Optional.empty()
                : Optional.ofNullable(queue.poll());
    }
}