package task1.event;

/**
 * Event published when a book is returned.
 */
public record BookReturnedEvent(String bookId) {}