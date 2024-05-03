FROM amazoncorretto:21

ARG JAR_FILE=backend/build/libs/*.jar

COPY ./backend/build/libs/url-shortener-0.0.5.jar app.jar

CMD apt-get update -y

ENTRYPOINT ["java", "-Xmx1024M", "-jar","/app.jar"]