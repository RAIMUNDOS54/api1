# Alpine Linux com OpenJDK 11 JRE
#FROM azul/zulu-openjdk-alpine:11
FROM openjdk:11.0.16-slim

# Copiar pacote .jar e renomear para echo.war
COPY api1-0.0.1-SNAPSHOT.jar /echo.war

# Executar a aplicação
CMD ["/usr/local/openjdk-11/bin/java", "-jar", "/echo.war"]
