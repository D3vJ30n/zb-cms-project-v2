# Run stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/order-api/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:mysql://zb-cms-mysql:3308/zb_cms
ENV SPRING_DATASOURCE_USERNAME=zb_user
ENV SPRING_DATASOURCE_PASSWORD=1234
ENV SPRING_REDIS_HOST=redis-local
ENV SPRING_REDIS_PORT=6379

ENTRYPOINT ["java", "-jar", "app.jar"]
