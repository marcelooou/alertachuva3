# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container de build
WORKDIR /app

# Copia o pom.xml para baixar as dependências primeiro (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Stage 2: Create the final image using a JRE
FROM eclipse-temurin:17-jre-jammy

# Define o nome do usuário e grupo não-root
ARG USER=fiapuser
ARG GROUP=fiapgroup
ARG UID=1001
ARG GID=1001

# Cria o grupo e o usuário
RUN groupadd -g ${GID} ${GROUP} \
    && useradd -u ${UID} -g ${GROUP} -m -s /bin/bash ${USER}

# Instala dependências e limpa cache do apt em uma única camada
# Muda para root temporariamente para instalar pacotes
USER root
RUN apt-get update && apt-get install -y --no-install-recommends netcat-openbsd \
    && rm -rf /var/lib/apt/lists/*

# Define o diretório de trabalho no container final
WORKDIR /app

# Copia o script de espera e garante permissão de execução
COPY wait-for-oracle.sh .
RUN chmod +x wait-for-oracle.sh

# Copia o JAR construído do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Define o usuário que executará a aplicação
USER ${USER}

# Expõe a porta que a aplicação usará (definida em application.properties)
EXPOSE 8080

# Define variáveis de ambiente para a conexão com o banco de dados
# Os valores reais serão fornecidos no comando 'docker run' ou docker-compose.yml
ENV DB_URL="jdbc:oracle:thin:@oracle-db:1521:XE" \
    DB_USER="system" \
    DB_PASSWORD="oracle"

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["./wait-for-oracle.sh", "oracle-db", "java", "-jar", "app.jar"]