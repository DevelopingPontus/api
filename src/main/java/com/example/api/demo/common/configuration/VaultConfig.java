package com.example.api.demo.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;


@Configuration
public class VaultConfig {
    private static final Logger logger = LoggerFactory.getLogger(VaultConfig.class);

    private final ConfigProperties configProperties;

    public VaultConfig(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    public String token() {
        return configProperties.getVaultToken();
    }

    @Bean
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(token());
    }

    @Bean
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setScheme("http");
        logger.info("----------------------------");
        logger.info("Vault end point: {}", vaultEndpoint);
        logger.info("----------------------------");
        return vaultEndpoint;
    }

    @Bean
    public VaultTemplate vaultTemplate(VaultEndpoint vaultEndpoint,
            ClientAuthentication auth) {
        return new VaultTemplate(vaultEndpoint, auth);
    }

}