package task1.repository;

import task1.domain.Loan;

import java.util.Optional;

public interface LoanRepository {
    Optional<Loan> findByBookId(String bookId);
    void save(Loan loan);
    void delete(String bookId);
    boolean saveIfAbsent(Loan loan);
}
