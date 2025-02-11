
FROM ubuntu:latest AS build

# Atualizar repositórios e instalar dependências
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install maven -y

# Copiar o código-fonte para o container
COPY . .


RUN mvn clean install -DskipTests


FROM openjdk:21-jdk-slim


EXPOSE 8080


COPY --from=build target/ecommercee-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
