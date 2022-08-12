FROM openjdk
WORKDIR usr/lib

ADD ./target/authentication-service-0.0.1-SNAPSHOT.jar /usr/lib/authentication-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","authentication-service-0.0.1-SNAPSHOT.jar"]