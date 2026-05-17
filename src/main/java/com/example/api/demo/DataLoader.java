package com.example.api.demo;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import com.example.api.demo.common.configuration.VaultConfig;
import com.example.api.demo.feature.book.BookFacade;
import com.example.api.demo.feature.book.v1.BookRequestV1;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BookFacade bookFacade;

    @Autowired
    private VaultConfig vaultConfig;

    public DataLoader(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @Override
    public void run(String... args) throws Exception {
        seedBooks();
        seedUserToVault();
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

    @Autowired
    private VaultTemplate vaultTemplate;

    private void seedUserToVault() {
        VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);

        System.out.println();
        System.out.println("Post secret" + Collections.singletonMap("user",
                "pastaword").toString() + " to vault");
        System.out.println();

        keyValueOperations.put("secret", Collections.singletonMap("user",
                "pastaword"));

        VaultResponse read = keyValueOperations.get("secret");
        System.out.println("Value of user password from vault [" +
                read.getRequiredData().get("user") + "]");
    }

}
