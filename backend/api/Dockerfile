# ---------- Build Stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos el wrapper y configuración de Gradle
COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew

# Copiamos los archivos de configuración Gradle y el código fuente
COPY build.gradle settings.gradle ./
COPY src src

# Compilamos el JAR
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew clean bootJar --no-daemon

# ---------- Run Stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos solo el jar generado
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
