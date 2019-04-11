FROM registry.cn-hangzhou.aliyuncs.com/nibo/oracle-jdk:jdk8u112
VOLUME /tmp
ADD *.jar app.jar

RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]