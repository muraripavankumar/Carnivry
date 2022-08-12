FROM openjdk
WORKDIR usr/lib

ADD ./target/ticketing-service-0.0.1-SNAPSHOT.jar /usr/lib/ticketing-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","ticketing-service-0.0.1-SNAPSHOT.jar"]