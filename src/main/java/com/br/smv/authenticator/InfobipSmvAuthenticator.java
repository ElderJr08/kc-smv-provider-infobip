package com.br.smv.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;


public class InfobipSmvAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String phoneNumber = user.getFirstAttribute("phone");

        boolean verificationResult = callInfobipSMV(phoneNumber);
        if (verificationResult) {
            context.success();
        } else {
            context.failure(AuthenticationFlowError.INVALID_USER);
        }
    }

    private boolean callInfobipSMV(String phoneNumber) {
        return true;
    }

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }
}
