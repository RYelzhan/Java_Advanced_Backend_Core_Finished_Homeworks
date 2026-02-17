package task1.factory;

import task1.domain.Book;
import task1.repository.*;

public class RepositoryFactory {
    public static LoanRepository createLoanRepository() {
        return new InMemoryLoanRepository();
    }

    public static BookRepository createBookRepository() {
        InMemoryBookRepository bookRepo = new InMemoryBookRepository();
        bookRepo.save(new Book("BK001", "Clean Code"));

        return  bookRepo;
    }

    public static ReservationRepository createReservationRepository() {
        return new InMemoryReservationRepository();
    }
}
