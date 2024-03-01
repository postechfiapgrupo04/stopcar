FROM openjdk:17-slim

WORKDIR /app

# recebemos o valor do argumento jar_file em tempo de execucao do build
ARG JAR_FILE

COPY target/${JAR_FILE} /app/stopcar.jar
COPY wait-for-it.sh /wait-for-it.sh

#RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "stopcar.jar"]