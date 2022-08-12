FROM openjdk
WORKDIR usr/lib

ADD ./target/product-webapp-0.0.1-SNAPSHOT.jar /usr/lib/product-webapp-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","product-webapp-0.0.1-SNAPSHOT.jar"]