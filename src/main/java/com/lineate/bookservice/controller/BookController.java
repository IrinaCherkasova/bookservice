package com.lineate.bookservice.controller;

import com.lineate.bookservice.model.Book;
import com.lineate.bookservice.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Book> getBookById(@PathVariable String id) {
        return bookService.findById(id);
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping(value = "/bookNames", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> getAllBookNames() {
        return bookService.findAllBookNames();
    }
}