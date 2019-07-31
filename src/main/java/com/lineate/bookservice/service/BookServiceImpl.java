package com.lineate.bookservice.service;

import com.lineate.bookservice.model.Book;
import com.lineate.bookservice.repository.BookRepository;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

  @Value("${timeout}")
  private Long timeout;

  @Value("${delay}")
  private Long delay;

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Mono<Book> findById(String id) {
    return bookRepository.findById(id);
  }

  @Override
  public Flux<Book> findAll() {
    return bookRepository.findAll()
        .filter(Objects::nonNull)
        .sort(Comparator.comparing(Book::getId))
        .delayElements(Duration.ofMillis(delay))
        .timeout(Duration.ofMillis(timeout))
        .doOnError(err -> LOGGER.error(err.getMessage()))
        .onErrorResume(err -> Flux.empty());
  }

  @Override
  public Mono<Book> save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return bookRepository.deleteById(id);
  }

  @Override
  public Flux<String> findAllBookNames() {
    return bookRepository.findAll()
        .timeout(Duration.ofMillis(timeout))
        .doOnError(err -> LOGGER.error(err.getMessage()))
        .onErrorResume(err -> Flux.empty())
        .filter(Objects::nonNull)
        .map(Book::getTitle)
        .distinct();
  }

  @Override
  public Flux<Book> save(List<Book> books) {
    return bookRepository.saveAll(books);
  }
}