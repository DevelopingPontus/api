package com.example.api.demo.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "library")
public class ConfigProperties {
    private String vaultToken;

    public void setVaultToken(String vaultToken) {
        this.vaultToken = vaultToken;
    }

    public String getVaultToken() {
        return vaultToken;
    }
}
