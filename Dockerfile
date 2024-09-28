FROM openjdk:17
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar couponmanagement.jar
ENTRYPOINT ["java","-jar","/couponmanagement.jar"]