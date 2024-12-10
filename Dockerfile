# Use a imagem base com Java 17
FROM openjdk:17-jdk-slim

# Argumento para o jar gerado
ARG JAR_FILE=target/Script-Generator-0.0.1-SNAPSHOT.jar

# Copia o arquivo .jar para dentro da imagem Docker
COPY ${JAR_FILE} app.jar

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]
