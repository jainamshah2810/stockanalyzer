FROM openjdk:8
COPY target/stockanalyzer-0.0.1-SNAPSHOT.jar stockanalyzer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/stockanalyzer-0.0.1-SNAPSHOT.jar"]