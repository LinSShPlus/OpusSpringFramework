FROM maven:3.8.3-openjdk-17-slim

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
ADD ./src $PROJECT_DIR/src
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:17-jdk-slim

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/target/*.jar $PROJECT_DIR/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/app.jar"]