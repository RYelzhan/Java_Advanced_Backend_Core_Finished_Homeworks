package task1.repository;

import task1.domain.Book;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepository implements BookRepository {

    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Optional<Book> findById(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public void save(Book book) {
        books.put(book.getBookId(), book);
    }
}
