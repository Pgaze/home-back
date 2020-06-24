FROM java:8-alpine
ENV JAR_FILE target/*.jar
COPY $JAR_FILE app.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xmx1G"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=pi -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
