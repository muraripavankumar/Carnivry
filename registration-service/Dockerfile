FROM openjdk
WORKDIR usr/lib

ADD ./target/registration-service-0.0.1-SNAPSHOT.jar /usr/lib/registration-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","registration-service-0.0.1-SNAPSHOT.jar"]