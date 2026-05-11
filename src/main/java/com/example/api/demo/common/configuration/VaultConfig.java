package com.example.api.demo.common.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.create("host", 8020);
    }

    public void seedVaultWithUser() {
		VaultOperations operations = new VaultTemplate(new VaultEndpoint());
		VaultKeyValueOperations keyValueOperations = operations.opsForKeyValue("secret",
				VaultKeyValueOperationsSupport.KeyValueBackend.KV_1);

		keyValueOperations.put("user", Collections.singletonMap("password", "password"));

		VaultResponse read = keyValueOperations.get("user");
		read.getRequiredData().get("password");
    }
}