package com.gkilas.filtering.rest.service;

import com.gkilas.filtering.rest.criteria.BookCriteria;
import com.gkilas.filtering.rest.domain.Book;
import com.gkilas.filtering.rest.repository.BookRepository;
import com.gkilas.filtering.rest.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create a new book
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }


    // Get all books
    public List<Book> getAllBooks(BookCriteria bookCriteria) {
        Specification<Book> specification = BookSpecification.buildBookSpecification(bookCriteria);
        return bookRepository.findAll(specification);
    }
}
