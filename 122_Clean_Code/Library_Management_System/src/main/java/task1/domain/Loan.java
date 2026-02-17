package task1.domain;

import java.time.LocalDate;

public class Loan {
    private final String bookId;
    private final String userId;
    private final LocalDate dueDate;

    public Loan(String bookId, String userId, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.dueDate = dueDate;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }
}
