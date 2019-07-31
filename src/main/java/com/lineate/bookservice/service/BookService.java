package com.lineate.bookservice.service;

import com.lineate.bookservice.model.Book;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> findById(String id);
    Flux<Book> findAll();
    Mono<Book> save(Book book);
    Mono<Void> deleteById(String id);
    Flux<String> findAllBookNames();
    Flux<Book> save(List<Book> books);
}