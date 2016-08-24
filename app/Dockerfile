FROM maven:3-jdk-8 
RUN mkdir -p /app
WORKDIR /app

# Prepare by downloading dependencies
ADD pom.xml pom.xml
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD . .
RUN ["mvn", "package"]

EXPOSE 8000
CMD ["java", "-jar", "target/app-1.0-SNAPSHOT-jar-with-dependencies.jar"]
