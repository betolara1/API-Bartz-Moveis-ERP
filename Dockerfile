# Estágio de build (builder)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /workspace/app

# Copia os arquivos essenciais do maven e o pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dá permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Copia dependências locais e as instala no repositório interno do Maven
COPY libs/ libs/
RUN ./mvnw install:install-file \
    -Dfile=libs/jwt-package-1.0.3.jar \
    -DgroupId=com.betolara1 \
    -DartifactId=jwt-package \
    -Dversion=1.0.3 \
    -Dpackaging=jar

# Baixa as dependências (faz cache na camada Docker)
RUN ./mvnw dependency:go-offline

# Copia o restante do código-fonte
COPY src src

# Faz o build pulando os testes para ser mais rápido (os testes rodam no CI)
RUN ./mvnw clean package -DskipTests

# Estágio de runtime (produção)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Adiciona um usuário não-root por questões de segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Variáveis de ambiente com valores padrão (opcional)
ENV PORT=8081

# Copia apenas o JAR final do estágio de build
COPY --from=builder /workspace/app/target/*.jar app.jar

# Expõe a porta que a aplicação escuta
EXPOSE ${PORT}

# Ponto de entrada
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
