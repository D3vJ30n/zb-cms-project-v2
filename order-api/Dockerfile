# Build stage
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean :order-api:build -x test

# Run stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/order-api/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:mysql://zb-cms-mysql:3306/zb_cms
ENV SPRING_DATASOURCE_USERNAME=zb_user
ENV SPRING_DATASOURCE_PASSWORD=1234
ENV SPRING_REDIS_HOST=redis-local
ENV SPRING_REDIS_PORT=6379

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
