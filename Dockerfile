ARG BASE_IMAGE_PREFIX=""

FROM ${BASE_IMAGE_PREFIX}node as node-builder
ADD /src/frontend /source
WORKDIR /source
RUN npm i && npm run build

FROM ${BASE_IMAGE_PREFIX}maven AS builder
COPY . /source
WORKDIR /source
COPY --from=node-builder /source/build /source/src/main/resources/static
RUN mvn package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=builder /source/target /app
WORKDIR /app
ADD application.properties /app/application.properties

ENV LC_ALL="no_NB.UTF-8"
ENV LANG="no_NB.UTF-8"
ENV TZ="Europe/Oslo"

EXPOSE 8800
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar ./mia.jar
