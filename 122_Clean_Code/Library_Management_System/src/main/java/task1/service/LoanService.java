package task1.service;

import task1.domain.Loan;
import task1.event.BookReturnEventPublisher;
import task1.event.BookReturnedEvent;
import task1.exception.BookAlreadyLoanedException;
import task1.exception.BookNotFoundException;
import task1.repository.BookRepository;
import task1.repository.LoanRepository;
import task1.strategy.FineCalculationStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Service responsible for handling book loans.
 * <p>
 * Validates book existence, prevents double-loaning,
 * and calculates overdue fines.
 */
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final FineCalculationStrategy fineStrategy;
    private final BookReturnEventPublisher eventPublisher;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       FineCalculationStrategy fineStrategy,
                       BookReturnEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.fineStrategy = fineStrategy;
        this.eventPublisher = eventPublisher;
    }

    public void checkOutBook(String bookId, String userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException("Book does not exist: " + bookId));

        Loan loan = new Loan(bookId, userId, LocalDate.now().plusDays(14));

        if (!loanRepository.saveIfAbsent(loan)) {
            throw new BookAlreadyLoanedException("Book is already loaned out");
        }
        loanRepository.save(loan);
    }

    public double returnBook(String bookId) {
        Loan loan = loanRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not loaned"));

        loanRepository.delete(bookId);

        eventPublisher.publish(new BookReturnedEvent(bookId));

        return calculateFineIfNeeded(loan);
    }

    private double calculateFineIfNeeded(Loan loan) {
        if (!loan.isOverdue()) {
            return 0.0;
        }
        long days = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        return fineStrategy.calculateFine(days);
    }
}
