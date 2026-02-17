package task1.repository;

import task1.domain.Loan;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLoanRepository implements LoanRepository {

    private final ConcurrentHashMap<String, Loan> loans = new ConcurrentHashMap<>();

    @Override
    public Optional<Loan> findByBookId(String bookId) {
        return Optional.ofNullable(loans.get(bookId));
    }

    @Override
    public void save(Loan loan) {
        loans.put(loan.getBookId(), loan);
    }

    @Override
    public void delete(String bookId) {
        loans.remove(bookId);
    }

    @Override
    public boolean saveIfAbsent(Loan loan) {
        return loans.putIfAbsent(loan.getBookId(), loan) == null;
    }
}
