package task1.repository;

import task1.domain.Book;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String bookId);
}
