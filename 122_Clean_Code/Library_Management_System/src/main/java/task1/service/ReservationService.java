package task1.service;

import task1.domain.Reservation;
import task1.repository.LoanRepository;
import task1.repository.ReservationRepository;

/**
 * Service responsible for handling book reservations.
 * <p>
 * Allows users to reserve books that are currently loaned out.
 */
public class ReservationService {

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(LoanRepository loanRepository,
                              ReservationRepository reservationRepository) {
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserveBook(String bookId, String userId) {
        if (loanRepository.findByBookId(bookId).isEmpty()) {
            throw new IllegalStateException("Book is available; reservation not required");
        }

        reservationRepository.save(new Reservation(bookId, userId));
    }
}
