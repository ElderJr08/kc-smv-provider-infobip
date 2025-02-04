package com.br.smv.factory;

import com.br.smv.authenticator.InfobipSmvAuthenticator;
import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class InfobipSmvAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "infobip-silent-mobile-verification";

    @Override
    public Authenticator create(KeycloakSession session) {
        return new InfobipSmvAuthenticator();
    }

    @Override
    public void init(Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Infobip Silent Mobile Verification";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public String getHelpText() {
        return "Realiza a verificação móvel silenciosa utilizando a API Infobip SMV.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        // Configure as opções de exigência do autenticador (por exemplo, REQUIRED, ALTERNATIVE, etc.)
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void close() {
        System.out.println();
    }
}