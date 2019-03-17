FROM openjdk:11-jdk
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY ${DEPENDENCY}/static /app/static
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-cp","app:app/lib/*","social.alone.server.Application"]