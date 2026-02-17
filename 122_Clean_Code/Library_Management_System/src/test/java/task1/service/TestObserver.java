package task1.service;

import task1.event.BookReturnedEvent;
import task1.event.BookReturnedObserver;

class TestObserver implements BookReturnedObserver {

    boolean notified = false;

    @Override
    public void onBookReturned(BookReturnedEvent event) {
        notified = true;
    }
}
