package com.example.api.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api.demo.feature.book.BookService;
import com.example.api.demo.feature.book.v1.BookRequestV1;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookService bookService;

    public DataLoader(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Starting Database Seeding ---");

        seedBooks();

        System.out.println("--- Database Seeding Complete ---");
    }

    private void seedBooks() {

        // Seed books with availability
        List<BookRequestV1> books = new ArrayList<>();
        BookRequestV1 book1 = new BookRequestV1("Animal Farm", "George Orwell", "12200", 1945, true);
        books.add(book1);
        BookRequestV1 book2 = new BookRequestV1("1984", "George Orwell", "12300", 1949, true);
        books.add(book2);
        BookRequestV1 book3 = new BookRequestV1("Brave New World", "Aldous Huxley", "12500", 1932, false);
        books.add(book3);

        bookService.save(books);

    }
}