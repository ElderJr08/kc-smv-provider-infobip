package com.br.smv.requiredactions;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;

import java.io.IOException;

public class InfobipSmvRequiredActionsProvider implements RequiredActionProvider {

    /**
     * Se necessário, você pode implementar condições para disparar a ação automaticamente.
     */
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // Por padrão, não fazemos nada aqui. A ação poderá ser forçada manualmente ou por outro fluxo.
    }

    /**
     * Exibe um challenge simples para informar ao usuário que a verificação está em andamento.
     * Esse template pode apresentar uma mensagem do tipo "Verificando sua posse, por favor aguarde..."
     * e um botão "Continuar" que envia a requisição para processAction.
     *
     * O template "infobip-smv-challenge.ftl" deve ser criado na pasta do tema (por exemplo, themes/base/login/).
     */
    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        // Exibe um formulário sem necessidade de input do usuário.
        System.out.println(context);
        context.challenge(context.form().createForm("infobip-smv-challenge"));
    }

    /**
     * Processa a ação sem esperar um código do usuário.
     * Aqui, o provider simplesmente chama a API Infobip SMV com o número de telefone do usuário e,
     * com base na resposta (sucesso ou falha), remove a ação obrigatória ou reexibe o challenge.
     */
    @Override
    public void processAction(RequiredActionContext context) {
        UserModel user = context.getUser();
        String phoneNumber = user.getFirstAttribute("phone");

        boolean verified;
        try {
            verified = callInfobipSMV(phoneNumber);
        } catch (IOException e) {
            context.challenge(
                    context.form()
                            .setError("Erro de comunicação com o serviço de verificação. Tente novamente.")
                            .createForm("infobip-smv-challenge")
            );
            return;
        }

        if (verified) {
            user.removeRequiredAction(getId());
            context.success();
        } else {
            context.challenge(
                    context.form()
                            .setError("Falha na verificação da posse do número.")
                            .createForm("infobip-smv-challenge")
            );
        }
    }

    @Override
    public void close() {
    }

    /**
     * Chama a API Infobip SMV para validar o número de telefone.
     * Como a Infobip SMV retorna apenas sucesso ou falha, não enviamos um código de verificação.
     *
     * @param phoneNumber o número de telefone do usuário
     * @return true se a verificação for bem-sucedida; false caso contrário
     * @throws IOException em caso de erro na comunicação
     */
    private boolean callInfobipSMV(String phoneNumber) throws IOException {
        String url = "https://api.infobip.com/smv/verify";
        String jsonPayload = String.format("{\"phoneNumber\": \"%s\"}", phoneNumber);

//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost postRequest = new HttpPost(url);
//            postRequest.setHeader("Content-Type", "application/json");
//            postRequest.setHeader("Authorization", "App YOUR_API_TOKEN_HERE");
//            postRequest.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));
//
//            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
//                int statusCode = response.getStatusLine().getStatusCode();
//                return statusCode >= 200 && statusCode < 300;
//            }
//        }

        return true;
    }

    public String getId() {
        return "infobip-smv-required-action";
    }
}
