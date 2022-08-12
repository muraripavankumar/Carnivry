FROM openjdk
WORKDIR usr/lib

ADD ./target/notification-service-0.0.1-SNAPSHOT.jar /usr/lib/notification-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","notification-service-0.0.1-SNAPSHOT.jar"]