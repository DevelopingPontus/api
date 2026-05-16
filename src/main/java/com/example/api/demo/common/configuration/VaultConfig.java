package com.example.api.demo.common.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Configuration
public class VaultConfig {

    @Bean
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("my-dev-root-token");
    }

    @Bean
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setScheme("http");
        System.out.println("----------------------------");
        System.out.println(vaultEndpoint.toString());
        System.out.println("----------------------------");
        return vaultEndpoint;
    }

    @Bean
    public VaultTemplate vaultTemplate(VaultEndpoint vaultEndpoint, 
            ClientAuthentication auth) {
        return new VaultTemplate(vaultEndpoint, auth);
    }
    


}