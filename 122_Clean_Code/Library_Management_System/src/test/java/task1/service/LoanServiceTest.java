package task1.service;

import org.junit.jupiter.api.Test;
import task1.domain.Book;
import task1.domain.Loan;
import task1.event.BookReturnEventPublisher;
import task1.exception.BookNotFoundException;
import task1.repository.*;
import task1.strategy.DailyFineStrategy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    @Test
    void shouldThrowExceptionWhenBookDoesNotExist() {
        BookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();

        BookReturnEventPublisher publisher = new BookReturnEventPublisher();

        LoanService service = new LoanService(
                loanRepo,
                bookRepo,
                new DailyFineStrategy(1.5),
                publisher
        );

        assertThrows(BookNotFoundException.class,
                () -> service.checkOutBook("INVALID", "USR1"));
    }

    @Test
    void shouldAllowReservationWhenBookIsLoaned() {
        InMemoryBookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();
        ReservationRepository reservationRepo =
                new InMemoryReservationRepository();

        BookReturnEventPublisher publisher = new BookReturnEventPublisher();

        // Register observer (important!)
        publisher.register(
                new ReservationNotificationObserver(reservationRepo)
        );

        LoanService loanService = new LoanService(
                loanRepo,
                bookRepo,
                new DailyFineStrategy(1.5),
                publisher
        );

        ReservationService reservationService =
                new ReservationService(loanRepo, reservationRepo);

        bookRepo.save(new Book("BK1", "DDD"));

        loanService.checkOutBook("BK1", "USR1");

        assertDoesNotThrow(() ->
                reservationService.reserveBook("BK1", "USR2"));
    }

    @Test
    void shouldNotifyObserversWhenBookIsReturned() {
        InMemoryBookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();

        BookReturnEventPublisher publisher = new BookReturnEventPublisher();
        TestObserver observer = new TestObserver();

        publisher.register(observer);

        LoanService service = new LoanService(
                loanRepo,
                bookRepo,
                new DailyFineStrategy(1.5),
                publisher
        );

        bookRepo.save(new Book("BK2", "Clean Code"));

        service.checkOutBook("BK2", "USR1");
        double fine = service.returnBook("BK2");

        assertTrue(observer.notified);
    }

    @Test
    void shouldCalculateFineForOverdueBook() {
        InMemoryBookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();
        BookReturnEventPublisher publisher = new BookReturnEventPublisher();

        LoanService service = new LoanService(
                loanRepo,
                bookRepo,
                new DailyFineStrategy(2), // example: $2/day
                publisher
        );

        bookRepo.save(new Book("BK3", "Effective Java"));

        Loan overdueLoan = new Loan(
                "BK3",
                "USR1",
                LocalDate.now().minusDays(5)
        );
        loanRepo.save(overdueLoan);

        double fine = service.returnBook("BK3");

        assertEquals(10.0, fine);
    }

    @Test
    void fullLoanReservationReturnFlow() {
        InMemoryBookRepository bookRepo = new InMemoryBookRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();
        InMemoryReservationRepository reservationRepo = new InMemoryReservationRepository();

        BookReturnEventPublisher publisher = new BookReturnEventPublisher();
        TestObserver observer = new TestObserver();

        publisher.register(observer);
        publisher.register(new ReservationNotificationObserver(reservationRepo));

        LoanService loanService = new LoanService(
                loanRepo,
                bookRepo,
                new DailyFineStrategy(1.5),
                publisher
        );

        ReservationService reservationService =
                new ReservationService(loanRepo, reservationRepo);

        bookRepo.save(new Book("BK4", "Refactoring"));

        loanService.checkOutBook("BK4", "USR1");
        reservationService.reserveBook("BK4", "USR2");

        loanService.returnBook("BK4");

        assertTrue(observer.notified);
    }
}