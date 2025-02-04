# Provider Customizado para Keycloak – SMV

Este projeto demonstra como desenvolver um provider customizado para o Keycloak (por exemplo, para realizar verificação móvel via Infobip SMV), empacotá-lo em um JAR e rodar o Keycloak utilizando Docker.

> **Atenção:**  
> As instruções abaixo foram testadas utilizando o Keycloak 16.1.1 (para garantir a disponibilidade da classe `org.keycloak.Config`) e um ambiente Windows com Docker Desktop instalado.

---

## Pré-requisitos

- **Java JDK 11** (ou superior)  
  [Download JDK](https://adoptium.net/)
- **Maven**  
  [Download Maven](https://maven.apache.org/install.html)
- **Docker Desktop** para Windows  
  [Download Docker Desktop](https://www.docker.com/products/docker-desktop)

---

## Estrutura do Projeto

O projeto utiliza Maven. Confira um exemplo de `pom.xml` com as dependências necessárias:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.seuprojeto</groupId>
  <artifactId>kc-smv-provider-infobip</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>
  <name>Provider SMV Customizado</name>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <keycloak.version>16.1.1</keycloak.version>
  </properties>

  <dependencies>
    <!-- Keycloak SPI -->
    <dependency>
      <groupId>org.keycloak</groupId>
      <artifactId>keycloak-server-spi</artifactId>
      <version>${keycloak.version}</version>
      <scope>provided</scope>
    </dependency>

    <!-- Keycloak SPI Private -->
    <dependency>
      <groupId>org.keycloak</groupId>
      <artifactId>keycloak-server-spi-private</artifactId>
      <version>${keycloak.version}</version>
      <scope>provided</scope>
    </dependency>

    <!-- Keycloak Core (contém org.keycloak.Config) -->
    <dependency>
      <groupId>org.keycloak</groupId>
      <artifactId>keycloak-core</artifactId>
      <version>${keycloak.version}</version>
      <scope>provided</scope>
    </dependency>

    <!-- Dependência opcional: Apache HttpClient -->
    <dependency>
      <groupId>org.apache.httpcomponents</groupId>
      <artifactId>httpclient</artifactId>
      <version>4.5.13</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <!-- Plugin de compilação do Maven -->
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
          <source>${maven.compiler.source}</source>
          <target>${maven.compiler.target}</target>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

Além disso, crie o arquivo de serviços para registrar o provider customizado.
Crie o arquivo:


src/main/resources/META-INF/services/org.keycloak.authentication.AuthenticatorFactory
e adicione a linha com o nome completo da classe da sua factory, por exemplo:

```
com.br.smv.authenticator.InfobipSmvAuthenticatorFactory
```

## Passo 1: Gerar o JAR do Provider

- Abra um terminal na raiz do projeto.
- Execute o comando Maven:

```cmd
mvn clean package
```

O JAR será gerado em target/kc-smv-provider-infobip-1.0-SNAPSHOT.jar.

## Passo 2: Rodar o Keycloak no Docker com o Provider Customizado

Observações para Windows:

    Utilize a notação de caminho Unix para o volume, ou seja, substitua C: por /c.
    Envolva todo o mapeamento de volume em aspas duplas para lidar com espaços no caminho.

### Comando de Execução:

Abra o Prompt de Comando (CMD) e execute o comando abaixo (tudo em uma única linha):

```
docker run -p 8080:8080 --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin123 -v "/c/Users/Projetos/kc-smv-provider-infobip/target/kc-smv-provider-infobip-1.0-SNAPSHOT.jar:/opt/keycloak/providers/kc-smv-provider-infobip-1.0-snapshot.jar" quay.io/keycloak/keycloak:latest start-dev
```

Com volume:

```
docker run -p 8080:8080 --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin123 -v "/c/Users/Projetos/kc-smv-provider-infobip/target/kc-smv-provider-infobip-1.0-SNAPSHOT.jar:/opt/keycloak/providers/kc-smv-provider-infobip-1.0-snapshot.jar" -v "/c/Users/Projetos/kc-smv-provider-infobip/src/themes:/opt/keycloak/themes/base" quay.io/keycloak/keycloak:latest start-dev
```