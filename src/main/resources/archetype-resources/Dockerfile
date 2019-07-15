FROM adoptopenjdk/openjdk8-openj9:alpine-slim
#FROM openjdk:jre-alpine

#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]
RUN apk add --update \
    &&  apk add curl bash tzdata \
    && cp -r -f /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime \ 
    && rm -rf /var/cache/apk/* && apk del tzdata \
    && mkdir -p /usr/src/myapp
    
# RUN mkdir -p /usr/src/myapp
COPY ./target/${artifactId}-${version}.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Duser.timezone=GMT+08","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
ENTRYPOINT exec java $JAVA_OPTS -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar ./app.jar 