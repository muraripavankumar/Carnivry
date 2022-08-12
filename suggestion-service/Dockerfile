FROM openjdk
WORKDIR usr/lib

ADD ./target/suggestion-service-0.0.1-SNAPSHOT.jar /usr/lib/suggestion-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","suggestion-service-0.0.1-SNAPSHOT.jar"]