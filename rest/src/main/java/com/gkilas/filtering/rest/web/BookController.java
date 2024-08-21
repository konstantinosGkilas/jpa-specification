package com.gkilas.filtering.rest.web;

import com.gkilas.filtering.rest.criteria.BookCriteria;
import com.gkilas.filtering.rest.domain.Book;
import com.gkilas.filtering.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(BookCriteria bookCriteria) {
        List<Book> books = bookService.getAllBooks(bookCriteria);
        return ResponseEntity.ok(books);
    }
}
