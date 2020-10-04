FROM openjdk:11
ADD target/jav-es-database-proxy-0.0.1-SNAPSHOT.jar jav-es-database-proxy-0.0.1-SNAPSHOT.jar
EXPOSE 9070
ENTRYPOINT ["java", "-jar", "jav-es-database-proxy-0.0.1-SNAPSHOT.jar"]
