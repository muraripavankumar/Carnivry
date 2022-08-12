FROM openjdk
WORKDIR usr/lib

ADD ./target/management-service-0.0.1-SNAPSHOT.jar /usr/lib/management-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","management-service-0.0.1-SNAPSHOT.jar"]