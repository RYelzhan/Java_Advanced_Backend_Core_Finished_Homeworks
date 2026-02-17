package task1.event;

public interface BookReturnedObserver {
    void onBookReturned(BookReturnedEvent event);
}