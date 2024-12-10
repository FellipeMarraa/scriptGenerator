# Use a imagem base com Java 17
FROM openjdk:17-jdk-slim

# Adicione o Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixe as dependências antes de copiar o código-fonte
RUN ./mvnw dependency:go-offline -B

# Copie o restante do código do projeto
COPY src/ src

# Compile o projeto para gerar o .jar
RUN ./mvnw package -DskipTests

# Copie o arquivo gerado para o local correto na imagem
COPY target/Script-Generator-0.0.1-SNAPSHOT.jar app.jar

# Defina o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app.jar"]
