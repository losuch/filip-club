# docker build -t filip-club:latest .
# docker run --name filip-club --network fc-network -p 8080:8080 -d filip-club:latest

FROM maven:3.8-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:18-jdk-alpine
COPY --from=build /home/app/target/*.jar app.jar
# COPY start.sh /app/start.sh
# COPY app.env /app/app.env
# COPY wait-for.sh /app/wait-for.sh
# COPY db /app/db
ENV JDBC_URL=$JDBC_URL
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD
# CMD ["java", "-jar", "app.jar"]
# ENTRYPOINT [ "/app/start.sh" ]
ENTRYPOINT [ "java", "-jar", "app.jar" ]