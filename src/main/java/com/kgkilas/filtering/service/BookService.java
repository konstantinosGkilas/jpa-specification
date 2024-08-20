package com.kgkilas.filtering.service;

import com.kgkilas.filtering.criteria.BookCriteria;
import com.kgkilas.filtering.domain.Book;
import com.kgkilas.filtering.repository.BookRepository;
import com.kgkilas.filtering.specification.BookSpecification;
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
