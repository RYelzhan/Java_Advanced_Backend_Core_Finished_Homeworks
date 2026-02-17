package task1.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookReturnEventPublisher {

    private final List<BookReturnedObserver> observers = new CopyOnWriteArrayList<>();

    public void register(BookReturnedObserver observer) {
        observers.add(observer);
    }

    public void publish(BookReturnedEvent event) {
        observers.forEach(o -> o.onBookReturned(event));
    }
}