package task1;

import task1.event.BookReturnEventPublisher;
import task1.factory.RepositoryFactory;
import task1.repository.BookRepository;
import task1.repository.LoanRepository;
import task1.repository.ReservationRepository;
import task1.service.LoanService;
import task1.service.ReservationNotificationObserver;
import task1.strategy.DailyFineStrategy;

public class LibraryApplication {

    public static void main(String[] args) {
        BookReturnEventPublisher publisher = new BookReturnEventPublisher();

        ReservationRepository reservationRepository = RepositoryFactory.createReservationRepository();
        publisher.register(
                new ReservationNotificationObserver(reservationRepository)
        );

        LoanRepository loanRepository = RepositoryFactory.createLoanRepository();
        BookRepository bookRepository = RepositoryFactory.createBookRepository();
        DailyFineStrategy fineStrategy = new DailyFineStrategy(1.5);
        LoanService loanService = new LoanService(
                loanRepository,
                bookRepository,
                fineStrategy,
                publisher
        );

        loanService.checkOutBook("BK001", "USR001");
        loanService.returnBook("BK001");
    }
}

