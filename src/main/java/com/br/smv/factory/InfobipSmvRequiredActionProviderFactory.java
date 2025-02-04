package com.br.smv.factory;

import com.br.smv.requiredactions.InfobipSmvRequiredActionsProvider;
import org.keycloak.Config.Scope;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;


public class InfobipSmvRequiredActionProviderFactory implements RequiredActionFactory {
    public static final String PROVIDER_ID = "infobip-smv-required-action";

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new InfobipSmvRequiredActionsProvider();
    }

    @Override
    public void init(Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Tarefas pós-inicialização, se necessário.
    }

    @Override
    public void close() {
        // Liberação de recursos, se necessário.
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "Infobip SMV Verification";
    }
}
