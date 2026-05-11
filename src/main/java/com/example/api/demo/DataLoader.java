package com.example.api.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api.demo.feature.book.BookFacade;
import com.example.api.demo.feature.book.v1.BookRequestV1;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookFacade bookFacade;

    public DataLoader(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @Override
    public void run(String... args) throws Exception {
        seedBooks();
    }

    private void seedBooks() {

        // Seed books with availability
        BookRequestV1 book1 = new BookRequestV1("Animal Farm", "George Orwell", "12200", 1945);
        BookRequestV1 book2 = new BookRequestV1("1984", "George Orwell", "12300", 1949);
        BookRequestV1 book3 = new BookRequestV1("Brave New World", "Aldous Huxley", "12500", 1932);

        bookFacade.save(book1);
        bookFacade.save(book2);
        bookFacade.save(book3);

    }

    
}
    