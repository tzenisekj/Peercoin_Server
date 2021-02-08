FROM gradle:6.8.2-jdk11
COPY . /usr/src/
WORKDIR /usr/src/web/
COPY ./web/src/main/resources/application.properties.sample /usr/src/web/src/main/resources/application.properties
CMD ["gradle", "bootrun"]
