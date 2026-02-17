package task1.domain;

import java.time.LocalDateTime;

/**
 * Represents a reservation made by a user for a book that is currently loaned out.
 * Reservations are handled on a first-come, first-served basis.
 */
public class Reservation {

    private final String bookId;
    private final String userId;
    private final LocalDateTime reservedAt;

    public Reservation(String bookId, String userId) {
        if (bookId == null || userId == null) {
            throw new IllegalArgumentException("bookId and userId must not be null");
        }
        this.bookId = bookId;
        this.userId = userId;
        this.reservedAt = LocalDateTime.now();
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}