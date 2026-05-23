package com.example.api.demo;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import com.example.api.demo.feature.book.BookFacade;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.loan.Loan;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private BookFacade bookFacade;
    private VaultTemplate vaultTemplate;
    private PasswordEncoder passwordEncoder;

    public DataLoader(BookFacade bookFacade, VaultTemplate vaultTemplate, PasswordEncoder passwordEncoder) {
        this.bookFacade = bookFacade;
        this.vaultTemplate = vaultTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedBooks();
        seedUserSecretToVault();
    }

    private void seedBooks() {
        // Seed books
        BookRequestV1 book1 = new BookRequestV1("Animal Farm", "George Orwell", "12200", 1945);
        BookRequestV1 book2 = new BookRequestV1("1984", "George Orwell", "12300", 1949);
        BookRequestV1 book3 = new BookRequestV1("Brave New World", "Aldous Huxley", "12500", 1932);
        BookRequestV1 book4 = new BookRequestV1("The Great Gatsby", "F. Scott Fitzgerald", "22020", 1925);
        BookRequestV1 book5 = new BookRequestV1("To Kill a Mockingbird", "Harper Lee", "22030", 1960);

        bookFacade.save(book1);
        bookFacade.save(book2);
        bookFacade.save(book3);
        bookFacade.save(book4);
        bookFacade.save(book5);
    }

    // Just to show that vault can take a request and return it
    private void seedUserSecretToVault() {
        String what = "secret";
        try {
            VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue(what,
                    VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);

            keyValueOperations.put(
                    what, Collections.singletonMap("password",
                            "pastaword"));

            logger.info("Vault operations done in DataLoader.java");
            logger.info("Posted {} secret to vault", Collections.singletonMap("password", "pastaword"));

            VaultResponse read = keyValueOperations.get(what);
            if (read != null) {
                Object password = read.getRequiredData().get("password");
                logger.info("Value of user password read from vault [{}]", password);
                userDetailsService(passwordEncoder, password);
            }
        } catch (Exception _) {
            logger.warn("Error while seeding user to vault");
        }
    }
    

    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, Object password) {
        UserDetails userDetails = User.builder()
                .username("user")
                .password(passwordEncoder.encode(password.toString()))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
