# 빌드용 이미지
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Gradle 캐시 최적화용
COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon

# 실제 소스 복사 및 빌드
COPY . .
RUN ./gradlew bootJar --no-daemon

# 실행용 이미지 (경량)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
