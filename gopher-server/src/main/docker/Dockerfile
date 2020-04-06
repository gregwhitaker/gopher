FROM adoptopenjdk/openjdk11
ARG JAR_FILE
VOLUME /tmp
RUN mkdir -p /opt/gopher
COPY build/libs/${JAR_FILE} /opt/gopher/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/gopher/app.jar"]
