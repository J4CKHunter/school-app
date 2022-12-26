# first version
#FROM maven:3.8.5-openjdk-17
#WORKDIR /app
#COPY . /app
#RUN mvn clean package -DskipTests
#EXPOSE 9000
#CMD ["mvn", "spring-boot:run"]

# second version
#FROM maven:3.8.5-openjdk-17
#EXPOSE 9000
#ADD target/spring-rabbitmq-producer-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]

# third version
#
# build stage
#
FROM maven:3.8.5-openjdk-17 as build
COPY . /home/app
WORKDIR /home/app
RUN mvn clean install -am -DskipTests=true
#
# package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar 
EXPOSE 9000
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]


