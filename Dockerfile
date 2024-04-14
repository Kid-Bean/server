FROM openjdk:17
COPY build/libs/server-0.0.1-SNAPSHOT.jar maribo.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "maribo.jar"]