FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY *.jar MicroserviceTechnician.jar
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "java","-jar", "MicroserviceTechnician.jar" ]
