<#-- infobip-smv-challenge.ftl -->
<#-- Incluir cabeçalho padrão do tema -->
<#include "login/header.ftl">

<div class="container">
  <h2>Verificação de Posse</h2>
  <p>
    Estamos verificando a posse do seu número de telefone. Por favor, aguarde enquanto realizamos a verificação.
  </p>

  <#-- Se houver mensagem de erro, exibe-a -->
  <#if message?? && message != "">
    <div class="alert alert-danger">${message}</div>
  </#if>

  <form action="${url.loginAction}" method="post">
    <input type="hidden" name="kc_action" value="${kcAction}" />
    <button type="submit" class="btn btn-primary">Continuar</button>
  </form>
</div>

<#-- Incluir rodapé padrão do tema -->
<#include "login/footer.ftl">
